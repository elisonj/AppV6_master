package br.com.bg7.appvistoria.data;

import com.orm.SugarRecord;

import java.io.File;
import java.util.Vector;

import br.com.bg7.appvistoria.data.source.PictureService;
import br.com.bg7.appvistoria.data.source.ProductInspectionService;
import br.com.bg7.appvistoria.data.source.remote.HttpProgressCallback;
import br.com.bg7.appvistoria.data.source.remote.HttpResponse;
import br.com.bg7.appvistoria.data.source.remote.SyncCallback;
import br.com.bg7.appvistoria.data.source.remote.dto.PictureResponse;
import br.com.bg7.appvistoria.data.source.remote.dto.ProductResponse;
import br.com.bg7.appvistoria.sync.SyncStatus;

/**
 * Created by: elison
 * Date: 2017-07-27
 */
public class ProductInspection extends SugarRecord<ProductInspection> {

    public SyncStatus syncStatus = null;

    private long id;
    private Product product;
    private Vector<File> imagesToSync = new Vector<File>();
    private Vector<File> imagesSynced = new Vector<File>();

    /**
     * Default constructor used by Sugar
     */
    @SuppressWarnings("unused")
    public ProductInspection() {}

    public ProductInspection(SyncStatus syncStatus) {
        this.syncStatus = syncStatus;
    }

    public boolean canSyncProduct() {
        if(syncStatus == SyncStatus.PICTURES_SYNCED) {
            return true;
        }
        return false;
    }

    public boolean canSyncPictures() {
        if(syncStatus == SyncStatus.READY || syncStatus == SyncStatus.PICTURES_BEING_SYNCED) {
            return true;
        }
        return false;
    }

    public SyncStatus ready() throws IllegalStateException {
        if(syncStatus == null || syncStatus == SyncStatus.READY) {
            syncStatus = SyncStatus.READY;
            return syncStatus;
        }

        throw new IllegalStateException("Cannot sync when status is "+syncStatus);
    }

    public void sync(PictureService pictureService, final SyncCallback syncCallback) {

        if (canSyncPictures()) {

            if(countImagesToSync() > 0) {

                syncStatus = SyncStatus.PICTURES_BEING_SYNCED;

                final File image = getImageToSync();
                pictureService.send(image, this, new HttpProgressCallback<PictureResponse>() {
                    @Override
                    public void onProgressUpdated(double percentage) {
                        syncCallback.onProgressUpdated(ProductInspection.this, percentage);
                    }

                    @Override
                    public void onResponse(HttpResponse<PictureResponse> httpResponse) {
                        addImageSynced(image);
                        if(countImagesToSync() == 0) {
                            syncStatus = SyncStatus.PICTURES_SYNCED;
                        }
                        syncCallback.onSuccess(ProductInspection.this);
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        returnImageToSync(image);
                        syncStatus = SyncStatus.FAILED;
                        syncCallback.onFailure(ProductInspection.this, t);
                    }
                });
            }
        }
    }

    public void sync(ProductInspectionService productInspectionService, final SyncCallback syncCallback) {
            if (canSyncProduct()) {
                syncStatus = SyncStatus.INSPECTION_BEING_SYNCED;
                productInspectionService.send(this, new HttpProgressCallback<ProductResponse>() {
                    @Override
                    public void onProgressUpdated(double percentage) {
                        syncCallback.onProgressUpdated(ProductInspection.this, percentage);
                    }

                    @Override
                    public void onResponse(HttpResponse<ProductResponse> httpResponse) {
                        syncStatus = SyncStatus.DONE;
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

    private synchronized int countImagesToSync() {
        if(imagesToSync != null && imagesToSync.size() > 0) {
            return imagesToSync.size();
        }
        return 0;
    }

    public synchronized void addImageToSync(File image) {
        imagesToSync.add(image);
    }

    private synchronized File getImageToSync() {
        File image = imagesToSync.get(0);
        imagesToSync.remove(0);
        return image;
    }

    private synchronized void returnImageToSync(File image) {
        imagesToSync.add(image);
    }

    private synchronized void addImageSynced(File image) {
        imagesSynced.add(image);
    }

    public SyncStatus getSyncStatus() {
        return syncStatus;
    }

    public void reset() {
        syncStatus = SyncStatus.READY;
    }
}
