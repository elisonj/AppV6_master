package br.com.bg7.appvistoria.sync;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.bg7.appvistoria.data.ProductInspection;
import br.com.bg7.appvistoria.data.source.remote.HttpProgressCallback;
import br.com.bg7.appvistoria.data.source.remote.dto.ProductResponse;
import br.com.bg7.appvistoria.data.source.remote.fake.FakeProductInspection;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
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

        runSync(inspection);
        fail(inspection);

        ProductInspection inspectionFromDb = productInspectionRepository.findById(ProductInspection.class, inspection.getId());
        Assert.assertEquals(SyncStatus.FAILED, inspectionFromDb.getSyncStatus());
    }

    @Test
    public void shouldCallSubscribedCallback() {
        final FakeProductInspection inspection = new FakeProductInspection(SyncStatus.READY);
        productInspectionRepository.save(inspection);

        syncManager.subscribe(new SyncCallbackCheck(inspection));

        runSync(inspection);
        fail(inspection);

        // Verification happens when the callback gets called
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

    private void runSync(FakeProductInspection inspection) {
        updateQueue.run();
        sync.run();

        verify(syncExecutor).executeSync(executeSyncCaptor.capture());
        executeSyncCaptor.getValue().run();
    }

    private void fail(FakeProductInspection inspection) {
        verify(productInspectionService).send(eq(inspection), serviceCallback.capture());
        serviceCallback.getValue().onFailure(null);
    }
}
