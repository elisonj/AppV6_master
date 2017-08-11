package br.com.bg7.appvistoria.data;

import com.orm.SugarRecord;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import br.com.bg7.appvistoria.data.source.remote.PictureService;
import br.com.bg7.appvistoria.data.source.remote.ProductInspectionService;
import br.com.bg7.appvistoria.data.source.remote.callback.SyncCallback;
import br.com.bg7.appvistoria.data.source.remote.dto.PictureResponse;
import br.com.bg7.appvistoria.data.source.remote.dto.ProductResponse;
import br.com.bg7.appvistoria.data.source.remote.http.HttpProgressCallback;
import br.com.bg7.appvistoria.data.source.remote.http.HttpResponse;
import br.com.bg7.appvistoria.sync.PictureSyncStatus;
import br.com.bg7.appvistoria.sync.SyncStatus;

/**
 * Created by: elison
 * Date: 2017-07-27
 */
public class ProductInspection extends SugarRecord<ProductInspection> {

    private SyncStatus syncStatus = null;

    private List<ProductFile> imagesToSync = new ArrayList<>();

    /**
     * Default constructor used by Sugar
     */
    @SuppressWarnings("unused")
    public ProductInspection() {}

    public boolean canSyncProduct() {
        return syncStatus == SyncStatus.PICTURES_SYNCED;
    }

    public boolean canSyncPictures() {
        return syncStatus == SyncStatus.READY || syncStatus == SyncStatus.PICTURES_BEING_SYNCED;
    }

    public synchronized void sync(PictureService pictureService, final SyncCallback syncCallback) {

        if (!canSyncPictures()) {
            throw new IllegalStateException("Cannot sync Pictures when status is "+syncStatus);
        }

        final ProductFile productFile = getNextImageReady();

        if(productFile == null) {
            throw new IllegalStateException("No Pictures to send ");
        }

        syncStatus = SyncStatus.PICTURES_BEING_SYNCED;
        productFile.setSyncStatus(PictureSyncStatus.BEING_SYNCED);

        pictureService.send(productFile.getFile(), this, new HttpProgressCallback<PictureResponse>() {
            @Override
            public void onProgressUpdated(double percentage) {
                syncCallback.onProgressUpdated(ProductInspection.this, percentage);
            }

            @Override
            public void onResponse(HttpResponse<PictureResponse> httpResponse) {
                if(httpResponse == null) {
                    onFailure(new NullPointerException());
                    return;
                }
                if(!httpResponse.isSuccessful()) {
                    onFailure(new IllegalStateException("Response invalid"));
                    return;
                }

                productFile.setSyncStatus(PictureSyncStatus.DONE);

                if(countImagesNotDone() == 0) {
                    syncStatus = SyncStatus.PICTURES_SYNCED;
                }
                syncCallback.onSuccess(ProductInspection.this);
            }

            @Override
            public void onFailure(Throwable t) {
                syncStatus = SyncStatus.FAILED;
                syncCallback.onFailure(ProductInspection.this, t);
            }
        });
    }

    public synchronized void sync(ProductInspectionService productInspectionService, final SyncCallback syncCallback) {
        if (!canSyncProduct()) {
            throw new IllegalStateException("Cannot sync when status is "+syncStatus);
        }

        syncStatus = SyncStatus.INSPECTION_BEING_SYNCED;
        productInspectionService.send(this, new HttpProgressCallback<ProductResponse>() {
            @Override
            public void onProgressUpdated(double percentage) {
                syncCallback.onProgressUpdated(ProductInspection.this, percentage);
            }

            @Override
            public void onResponse(HttpResponse<ProductResponse> httpResponse) {
                if(httpResponse == null) {
                    onFailure(null);
                    return;
                }
                if(httpResponse.isSuccessful()) {
                    syncStatus = SyncStatus.DONE;
                    syncCallback.onSuccess(ProductInspection.this);
                    return;
                }
                onFailure(null);
            }

            @Override
            public void onFailure(Throwable t) {
                syncStatus = SyncStatus.FAILED;
                syncCallback.onFailure(ProductInspection.this, t);
            }
        });
    }

    synchronized void addImageToSync(File image) {
        ProductFile productFile = new ProductFile(this, image);
        imagesToSync.add(productFile);
    }

    public SyncStatus getSyncStatus() {
        if(syncStatus == null) syncStatus = SyncStatus.READY;
        return syncStatus;
    }

    public void reset() {
        for(ProductFile productFile: imagesToSync) {
            if(productFile.getSyncStatus() == PictureSyncStatus.BEING_SYNCED) {
                productFile.setSyncStatus(PictureSyncStatus.NOT_STARTED);
            }
        }
        if(syncStatus == SyncStatus.INSPECTION_BEING_SYNCED) {
            syncStatus = SyncStatus.PICTURES_SYNCED;
        }
    }

    private int countImagesNotDone() {
        int countImagesNotDone = 0;
        for(ProductFile productFile: imagesToSync) {
            if(productFile.getSyncStatus() != PictureSyncStatus.DONE) countImagesNotDone++;
        }
        return countImagesNotDone;
    }

    private ProductFile getNextImageReady() {
        if(imagesToSync.size() <= 0) return null;

        for(ProductFile productFile: imagesToSync) {
            if(productFile.getSyncStatus() == PictureSyncStatus.NOT_STARTED)
                return productFile;
        }
        return null;
    }
}
