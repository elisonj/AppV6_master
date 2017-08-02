package br.com.bg7.appvistoria.sync;

import java.util.concurrent.LinkedBlockingQueue;

import br.com.bg7.appvistoria.Constants;
import br.com.bg7.appvistoria.data.ProductInspection;
import br.com.bg7.appvistoria.data.source.PictureService;
import br.com.bg7.appvistoria.data.source.ProductInspectionService;
import br.com.bg7.appvistoria.data.source.local.ProductInspectionRepository;
import br.com.bg7.appvistoria.data.source.remote.SyncCallback;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by: luciolucio
 * Date: 2017-07-31
 */

class SyncManager {

    private SyncManagerCallback callback = new SyncManagerCallback();
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

        initQueue();

        startQueueUpdates();
        startSync();
    }

    /**
     * Initialization process
     *
     * 1. Reset PRODUCT_INSPECTION_BEING_SYNCED to READY
     * 2. Queue {@link ProductInspection} items that are in the statuses
     *    between READY and DONE/FAIL
     *
     * This logic assumes that no other {@link SyncManager} is currently monitoring
     * the given {@link #productInspectionRepository}, and that we are free to change
     * the status of the items of the {@link ProductInspectionRepository}.
     *
     * TODO: Resetar PRODUCT_INSPECTION_BEING_SYNCED para READY
     */
    private void initQueue() {
        SyncStatus[] syncStatuses = Constants.PENDING_INSPECTIONS_STATUS_INITIALIZATION_ORDER;

        for (SyncStatus syncStatus : syncStatuses) {
            updateQueue(syncStatus);
        }
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
            if (!inspectionQueue.offer(inspection)) {
                break;
            }
        }
    }

    private synchronized void sync() {
        ProductInspection inspection;

        while ((inspection = inspectionQueue.peek()) != null) {
            final ProductInspection inspectionToSync = inspection;

            boolean canSync = syncExecutor.executeSync(inspectionToSync, new Runnable() {
                @Override
                public void run() {
                    if (inspectionToSync.canSyncProduct()) {
                        inspectionToSync.sync(productInspectionService, callback);
                        return;
                    }

                    if (inspectionToSync.canSyncPictures()) {
                        inspectionToSync.sync(pictureService, callback);
                    }
                }});

            if (!canSync) {
                break;
            }

            inspectionQueue.poll();
        }
    }

    void subscribe(SyncCallback syncCallback) {
        callback.subscribe(syncCallback);
    }

    void unsubscribe(SyncCallback syncCallback) {
        callback.unsubscribe(syncCallback);
    }
}
