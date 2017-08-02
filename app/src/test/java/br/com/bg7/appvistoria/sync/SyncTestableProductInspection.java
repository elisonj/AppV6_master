package br.com.bg7.appvistoria.sync;

import br.com.bg7.appvistoria.data.ProductInspection;
import br.com.bg7.appvistoria.data.source.PictureService;
import br.com.bg7.appvistoria.data.source.ProductInspectionService;
import br.com.bg7.appvistoria.data.source.remote.SyncCallback;

/**
 * Created by: luciolucio
 * Date: 2017-08-02
 */

class SyncTestableProductInspection extends ProductInspection {
    SyncTestableProductInspection(SyncStatus syncStatus) {
        super(syncStatus);
    }

    @Override
    public void sync(ProductInspectionService productInspectionService, SyncCallback callback) {
        super.sync(productInspectionService, callback);
    }

    @Override
    public void sync(PictureService pictureService, SyncCallback callback) {
        super.sync(pictureService, callback);
    }
}
