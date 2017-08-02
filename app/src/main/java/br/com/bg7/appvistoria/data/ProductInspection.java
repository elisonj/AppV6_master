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
import br.com.bg7.appvistoria.data.source.remote.dto.ProductResponse;
import br.com.bg7.appvistoria.sync.SyncStatus;

import static br.com.bg7.appvistoria.sync.SyncStatus.PRODUCT_INSPECTION_SYNCED;

/**
 * Created by: elison
 * Date: 2017-07-27
 */
public class ProductInspection extends SugarRecord<ProductInspection> {

    private SyncStatus syncStatus = null;

    private long id;
    private Product product;
    private List<File> images = new ArrayList<File>();

    /**
     * Default constructor used by Sugar
     */
    @SuppressWarnings("unused")
    public ProductInspection() {}

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
        if (syncStatus != SyncStatus.PRODUCT_INSPECTION_BEING_SYNCED) {
            File image = images.get(0);
            pictureService.send(image, this, new HttpProgressCallback<PictureResponse>() {
                @Override
                public void onProgressUpdated(double percentage) {
                    syncCallback.onProgressUpdated(ProductInspection.this, percentage);
                }

                @Override
                public void onResponse(HttpResponse<PictureResponse> httpResponse) {
                    syncStatus = SyncStatus.PRODUCT_INSPECTION_SYNCED;
                    syncCallback.onSuccess(ProductInspection.this);
                }

                @Override
                public void onFailure(Throwable t) {
                    syncStatus = SyncStatus.FAILED;
                    syncCallback.onFailure(ProductInspection.this, t);
                }
            });
        }
    }

    public void sync(ProductInspectionService productInspectionService, final SyncCallback syncCallback) {
        try {
            if (ready() == SyncStatus.READY) {
                syncStatus = SyncStatus.PRODUCT_INSPECTION_BEING_SYNCED;
                productInspectionService.send(this, new HttpProgressCallback<ProductResponse>() {
                    @Override
                    public void onProgressUpdated(double percentage) {
                        syncCallback.onProgressUpdated(ProductInspection.this, percentage);
                    }

                    @Override
                    public void onResponse(HttpResponse<ProductResponse> httpResponse) {
                        syncStatus = PRODUCT_INSPECTION_SYNCED;
                        syncCallback.onSuccess(ProductInspection.this);
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        syncStatus = SyncStatus.FAILED;
                        syncCallback.onFailure(ProductInspection.this, t);
                    }
                });
            }
        } catch (SyncFailedException ex) {
            ex.printStackTrace();
        }
    }

    public SyncStatus getSyncStatus() {
        return syncStatus;
    }
}
