package br.com.bg7.appvistoria.sync;

import org.junit.Test;

import java.util.ArrayList;

import br.com.bg7.appvistoria.data.Inspection;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * Created by: elison
 * Date: 2017-08-02
 */

public class SyncManagerInitializationTest extends SyncManagerTestBase {

    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullRepository() {
        new SyncManager(null, inspectionService, pictureService, syncExecutor);
    }

    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullExecutor() {
        new SyncManager(fakeInspectionRepository, inspectionService, pictureService, null);
    }

    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullInspectionService() {
        new SyncManager(fakeInspectionRepository, null, pictureService, syncExecutor);
    }

    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullPictureService() {
        new SyncManager(fakeInspectionRepository, inspectionService, null, syncExecutor);
    }

    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullSubscriber()
    {
        syncManager.subscribe(null);
    }

    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullToUnsubscribe()
    {
        syncManager.unsubscribe(null);
    }

    @Test
    public void shouldScheduleQueueUpdates() {
        verify(syncExecutor).scheduleQueueUpdates(any(Runnable.class));
    }

    @Test
    public void shouldScheduleSyncLoop() {
        verify(syncExecutor).scheduleSyncLoop(any(Runnable.class));
    }

    @Test
    public void shouldResetIncompleteInspectionsWhenStarting() {

        ArrayList<Inspection> inspectionsThatGetReset = new ArrayList<>();
        inspectionsThatGetReset.add(saveWithStatus(SyncStatus.PICTURES_BEING_SYNCED));
        inspectionsThatGetReset.add(saveWithStatus(SyncStatus.INSPECTION_BEING_SYNCED));

        ArrayList<Inspection> inspectionsThatDoNotGetReset = new ArrayList<>();
        inspectionsThatDoNotGetReset.add(saveWithStatus(SyncStatus.DONE));
        inspectionsThatDoNotGetReset.add(saveWithStatus(SyncStatus.FAILED));
        inspectionsThatDoNotGetReset.add(saveWithStatus(SyncStatus.PICTURES_SYNCED));
        inspectionsThatDoNotGetReset.add(saveWithStatus(SyncStatus.READY));

        new SyncManager(fakeInspectionRepository, inspectionService, pictureService, syncExecutor);

        for (Inspection mockThatGetsReset : inspectionsThatGetReset) {
            verify(mockThatGetsReset).reset();
        }

        for (Inspection mockThatDoesNotGetsReset : inspectionsThatDoNotGetReset) {
            verify(mockThatDoesNotGetsReset, never()).reset();
        }
    }
}
