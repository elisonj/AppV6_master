package br.com.bg7.appvistoria.sync;

import br.com.bg7.appvistoria.data.ProductInspection;
import br.com.bg7.appvistoria.data.source.PictureService;
import br.com.bg7.appvistoria.data.source.ProductInspectionService;
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

        }
    }
}
