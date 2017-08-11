package br.com.bg7.appvistoria.sync;

import org.junit.Test;

import java.util.ArrayList;

import br.com.bg7.appvistoria.data.Inspection;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * Created by: luciolucio
 * Date: 2017-08-02
 */

public class SyncManagerInitializationTest extends SyncManagerTestBase {

    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullRepository() {
        new SyncManager(null, productInspectionService, pictureService, syncExecutor);
    }

    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullExecutor() {
        new SyncManager(productInspectionRepository, productInspectionService, pictureService, null);
    }

    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullProductInspectionService() {
        new SyncManager(productInspectionRepository, null, pictureService, syncExecutor);
    }

    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullPictureService() {
        new SyncManager(productInspectionRepository, productInspectionService, null, syncExecutor);
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
    public void shouldResetIncompleteProductInspectionsWhenStarting() {

        ArrayList<Inspection> inspectionsThatGetReset = new ArrayList<>();
        inspectionsThatGetReset.add(saveWithStatus(SyncStatus.PICTURES_BEING_SYNCED));
        inspectionsThatGetReset.add(saveWithStatus(SyncStatus.INSPECTION_BEING_SYNCED));

        ArrayList<Inspection> inspectionsThatDoNotGetReset = new ArrayList<>();
        inspectionsThatDoNotGetReset.add(saveWithStatus(SyncStatus.DONE));
        inspectionsThatDoNotGetReset.add(saveWithStatus(SyncStatus.FAILED));
        inspectionsThatDoNotGetReset.add(saveWithStatus(SyncStatus.PICTURES_SYNCED));
        inspectionsThatDoNotGetReset.add(saveWithStatus(SyncStatus.READY));

        new SyncManager(productInspectionRepository, productInspectionService, pictureService, syncExecutor);

        for (Inspection mockThatGetsReset : inspectionsThatGetReset) {
            verify(mockThatGetsReset).reset();
        }

        for (Inspection mockThatDoesNotGetsReset : inspectionsThatDoNotGetReset) {
            verify(mockThatDoesNotGetsReset, never()).reset();
        }
    }
}
