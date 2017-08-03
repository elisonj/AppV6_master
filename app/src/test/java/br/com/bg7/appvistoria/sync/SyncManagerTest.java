package br.com.bg7.appvistoria.sync;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;

import br.com.bg7.appvistoria.data.ProductInspection;
import br.com.bg7.appvistoria.data.source.remote.HttpProgressCallback;
import br.com.bg7.appvistoria.data.source.remote.dto.ProductResponse;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by: luciolucio
 * Date: 2017-08-02
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

    /**
     * TODO: Obter a implementação do ProductInspection para esse teste poder passar
     */
    @Test
    public void shouldCallSyncOnInspectionFromQueueIfReady() {
        final SyncTestableProductInspection inspection = new SyncTestableProductInspection(SyncStatus.READY);
        productInspectionRepository.save(inspection);

        updateQueue.run();

        when(syncExecutor.executeSync(eq(inspection), executeSyncCaptor.capture())).thenReturn(true);

        sync.run();
        Runnable syncJob = executeSyncCaptor.getValue();

        syncManager.subscribe(new FailureCheckSyncCallback() {
            @Override
            public void onFailure(ProductInspection productInspection, Throwable t) {
                Assert.assertEquals(inspection, productInspection);
                Assert.assertNull(t);
                Assert.assertEquals(SyncStatus.FAILED, inspection.getSyncStatus());
            }
        });

        syncJob.run();

        verify(productInspectionService).send(eq(inspection), serviceCallback.capture());
        serviceCallback.getValue().onFailure(null);
    }
}
