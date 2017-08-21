package br.com.bg7.appvistoria.data;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import br.com.bg7.appvistoria.data.source.remote.InspectionService;
import br.com.bg7.appvistoria.data.source.remote.PictureService;
import br.com.bg7.appvistoria.data.source.remote.callback.SyncCallback;
import br.com.bg7.appvistoria.data.source.remote.dto.PictureResponse;
import br.com.bg7.appvistoria.data.source.remote.dto.ProductResponse;
import br.com.bg7.appvistoria.data.source.remote.http.HttpProgressCallback;
import br.com.bg7.appvistoria.data.source.remote.http.HttpResponse;
import br.com.bg7.appvistoria.sync.PictureSyncStatus;
import br.com.bg7.appvistoria.sync.SyncStatus;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by: elison
 * Date: 2017-07-27
 */
@DatabaseTable(tableName = "inspections")
public class Inspection {

    @DatabaseField(generatedId = true)
    private Long id;

    @DatabaseField(index = true, canBeNull = false)
    private SyncStatus syncStatus = SyncStatus.READY;

    @ForeignCollectionField
    private Collection<Picture> pictures = new ArrayList<>();

    @SuppressWarnings("unused")
    public Inspection() {
        // used by ormlite
    }

    public boolean canSyncProduct() {
        return pictures.size() == 0 || syncStatus == SyncStatus.PICTURES_SYNCED;
    }

    public boolean canSyncPictures() {
        return pictures.size() > 0 && atLeastOnePictureInNotStartedStatus();
    }

    public synchronized void sync(PictureService pictureService, final SyncCallback syncCallback) {
        pictureService = checkNotNull(pictureService, "pictureService cannot be null");
        if (syncCallback == null) {
            throw new NullPointerException("Callback cannot be null");
        }

        if (!canSyncPictures()) {
            throw new IllegalStateException("Cannot sync Pictures when status is "+syncStatus);
        }

        final Picture picture = getNextImageReady();

        if (picture == null) {
            throw new IllegalStateException("No Pictures to send ");
        }

        // TODO: Pensar nos potenciais perigos de multithreading em setar status aqui e no onResponse/onFailure
        syncStatus = SyncStatus.PICTURES_BEING_SYNCED;
        picture.setSyncStatus(PictureSyncStatus.BEING_SYNCED);

        pictureService.send(picture.getFile(), this, new HttpProgressCallback<PictureResponse>() {
            @Override
            public void onProgressUpdated(double percentage) {
                syncCallback.onProgressUpdated(Inspection.this, percentage);
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

                picture.setSyncStatus(PictureSyncStatus.DONE);

                if(countImagesNotDone() == 0) {
                    syncStatus = SyncStatus.PICTURES_SYNCED;
                }
                syncCallback.onSuccess(Inspection.this);
            }

            @Override
            public void onFailure(Throwable t) {
                picture.setSyncStatus(PictureSyncStatus.NOT_STARTED);
                syncStatus = SyncStatus.FAILED;

                syncCallback.onFailure(Inspection.this, t);
            }
        });
    }

    public synchronized void sync(InspectionService inspectionService, final SyncCallback syncCallback) {
        inspectionService = checkNotNull(inspectionService, "inspectionService cannot be null");
        if (syncCallback == null) {
            throw new NullPointerException("Callback cannot be null");
        }

        if (!canSyncProduct()) {
            throw new IllegalStateException("Cannot sync when status is "+syncStatus);
        }

        syncStatus = SyncStatus.INSPECTION_BEING_SYNCED;
        inspectionService.send(this, new HttpProgressCallback<ProductResponse>() {
            @Override
            public void onProgressUpdated(double percentage) {
                syncCallback.onProgressUpdated(Inspection.this, percentage);
            }

            @Override
            public void onResponse(HttpResponse<ProductResponse> httpResponse) {
                if(httpResponse == null) {
                    onFailure(null);
                    return;
                }
                if(httpResponse.isSuccessful()) {
                    syncStatus = SyncStatus.DONE;
                    syncCallback.onSuccess(Inspection.this);
                    return;
                }
                onFailure(null);
            }

            @Override
            public void onFailure(Throwable t) {
                syncStatus = SyncStatus.FAILED;
                syncCallback.onFailure(Inspection.this, t);
            }
        });
    }

    public synchronized void addImageToSync(File image) {
        if (syncStatus != SyncStatus.READY) {
            throw new IllegalStateException("Cannot add images to an inspection that started syncing");
        }

        image = checkNotNull(image);

        Picture picture = new Picture(this, image);
        pictures.add(picture);
    }

    public SyncStatus getSyncStatus() {
        return syncStatus;
    }

    public void reset() {
        for (Picture picture : pictures) {
            if(picture.getSyncStatus() == PictureSyncStatus.BEING_SYNCED) {
                picture.setSyncStatus(PictureSyncStatus.NOT_STARTED);
            }
        }

        if (syncStatus == SyncStatus.INSPECTION_BEING_SYNCED) {
            syncStatus = SyncStatus.READY;
            // TODO: syncStatus = SyncStatus.PICTURES_SYNCED;
        }

        if (syncStatus == SyncStatus.FAILED) {
            syncStatus = SyncStatus.READY;
        }
    }

    private int countImagesNotDone() {
        int countImagesNotDone = 0;
        for(Picture picture : pictures) {
            if(picture.getSyncStatus() != PictureSyncStatus.DONE) countImagesNotDone++;
        }
        return countImagesNotDone;
    }

    private Picture getNextImageReady() {
        if(pictures.size() <= 0) return null;

        for(Picture picture : pictures) {
            if(picture.getSyncStatus() == PictureSyncStatus.NOT_STARTED)
                return picture;
        }
        return null;
    }

    private boolean atLeastOnePictureInNotStartedStatus() {
        for (Picture picture : pictures) {
            if (picture.getSyncStatus() == PictureSyncStatus.NOT_STARTED)
                return true;
        }

        return false;
    }
}
