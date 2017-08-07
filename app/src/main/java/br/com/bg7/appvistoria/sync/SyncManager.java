package br.com.bg7.appvistoria.sync;

import java.util.concurrent.LinkedBlockingQueue;

import br.com.bg7.appvistoria.data.ProductInspection;
import br.com.bg7.appvistoria.data.source.local.ProductInspectionRepository;
import br.com.bg7.appvistoria.data.source.remote.PictureService;
import br.com.bg7.appvistoria.data.source.remote.ProductInspectionService;
import br.com.bg7.appvistoria.data.source.remote.callback.SyncCallback;

import static br.com.bg7.appvistoria.Constants.PENDING_INSPECTIONS_RESETTABLE_STATUS_LIST;
import static br.com.bg7.appvistoria.Constants.PENDING_INSPECTIONS_STATUS_INITIALIZATION_ORDER;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by: luciolucio
 * Date: 2017-07-31
 */

class SyncManager {

    private SyncManagerCallback callback;
    private LinkedBlockingQueue<ProductInspection> inspectionQueue = new LinkedBlockingQueue<>();

    private ProductInspectionService productInspectionService;
    private PictureService pictureService;

    private ProductInspectionRepository productInspectionRepository;
    private SyncExecutor syncExecutor;

    SyncManager(
            ProductInspectionRepository productInspectionRepository,
            ProductInspectionService productInspectionService,
            PictureService pictureService,
            SyncExecutor syncExecutor) {
        this.productInspectionRepository = checkNotNull(productInspectionRepository);
        this.productInspectionService = checkNotNull(productInspectionService);
        this.pictureService = checkNotNull(pictureService);
        this.syncExecutor = checkNotNull(syncExecutor);

        this.callback = new SyncManagerCallback(productInspectionRepository);

        initQueue();

        startQueueUpdates();
        startSync();
    }

    /**
     * Runs the initialization process
     *
     * 1. Reset inspections that failed midway through
     * 2. Queue {@link ProductInspection} items that are in the statuses
     *    between READY and DONE/FAIL
     *
     * This logic assumes that no other {@link SyncManager} is currently monitoring
     * the given {@link #productInspectionRepository}, and as such we are free to change
     * the status of the items in the repository
     */
    private void initQueue() {
        for (SyncStatus syncStatus : PENDING_INSPECTIONS_RESETTABLE_STATUS_LIST) {
            resetIncompleteInspections(syncStatus);
        }

        for (SyncStatus syncStatus : PENDING_INSPECTIONS_STATUS_INITIALIZATION_ORDER) {
            updateQueue(syncStatus);
        }
    }

    private void resetIncompleteInspections(SyncStatus syncStatus) {
        Iterable<ProductInspection> picturesSyncInpections = productInspectionRepository.findBySyncStatus(syncStatus);

        for (ProductInspection productInspection : picturesSyncInpections) {
            resetInspection(productInspection);
        }
    }

    private void resetInspection(ProductInspection productInspection) {
        if (productInspection == null) {
            return;
        }

        productInspection.reset();
        productInspectionRepository.save(productInspection);
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
        Iterable<ProductInspection> inspections = productInspectionRepository.findBySyncStatus(syncStatus);

        for (ProductInspection inspection : inspections) {
            inspectionQueue.offer(inspection);
        }
    }

    private synchronized void sync() {
        ProductInspection inspection;

        while ((inspection = inspectionQueue.poll()) != null) {
            SyncJob job = new SyncJob(inspection, productInspectionService, pictureService, callback);
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
