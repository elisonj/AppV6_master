package br.com.bg7.appvistoria.sync;

import br.com.bg7.appvistoria.data.ProductInspection;

/**
 * Created by: luciolucio
 * Date: 2017-07-31
 */

interface SyncExecutor {
    void scheduleQueueUpdates(Runnable runnable);

    void scheduleSyncLoop(Runnable runnable);

    boolean executeSync(ProductInspection inspection);
}
