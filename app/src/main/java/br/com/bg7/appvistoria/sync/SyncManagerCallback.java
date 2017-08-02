package br.com.bg7.appvistoria.sync;

import java.util.HashSet;

import br.com.bg7.appvistoria.data.ProductInspection;
import br.com.bg7.appvistoria.data.source.remote.SyncCallback;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by: luciolucio
 * Date: 2017-08-02
 */

public class SyncManagerCallback implements SyncCallback {
    private HashSet<SyncCallback> subscribers = new HashSet<>();

    @Override
    public void onSuccess(ProductInspection productInspection) {
        for (SyncCallback callback : subscribers) {
            callback.onSuccess(productInspection);
        }
    }

    /**
     * Notifies all subscribers of the consolidated inspection progress
     *
     * TODO: Implementar o calculo de progresso geral da inspection
     *
     * @param productInspection The inspection being synced
     * @param progress A 0-100 number indicating percentage completed
     */
    @Override
    public void onProgressUpdated(ProductInspection productInspection, double progress) {
        for (SyncCallback callback : subscribers) {
            callback.onProgressUpdated(productInspection, progress);
        }
    }

    @Override
    public void onFailure(ProductInspection productInspection, Throwable t) {
        for (SyncCallback callback : subscribers) {
            callback.onFailure(productInspection, t);
        }
    }

    void subscribe(SyncCallback syncCallback) {
        subscribers.add(checkNotNull(syncCallback));
    }

    void unsubscribe(SyncCallback syncCallback) {
        subscribers.remove(checkNotNull(syncCallback));
    }
}
