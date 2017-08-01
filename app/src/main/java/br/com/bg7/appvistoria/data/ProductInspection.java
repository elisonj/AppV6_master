package br.com.bg7.appvistoria.data;

import com.orm.SugarRecord;

import br.com.bg7.appvistoria.data.source.PictureService;
import br.com.bg7.appvistoria.data.source.ProductInspectionService;
import br.com.bg7.appvistoria.data.source.remote.SyncCallback;
import br.com.bg7.appvistoria.sync.SyncStatus;

/**
 * Created by: elison
 * Date: 2017-07-27
 */
public class ProductInspection extends SugarRecord<ProductInspection> {

    private SyncStatus syncStatus;

    /**
     * Default constructor used by Sugar
     */
    @SuppressWarnings("unused")
    public ProductInspection() {}

    public SyncStatus getSyncStatus() {
        return syncStatus;
    }

    public boolean canSyncProduct() {
        return false;
    }

    public boolean canSyncPictures() {
        return false;
    }

    public void sync(ProductInspectionService productInspectionService, SyncCallback callback) {

    }

    public void sync(PictureService pictureService, SyncCallback callback) {

    }
}
