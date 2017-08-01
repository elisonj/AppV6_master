package br.com.bg7.appvistoria.sync;

import java.util.HashSet;
import java.util.concurrent.LinkedBlockingQueue;

import br.com.bg7.appvistoria.Constants;
import br.com.bg7.appvistoria.data.ProductInspection;
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

    private ProductInspectionRepository productInspectionRepository;
    private SyncExecutor syncExecutor;

    SyncManager(ProductInspectionRepository productInspectionRepository, SyncExecutor syncExecutor) {
        this.productInspectionRepository = checkNotNull(productInspectionRepository);
        this.syncExecutor = checkNotNull(syncExecutor);

        initQueue();

        startQueueUpdates();
        startSync();
    }

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
                checkForInspections();
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

    private void checkForInspections() {
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
        while ((inspection = inspectionQueue.poll()) != null) {
        }
    }

    void subscribe(SyncCallback syncCallback) {
        subscribers.add(checkNotNull(syncCallback));
    }

    void unsubscribe(SyncCallback syncCallback) {
        subscribers.remove(checkNotNull(syncCallback));
    }
}
