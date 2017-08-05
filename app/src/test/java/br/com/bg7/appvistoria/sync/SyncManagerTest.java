package br.com.bg7.appvistoria.sync;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;

import java.util.ArrayList;

import br.com.bg7.appvistoria.data.ProductInspection;
import br.com.bg7.appvistoria.data.source.remote.HttpProgressCallback;
import br.com.bg7.appvistoria.data.source.remote.dto.ProductResponse;

import static br.com.bg7.appvistoria.sync.MockInspection.mockInspection;
import static br.com.bg7.appvistoria.sync.MockInspectionChecker.checkThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * Created by: luciolucio
 * Date: 2017-08-02
 *
 * Tests the SyncManager. One of its main rensponsibilities is to persist
 * the {@link ProductInspection} items in the {@link br.com.bg7.appvistoria.data.source.local.ProductInspectionRepository}
 *
 * TODO: Reorganizar a classe, que está muito grande
 * TODO: Todas as verificações de callback
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
    public void shouldNotAttemptToSyncPictureWhenCannotSyncPictures() {
        ProductInspection inspection = mockInspection().thatCannotSyncPictures().create();
        save(inspection);

        runSync();

        checkThat(inspection).doesNotSyncPictures();
    }

    @Test
    public void shouldNotAttemptToSyncProductWhenCannotSyncProduct() {
        ProductInspection inspection = mockInspection().thatCannotSyncProduct().create();
        save(inspection);

        runSync();

        checkThat(inspection).doesNotSyncProducts();
    }

    @Test
    public void shouldNotAttemptToSyncProductWhenCanSyncPictures() {
        ProductInspection inspection = mockInspection()
                .thatCanSyncPictures()
                .thatCanSyncProduct()
                .create();
        save(inspection);

        runSync();

        checkThat(inspection).doesNotSyncProducts();
    }

    @Test
    public void shouldSyncPictures() {
        ProductInspection inspection = mockInspection().thatCanSyncPictures().create();
        save(inspection);

        runSync();

        checkThat(inspection).syncsPictures();
    }

    @Test
    public void shouldSyncProductWhenCannotSyncPictures() {
        ProductInspection inspection = mockInspection()
                .thatCannotSyncPictures()
                .thatCanSyncProduct()
                .create();
        save(inspection);

        runSync();

        checkThat(inspection).syncsProducts();
    }

    @Test
    public void shouldResetIncompleteProductInspectionsWhenStarting() {

        ArrayList<ProductInspection> inspectionsThatGetReset = new ArrayList<>();
        inspectionsThatGetReset.add(saveWithStatus(SyncStatus.PICTURES_BEING_SYNCED));
        inspectionsThatGetReset.add(saveWithStatus(SyncStatus.PRODUCT_INSPECTION_BEING_SYNCED));

        ArrayList<ProductInspection> inspectionsThatDoNotGetReset = new ArrayList<>();
        inspectionsThatDoNotGetReset.add(saveWithStatus(SyncStatus.DONE));
        inspectionsThatDoNotGetReset.add(saveWithStatus(SyncStatus.FAILED));
        inspectionsThatDoNotGetReset.add(saveWithStatus(SyncStatus.PICTURES_SYNCED));
        inspectionsThatDoNotGetReset.add(saveWithStatus(SyncStatus.READY));

        new SyncManager(productInspectionRepository, productInspectionService, pictureService, syncExecutor);

        for (ProductInspection mockThatGetsReset : inspectionsThatGetReset) {
            verify(mockThatGetsReset).reset();
        }

        for (ProductInspection mockThatDoesNotGetsReset : inspectionsThatDoNotGetReset) {
            verify(mockThatDoesNotGetsReset, never()).reset();
        }
    }

    @Test
    public void shouldNotCallOnFailureForIllegalStateExceptionPicture() {
        ProductInspection inspection = mockInspection()
                .thatCanSyncPictures()
                .thatThrowsOnPictureSync()
                .create();
        save(inspection);
        syncManager.subscribe(new FailWhenOnFailureCalled());

        runSync();

        // Verification happens when the callback gets called
    }

    @Test
    public void shouldNotCallOnFailureForIllegalStateExceptionProduct() {
        ProductInspection inspection = mockInspection()
                .thatCannotSyncPictures()
                .thatCanSyncProduct()
                .thatThrowsOnProductSync()
                .create();
        save(inspection);
        syncManager.subscribe(new FailWhenOnFailureCalled());

        runSync();

        // Verification happens when the callback gets called
    }

    private void save(ProductInspection inspection) {
        productInspectionRepository.save(inspection);
    }

    private ProductInspection saveWithStatus(SyncStatus status) {
        ProductInspection productInspection = mockInspection().withStatus(status).create();
        save(productInspection);

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
