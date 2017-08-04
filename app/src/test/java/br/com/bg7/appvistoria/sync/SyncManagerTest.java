package br.com.bg7.appvistoria.sync;

import android.graphics.Picture;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;

import java.util.ArrayList;

import br.com.bg7.appvistoria.data.ProductInspection;
import br.com.bg7.appvistoria.data.source.PictureService;
import br.com.bg7.appvistoria.data.source.ProductInspectionService;
import br.com.bg7.appvistoria.data.source.remote.HttpProgressCallback;
import br.com.bg7.appvistoria.data.source.remote.SyncCallback;
import br.com.bg7.appvistoria.data.source.remote.dto.ProductResponse;
import br.com.bg7.appvistoria.data.source.remote.fake.FakeProductInspection;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by: luciolucio
 * Date: 2017-08-02
 *
 * Tests the SyncManager. One of its main rensponsibilities is to persist
 * the {@link ProductInspection} items in the {@link br.com.bg7.appvistoria.data.source.local.ProductInspectionRepository}
 */

public class SyncManagerTest extends SyncManagerTestBase {
    private SyncManager syncManager;

    @Captor
    private ArgumentCaptor<Runnable> scheduleQueueUpdatesCaptor;

    @Captor
    private ArgumentCaptor<Runnable> syncLoopCaptor;

    @Captor
    private ArgumentCaptor<Runnable> executeSyncCaptor;

    @Captor
    private ArgumentCaptor<HttpProgressCallback<ProductResponse>> serviceCallback;

    private Runnable updateQueue;

    private Runnable sync;

    @Before
    public void setUp() {
        super.setUp();

        syncManager = new SyncManager(
                productInspectionRepository,
                productInspectionService,
                pictureService,
                syncExecutor
        );

        verify(syncExecutor).scheduleQueueUpdates(scheduleQueueUpdatesCaptor.capture());
        updateQueue = scheduleQueueUpdatesCaptor.getValue();

        verify(syncExecutor).scheduleSyncLoop(syncLoopCaptor.capture());
        sync = syncLoopCaptor.getValue();
    }

    @Test
    public void shouldFailInspectionWhenServiceFails() {
        final FakeProductInspection inspection = new FakeProductInspection(SyncStatus.READY);
        productInspectionRepository.save(inspection);

        runSync();
        failProductInspectionService(inspection);

        ProductInspection inspectionFromDb = productInspectionRepository.findById(ProductInspection.class, inspection.getId());
        Assert.assertEquals(SyncStatus.FAILED, inspectionFromDb.getSyncStatus());
    }

    @Test
    public void shouldCallSubscribedCallback() {
        FakeProductInspection inspection = new FakeProductInspection(SyncStatus.READY);
        productInspectionRepository.save(inspection);

        syncManager.subscribe(new SyncCallbackCheck(inspection));

        runSync();
        failProductInspectionService(inspection);
        // TODO: fail the Picture service too
        // TODO: onProgressUpdate each service
        // TODO: succeed each service

        // Verification happens when the callback gets called
    }

    @Test
    public void shouldNotAttemptToSyncInspectionThatCannotSyncPictures() {
        ProductInspection inspection = mock(ProductInspection.class);
        when(inspection.canSyncPictures()).thenReturn(false);
        when(inspection.getSyncStatus()).thenReturn(SyncStatus.READY);
        productInspectionRepository.save(inspection);

        runSync();

        verify(inspection, never()).sync(any(PictureService.class), any(SyncCallback.class));
    }

    @Test
    public void shouldNotAttemptToSyncInspectionThatCannotSyncProduct() {
        ProductInspection inspection = mock(ProductInspection.class);
        when(inspection.canSyncProduct()).thenReturn(false);
        when(inspection.getSyncStatus()).thenReturn(SyncStatus.READY);
        productInspectionRepository.save(inspection);

        runSync();

        verify(inspection, never()).sync(any(ProductInspectionService.class), any(SyncCallback.class));
    }

    @Test
    public void shouldResetIncompleteProductInspectionsWhenStarting() {

        ArrayList<ProductInspection> inspectionsThatGetReset = new ArrayList<>();
        inspectionsThatGetReset.add(saveNewProductInspectionWith(SyncStatus.PICTURES_BEING_SYNCED));
        inspectionsThatGetReset.add(saveNewProductInspectionWith(SyncStatus.PRODUCT_INSPECTION_BEING_SYNCED));

        ArrayList<ProductInspection> inspectionsThatDoNotGetReset = new ArrayList<>();
        inspectionsThatDoNotGetReset.add(saveNewProductInspectionWith(SyncStatus.DONE));
        inspectionsThatDoNotGetReset.add(saveNewProductInspectionWith(SyncStatus.FAILED));
        inspectionsThatDoNotGetReset.add(saveNewProductInspectionWith(SyncStatus.PICTURES_SYNCED));
        inspectionsThatDoNotGetReset.add(saveNewProductInspectionWith(SyncStatus.READY));

        new SyncManager(productInspectionRepository, productInspectionService, pictureService, syncExecutor);

        for (ProductInspection mockThatGetsReset : inspectionsThatGetReset) {
            verify(mockThatGetsReset).reset();
        }

        for (ProductInspection mockThatDoesNotGetsReset : inspectionsThatDoNotGetReset) {
            verify(mockThatDoesNotGetsReset, never()).reset();
        }
    }

    private ProductInspection saveNewProductInspectionWith(SyncStatus status) {
        ProductInspection productInspection = mock(ProductInspection.class);
        when(productInspection.getSyncStatus()).thenReturn(status);
        productInspectionRepository.save(productInspection);

        return productInspection;
    }

    private void runSync() {
        updateQueue.run();
        sync.run();

        verify(syncExecutor).executeSync(executeSyncCaptor.capture());
        executeSyncCaptor.getValue().run();
    }

    private void failProductInspectionService(ProductInspection inspection) {
        verify(productInspectionService).send(eq(inspection), serviceCallback.capture());
        serviceCallback.getValue().onFailure(null);
    }
}
