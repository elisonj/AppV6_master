package br.com.bg7.appvistoria.sync;

import org.junit.Before;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.bg7.appvistoria.data.Inspection;
import br.com.bg7.appvistoria.data.source.local.fake.FakeInspectionRepository;
import br.com.bg7.appvistoria.data.source.remote.PictureService;
import br.com.bg7.appvistoria.data.source.remote.InspectionService;

import static br.com.bg7.appvistoria.sync.MockInspection.mockInspection;
import static org.mockito.Mockito.verify;

/**
 * Created by: luciolucio
 * Date: 2017-07-31
 */

class SyncManagerTestBase {

    SyncManager syncManager;

    Runnable updateQueue;

    Runnable sync;

    FakeInspectionRepository fakeInspectionRepository = new FakeInspectionRepository();

    @Mock
    SyncExecutor syncExecutor;

    @Mock
    InspectionService inspectionService;

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
                fakeInspectionRepository,
                inspectionService,
                pictureService,
                syncExecutor
        );

        verify(syncExecutor).scheduleQueueUpdates(scheduleQueueUpdatesCaptor.capture());
        updateQueue = scheduleQueueUpdatesCaptor.getValue();

        verify(syncExecutor).scheduleSyncLoop(syncLoopCaptor.capture());
        sync = syncLoopCaptor.getValue();
    }

    void save(Inspection inspection) {
        fakeInspectionRepository.save(inspection);
    }

    Inspection saveWithStatus(SyncStatus status) {
        Inspection inspection = mockInspection().withStatus(status).create();
        save(inspection);

        return inspection;
    }

    void runSync() {
        updateQueue.run();
        sync.run();

        verify(syncExecutor).executeSync(executeSyncCaptor.capture());
        executeSyncCaptor.getValue().run();
    }
}
