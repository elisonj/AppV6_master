package br.com.bg7.appvistoria.data;

import com.orm.SugarRecord;

import java.io.File;
import java.io.SyncFailedException;
import java.util.ArrayList;
import java.util.List;

import br.com.bg7.appvistoria.data.source.PictureService;
import br.com.bg7.appvistoria.data.source.ProductInspectionService;
import br.com.bg7.appvistoria.data.source.remote.HttpProgressCallback;
import br.com.bg7.appvistoria.data.source.remote.HttpResponse;
import br.com.bg7.appvistoria.data.source.remote.SyncCallback;
import br.com.bg7.appvistoria.data.source.remote.dto.PictureResponse;
import br.com.bg7.appvistoria.sync.SyncStatus;

/**
 * Created by: elison
 * Date: 2017-07-27
 */
public class ProductInspection extends SugarRecord<ProductInspection> {

    private SyncStatus syncStatus = null;
    private ProductInspectionCallback productInspectionCallback;

    private long id;
    private Product product;
    private List<File> images = new ArrayList<File>();

    /**
     * Default constructor used by Sugar
     */
    @SuppressWarnings("unused")
    public ProductInspection() {}

    public SyncStatus getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(SyncStatus syncStatus) {
        this.syncStatus = syncStatus;
    }

    public boolean canSyncProduct() {
        return false;
    }

    public boolean canSyncPictures() {
        return false;
    }

    public SyncStatus ready() throws SyncFailedException {
        if(syncStatus == null || syncStatus == SyncStatus.READY) {
            syncStatus = SyncStatus.READY;
            return syncStatus;
        }

        throw new SyncFailedException("Cannot sync");
    }

    public void sync(PictureService pictureService, final SyncCallback syncCallback) {

        File image = images.get(0);

        pictureService.send(image, this, new HttpProgressCallback<PictureResponse>() {
            @Override
            public void onProgressUpdated(double percentage) {
                syncCallback.onProgressUpdated(ProductInspection.this, percentage);
            }

            @Override
            public void onResponse(HttpResponse<PictureResponse> httpResponse) {
                syncCallback.onSuccess(ProductInspection.this);
            }

            @Override
            public void onFailure(Throwable t) {
                syncCallback.onFailure(ProductInspection.this, t);
            }
        });
    }

    public void sync(ProductInspectionService productInspectionService, final SyncCallback syncCallback) {
        try {
            if (ready() == SyncStatus.READY) {
                syncStatus = SyncStatus.PRODUCT_INSPECTION_BEING_SYNCED;
                productInspectionCallback = new ProductInspectionCallback(this, syncCallback);
                productInspectionService.send(this, productInspectionCallback);
            }
        } catch (SyncFailedException ex) {
            ex.printStackTrace();;
        }
    }

    public ProductInspectionCallback getProductInspectionCallback() {
        return productInspectionCallback;
    }
}
