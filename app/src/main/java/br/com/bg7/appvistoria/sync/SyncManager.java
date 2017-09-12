package br.com.bg7.appvistoria.sync;

import java.util.concurrent.LinkedBlockingQueue;

import br.com.bg7.appvistoria.data.Inspection;
import br.com.bg7.appvistoria.data.source.local.InspectionRepository;
import br.com.bg7.appvistoria.data.source.remote.InspectionService;
import br.com.bg7.appvistoria.data.source.remote.PictureService;
import br.com.bg7.appvistoria.data.source.remote.callback.SyncCallback;

import static br.com.bg7.appvistoria.Constants.PENDING_INSPECTIONS_RESETTABLE_STATUS_LIST;
import static br.com.bg7.appvistoria.Constants.PENDING_INSPECTIONS_STATUS_INITIALIZATION_ORDER;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by: luciolucio
 * Date: 2017-07-31
 */

public class SyncManager {

    private SyncManagerCallback callback;
    private LinkedBlockingQueue<Inspection> inspectionQueue = new LinkedBlockingQueue<>();

    private InspectionService inspectionService;
    private PictureService pictureService;

    private InspectionRepository inspectionRepository;
    private SyncExecutor syncExecutor;

    public SyncManager(
            InspectionRepository inspectionRepository,
            InspectionService inspectionService,
            PictureService pictureService,
            SyncExecutor syncExecutor) {
        this.inspectionRepository = checkNotNull(inspectionRepository);
        this.inspectionService = checkNotNull(inspectionService);
        this.pictureService = checkNotNull(pictureService);
        this.syncExecutor = checkNotNull(syncExecutor);

        this.callback = new SyncManagerCallback(inspectionRepository);

        initQueue();

        startQueueUpdates();
        startSync();
    }

    /**
     * Runs the initialization process
     *
     * 1. Reset inspections that failed midway through
     * 2. Queue {@link Inspection} items that are in the statuses
     *    between READY and DONE/FAIL
     *
     * This logic assumes that no other {@link SyncManager} is currently monitoring
     * the given {@link #inspectionRepository}, and as such we are free to change
     * the status of the items in the repository
     */
    private void initQueue() {
        for (SyncStatus syncStatus : PENDING_INSPECTIONS_RESETTABLE_STATUS_LIST) {
            resetInspections(syncStatus);
        }

        for (SyncStatus syncStatus : PENDING_INSPECTIONS_STATUS_INITIALIZATION_ORDER) {
            updateQueue(syncStatus);
        }
    }

    private void resetInspections(SyncStatus syncStatus) {
        Iterable<Inspection> inspectionsToReset = inspectionRepository.findBySyncStatus(syncStatus);

        for (Inspection inspection : inspectionsToReset) {
            resetInspection(inspection);
        }
    }

    private void resetInspection(Inspection inspection) {
        if (inspection == null) {
            return;
        }

        inspection.reset();
        inspectionRepository.save(inspection);
    }

    private void startQueueUpdates() {
        syncExecutor.scheduleQueueUpdates(new Runnable() {
            @Override
            public void run() {
                updateQueue();
            }
        });
    }

    private void startSync() {
        syncExecutor.scheduleSyncLoop(new Runnable() {
            @Override
            public void run() {
                sync();
            }
        });
    }

    private void updateQueue() {
        updateQueue(SyncStatus.READY);
    }

    private synchronized void updateQueue(SyncStatus syncStatus) {
        Iterable<Inspection> inspections = inspectionRepository.findBySyncStatus(syncStatus);

        for (Inspection inspection : inspections) {
            inspectionQueue.offer(inspection);
        }
    }

    private synchronized void sync() {
        Inspection inspection;

        while ((inspection = inspectionQueue.poll()) != null) {
            SyncJob job = new SyncJob(inspection, inspectionService, pictureService, callback);
            syncExecutor.executeSync(job);
        }
    }

    void subscribe(SyncCallback syncCallback) {
        callback.subscribe(syncCallback);
    }

    void unsubscribe(SyncCallback syncCallback) {
        callback.unsubscribe(syncCallback);
    }
}
