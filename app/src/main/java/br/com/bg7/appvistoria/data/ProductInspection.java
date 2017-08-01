package br.com.bg7.appvistoria.data;

import com.orm.SugarRecord;

import br.com.bg7.appvistoria.data.source.PictureService;
import br.com.bg7.appvistoria.data.source.ProductInspectionService;
import br.com.bg7.appvistoria.data.source.remote.SyncCallback;
import br.com.bg7.appvistoria.sync.SyncStatus;

import br.com.bg7.appvistoria.data.source.ProductInspectionService;
import br.com.bg7.appvistoria.data.source.remote.HttpProgressCallback;
import br.com.bg7.appvistoria.data.source.remote.HttpResponse;
import br.com.bg7.appvistoria.data.source.remote.SyncCallback;
import br.com.bg7.appvistoria.data.source.remote.dto.ProductResponse;

/**
 * Created by: elison
 * Date: 2017-07-27
 */
public class ProductInspection extends SugarRecord<ProductInspection> {

    private SyncStatus syncStatus;

    private long id;
    private Product product;

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

    public void sync(ProductInspectionService productInspectionService, final SyncCallback syncCallback) {

        productInspectionService.send(this, new HttpProgressCallback<ProductResponse>() {
            @Override
            public void onProgressUpdated(double percentage) {
                syncCallback.onProgressUpdated(ProductInspection.this, percentage);
            }

            @Override
            public void onResponse(HttpResponse<ProductResponse> httpResponse) {
                syncCallback.onSuccess(ProductInspection.this);
            }

            @Override
            public void onFailure(Throwable t) {
                syncCallback.onFailure(ProductInspection.this, t);
            }
        });
    }
}
