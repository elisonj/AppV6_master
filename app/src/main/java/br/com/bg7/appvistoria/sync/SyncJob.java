package br.com.bg7.appvistoria.sync;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.bg7.appvistoria.data.Inspection;
import br.com.bg7.appvistoria.data.source.remote.InspectionService;
import br.com.bg7.appvistoria.data.source.remote.PictureService;
import br.com.bg7.appvistoria.data.source.remote.callback.SyncCallback;

/**
 * Created by: luciolucio
 * Date: 2017-08-02
 */
class SyncJob implements Runnable {
    private final Inspection inspectionToSync;
    private InspectionService inspectionService;
    private PictureService pictureService;
    private SyncCallback callback;

    private static final Logger LOG = LoggerFactory.getLogger(SyncJob.class);

    SyncJob(Inspection inspectionToSync, InspectionService inspectionService, PictureService pictureService, SyncCallback callback) {
        this.inspectionToSync = inspectionToSync;
        this.inspectionService = inspectionService;
        this.pictureService = pictureService;
        this.callback = callback;
    }

    @Override
    public void run() {
        try {
            if (inspectionToSync.canSyncPictures()) {
                inspectionToSync.sync(pictureService, callback);
                return;
            }

            if (inspectionToSync.canSyncProduct()) {
                inspectionToSync.sync(inspectionService, callback);
            }
        } catch (IllegalStateException exception) {
            // Concurrency problem that should not happen considering that
            // a truthy answer from canSync() guarantees you can call sync()
            //
            // It's fine to simply ignore this exception because:
            // - If there was a concurrency problem, another thread would have synced it
            // - If no threads synced it, it can get picked up per status or get reset
            //   by a new SyncManager (i.e. close/restart app)

            LOG.warn("Problema inesperado de concorrencia durante o sync", exception);
            LOG.warn("Inspecao: {}", inspectionToSync.toString());
        }
    }
}
