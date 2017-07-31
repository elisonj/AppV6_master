package br.com.bg7.appvistoria.sync;

import java.util.HashSet;
import java.util.concurrent.ArrayBlockingQueue;

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
    private ProductInspectionRepository productInspectionRepository;
    private ArrayBlockingQueue<ProductInspection> inspectionQueue;
    private SyncStatus[] statusInitializationOrder = {
            SyncStatus.PICTURES_BEING_SYNCED,
            SyncStatus.PRODUCT_INSPECTION_SYNCED,
            SyncStatus.PRODUCT_INSPECTION_BEING_SYNCED,
            SyncStatus.READY
    };

    SyncManager(int queueSize, ProductInspectionRepository productInspectionRepository) {
        inspectionQueue = new ArrayBlockingQueue<>(queueSize);
        this.productInspectionRepository = checkNotNull(productInspectionRepository);

        initQueue();
    }

    private void initQueue() {
        for (SyncStatus status : statusInitializationOrder) {
            queueInspectionsWithStatus(status);
        }
    }

    private void queueInspectionsWithStatus(SyncStatus status) {
        Iterable<ProductInspection> inspections = productInspectionRepository.findBySyncStatus(status);

        for (ProductInspection inspection : inspections) {
            if (!inspectionQueue.offer(inspection)) {
                break;
            }
        }
    }

    void subscribe(SyncCallback syncCallback) {
        subscribers.add(checkNotNull(syncCallback));
    }

    void unsubscribe(SyncCallback syncCallback) {
        subscribers.remove(checkNotNull(syncCallback));
    }
}
