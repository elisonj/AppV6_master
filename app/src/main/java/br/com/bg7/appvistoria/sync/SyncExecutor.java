package br.com.bg7.appvistoria.sync;

/**
 * Created by: elison
 * Date: 2017-07-31
 */

public interface SyncExecutor {
    /**
     * Implementers must schedule the {@link Runnable} to run on a single separate thread
     * and keep running it periodically
     *
     * @param runnable the sync loop to run
     */
    void scheduleQueueUpdates(Runnable runnable);

    /**
     * Implementers must schedule the {@link Runnable} to run on a single separate thread
     * and keep running it periodically
     *
     * @param runnable the sync loop to run
     */
    void scheduleSyncLoop(Runnable runnable);

    /**
     * Implementers must spawn a thread to run the provided {@code Runnable}. If they
     * cannot run it right away, they must queue the request until they have resources
     * to create a new thread that can run it
     *
     * @param syncJob  the sync code to run
     */
    void executeSync(Runnable syncJob);
}
