package br.com.bg7.appvistoria.sync;

import java.util.HashSet;
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

    private HashSet<SyncCallback> subscribers = new HashSet<>();
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
     * Aqui é necessário resetar PRODUCT_INSPECTION_BEING_SYNCED para READY
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
                        inspectionToSync.sync(productInspectionService, null);
                        return;
                    }

                    if (inspectionToSync.canSyncPictures()) {
                        inspectionToSync.sync(pictureService, null);
                    }
                }});

            if (!canSync) {
                break;
            }

            inspectionQueue.poll();
        }
    }

    void subscribe(SyncCallback syncCallback) {
        subscribers.add(checkNotNull(syncCallback));
    }

    void unsubscribe(SyncCallback syncCallback) {
        subscribers.remove(checkNotNull(syncCallback));
    }
}
