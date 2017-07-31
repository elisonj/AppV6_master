package br.com.bg7.appvistoria.sync;

import com.google.common.collect.Sets;

import java.util.HashSet;
import java.util.concurrent.ArrayBlockingQueue;

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

    private static final int LOCAL_QUEUE_SIZE = 5;
    private ArrayBlockingQueue<ProductInspection> inspectionQueue = new ArrayBlockingQueue<>(LOCAL_QUEUE_SIZE);

    private ProductInspectionRepository productInspectionRepository;

    SyncManager(ProductInspectionRepository productInspectionRepository, SyncExecutor syncExecutor) {
        this.productInspectionRepository = checkNotNull(productInspectionRepository);
        syncExecutor = checkNotNull(syncExecutor);

        initQueue(syncExecutor);
    }

    private void initQueue(SyncExecutor syncExecutor) {
        checkForInspections();
        syncExecutor.scheduleQueueUpdates(new Runnable() {
            @Override
            public void run() {
                checkForInspections();
            }
        });
    }

    /**
     * Queues any new inspections by status
     *
     * If we need to, we can optimize here by checking {@link ArrayBlockingQueue#remainingCapacity()}
     */
    private void checkForInspections() {
        for (SyncStatus status : Constants.PENDING_INSPECTIONS_STATUS_INITIALIZATION_ORDER) {
            queueNewInspectionsWithStatus(status);
        }
    }

    private synchronized void queueNewInspectionsWithStatus(SyncStatus status) {
        Iterable<ProductInspection> inspectionsFromDatabase = productInspectionRepository.findBySyncStatus(status);
        ProductInspection[] inspectionsFromQueue = (ProductInspection[]) inspectionQueue.toArray();

        HashSet<ProductInspection> inspectionSetFromDatabase = Sets.newHashSet(inspectionsFromDatabase);
        HashSet<ProductInspection> inspectionSetFromQueue = Sets.newHashSet(inspectionsFromQueue);

        Sets.SetView<ProductInspection> newInspections = Sets.difference(inspectionSetFromDatabase, inspectionSetFromQueue);
        for (ProductInspection inspection : newInspections) {
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
