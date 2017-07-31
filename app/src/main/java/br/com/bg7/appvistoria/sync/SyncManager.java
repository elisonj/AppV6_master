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

    public SyncManager(int queueSize, ProductInspectionRepository productInspectionRepository) {
        inspectionQueue = new ArrayBlockingQueue<>(queueSize);
        this.productInspectionRepository = checkNotNull(productInspectionRepository);

        init();
    }

    private void init() {

    }

    void subscribe(SyncCallback syncCallback) {
        subscribers.add(checkNotNull(syncCallback));
    }

    void unsubscribe(SyncCallback syncCallback) {
        subscribers.remove(checkNotNull(syncCallback));
    }
}
