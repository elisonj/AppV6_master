package br.com.bg7.appvistoria.sync;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by: elison
 * Date: 2017-08-03
 */

public class SeparateThreadExecutor implements SyncExecutor {

    private final long queueUpdateIntervalMillis;
    private final long syncLoopIntervalMillis;

    private ScheduledExecutorService queueUpdateService = Executors.newSingleThreadScheduledExecutor();
    private ScheduledExecutorService syncLoopService = Executors.newSingleThreadScheduledExecutor();
    private ExecutorService syncService;

    public SeparateThreadExecutor(int maxSyncJobs, long queueUpdateIntervalMillis, long syncLoopIntervalMillis) {
        this.queueUpdateIntervalMillis = queueUpdateIntervalMillis;
        this.syncLoopIntervalMillis = syncLoopIntervalMillis;

        this.syncService = Executors.newFixedThreadPool(maxSyncJobs);
    }

    @Override
    public void scheduleQueueUpdates(Runnable runnable) {
        queueUpdateService.schedule(runnable, queueUpdateIntervalMillis, TimeUnit.MILLISECONDS);
    }

    @Override
    public void scheduleSyncLoop(Runnable runnable) {
        syncLoopService.schedule(runnable, syncLoopIntervalMillis, TimeUnit.MILLISECONDS);
    }

    @Override
    public void executeSync(Runnable syncJob) {
        syncService.submit(syncJob);
    }
}
