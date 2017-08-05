package br.com.bg7.appvistoria.sync;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.bg7.appvistoria.data.ProductInspection;
import br.com.bg7.appvistoria.data.source.remote.PictureService;
import br.com.bg7.appvistoria.data.source.remote.ProductInspectionService;
import br.com.bg7.appvistoria.data.source.remote.SyncCallback;

/**
 * Created by: luciolucio
 * Date: 2017-08-02
 */
class SyncJob implements Runnable {
    private final ProductInspection inspectionToSync;
    private ProductInspectionService productInspectionService;
    private PictureService pictureService;
    private SyncCallback callback;

    private static final Logger LOG = LoggerFactory.getLogger(SyncJob.class);

    SyncJob(ProductInspection inspectionToSync, ProductInspectionService productInspectionService, PictureService pictureService, SyncCallback callback) {
        this.inspectionToSync = inspectionToSync;
        this.productInspectionService = productInspectionService;
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
                inspectionToSync.sync(productInspectionService, callback);
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
