package br.com.bg7.appvistoria.sync;

import java.util.concurrent.LinkedBlockingQueue;

import br.com.bg7.appvistoria.data.ProductInspection;
import br.com.bg7.appvistoria.data.source.PictureService;
import br.com.bg7.appvistoria.data.source.ProductInspectionService;
import br.com.bg7.appvistoria.data.source.local.ProductInspectionRepository;
import br.com.bg7.appvistoria.data.source.remote.SyncCallback;

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
     * 1. Reset PRODUCT_INSPECTION_BEING_SYNCED to READY
     * 2. Queue {@link ProductInspection} items that are in the statuses
     *    between READY and DONE/FAIL
     *
     * This logic assumes that no other {@link SyncManager} is currently monitoring
     * the given {@link #productInspectionRepository}, and that we are free to change
     * the status of the items of the {@link ProductInspectionRepository}.
     */
    private void initQueue() {
        resetIncompleteInspections();

        for (SyncStatus syncStatus : PENDING_INSPECTIONS_STATUS_INITIALIZATION_ORDER) {
            updateQueue(syncStatus);
        }
    }

    private void resetIncompleteInspections() {
        Iterable<ProductInspection> productInspections = productInspectionRepository.findBySyncStatus(SyncStatus.INSPECTION_BEING_SYNCED);

        for (ProductInspection productInspection : productInspections) {
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
