package br.com.bg7.appvistoria.sync;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;

import br.com.bg7.appvistoria.data.ProductInspection;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
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

    private Runnable updateQueue;

    private Runnable sync;

    @Before
    public void setUp() {
        super.setUp();

        new SyncManager(
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
    public void shouldCallSyncOnInspectionFromQueueIfReady() {
        SyncTestableProductInspection inspection = new SyncTestableProductInspection(SyncStatus.READY);
        productInspectionRepository.save(inspection);

        updateQueue.run();

        when(syncExecutor.executeSync(eq(inspection), executeSyncCaptor.capture())).thenReturn(true);

        sync.run();
        Runnable syncJob = executeSyncCaptor.getValue();
        syncJob.run();

        // TODO: Terminar de fazer esse setup e ver o que verificar
        Assert.assertTrue(true);
    }
}
