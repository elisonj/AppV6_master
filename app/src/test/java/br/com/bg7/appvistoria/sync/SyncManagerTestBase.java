package br.com.bg7.appvistoria.sync;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.concurrent.ScheduledThreadPoolExecutor;

import br.com.bg7.appvistoria.data.ProductInspection;
import br.com.bg7.appvistoria.data.source.PictureService;
import br.com.bg7.appvistoria.data.source.ProductInspectionService;
import br.com.bg7.appvistoria.data.source.local.ProductInspectionRepository;
import br.com.bg7.appvistoria.data.source.local.fake.FakeProductInspectionRepository;

import static br.com.bg7.appvistoria.sync.MockInspection.mockInspection;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

/**
 * Created by: luciolucio
 * Date: 2017-07-31
 */

class SyncManagerTestBase {

    SyncManager syncManager;

    Runnable updateQueue;

    Runnable sync;

    FakeProductInspectionRepository productInspectionRepository = new FakeProductInspectionRepository();

    @Mock
    SyncExecutor syncExecutor;

    @Mock
    ProductInspectionService productInspectionService;

    @Mock
    PictureService pictureService;

    @Captor
    ArgumentCaptor<Runnable> scheduleQueueUpdatesCaptor;

    @Captor
    ArgumentCaptor<Runnable> syncLoopCaptor;

    @Captor
    ArgumentCaptor<Runnable> executeSyncCaptor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

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

    void save(ProductInspection inspection) {
        productInspectionRepository.save(inspection);
    }

    ProductInspection saveWithStatus(SyncStatus status) {
        ProductInspection productInspection = mockInspection().withStatus(status).create();
        save(productInspection);

        return productInspection;
    }

    void runSync() {
        updateQueue.run();
        sync.run();

        verify(syncExecutor).executeSync(executeSyncCaptor.capture());
        executeSyncCaptor.getValue().run();
    }
}
