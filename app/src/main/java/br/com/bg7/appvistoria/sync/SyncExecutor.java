package br.com.bg7.appvistoria.sync;

import br.com.bg7.appvistoria.data.ProductInspection;

/**
 * Created by: luciolucio
 * Date: 2017-07-31
 */

interface SyncExecutor {
    /**
     * Implementers must schedule the {@link Runnable} to run on a separate thread
     * and keep running it periodically
     *
     * @param runnable
     */
    void scheduleQueueUpdates(Runnable runnable);

    /**
     * Implementers must schedule the {@link Runnable} to run on a separate thread
     * and keep running it periodically
     *
     * @param runnable
     */
    void scheduleSyncLoop(Runnable runnable);

    /**
     * Implementers must check the {@link ProductInspection} and decide whether
     * they are capable of syncing either a product or a picture. If they are,
     * they must spawn a thread to run the {@link Runnable} and return true.
     *
     * If not, ignore the {@code Runnable} and return false.
     *
     * @param inspection  the inspection that is going to get synced
     * @param syncJob  the sync code to run
     * @return true if the job can be run, false if not
     */
    boolean executeSync(ProductInspection inspection, Runnable syncJob);
}
