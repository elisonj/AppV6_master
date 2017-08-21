package br.com.bg7.appvistoria.data;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.Nonnull;

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

    @DatabaseField(index = true)
    private SyncStatus syncStatus;

    @ForeignCollectionField
    private Collection<Picture> pictures = new ArrayList<>();

    @SuppressWarnings("unused")
    public Inspection() {
        // used by ormlite
    }

    /**
     * Checks if syncing the product is allowed
     *
     * @return true if all pictures are synced (or there are no pictures to sync) and
     * false if any pictures are still syncing
     */
    public boolean canSyncProduct() {
        return syncStatus != null && (pictures.size() == 0 || syncStatus == SyncStatus.PICTURES_SYNCED);
    }

    /**
     * Checks if syncing pictures is allowed
     *
     * @return true if there are any pictures to sync, false if pictures are all whether
     * currently in progress or completed
     */
    public boolean canSyncPictures() {
        if (syncStatus == null) {
            return false;
        }

        for (Picture picture : pictures) {
            if (picture.getSyncStatus() == PictureSyncStatus.NOT_STARTED)
                return true;
        }

        return false;
    }

    public void readyToSync() {
        syncStatus = SyncStatus.READY;
    }

    /**
     * Send a single picture via a {@code PictureService}
     *
     * TODO: Pensar nos potenciais perigos de multithreading em setar status aqui e no onResponse/onFailure
     *
     * @param pictureService the picture service where the file should be sent to
     * @param syncCallback a callback that will be invoked for progress, success or failure
     */
    public synchronized void sync(PictureService pictureService, final SyncCallback syncCallback) {
        pictureService = checkNotNull(pictureService, "pictureService cannot be null");
        if (syncCallback == null) {
            throw new NullPointerException("Callback cannot be null");
        }

        if (!canSyncPictures()) {
            throw new IllegalStateException("Cannot sync Pictures when status is " + syncStatus);
        }

        final Picture picture = getNextPicture();
        picture.setSyncStatus(PictureSyncStatus.BEING_SYNCED);

        if (syncStatus == SyncStatus.READY) {
            syncStatus = SyncStatus.PICTURES_BEING_SYNCED;
        }

        // TODO: Testar callbies
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
        if (syncStatus != null) {
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
            if (picture.getSyncStatus() == PictureSyncStatus.BEING_SYNCED) {
                picture.setSyncStatus(PictureSyncStatus.NOT_STARTED);
            }
        }

        if (syncStatus == SyncStatus.INSPECTION_BEING_SYNCED) {
            if (pictures.size() > 0) {
                syncStatus = SyncStatus.PICTURES_SYNCED;
                return;
            }

            syncStatus = SyncStatus.READY;
        }

        if (syncStatus == SyncStatus.FAILED) {
            if (!canSyncPictures()) {
                syncStatus = SyncStatus.PICTURES_SYNCED;
                return;
            }

            syncStatus = SyncStatus.READY;
        }
    }

    private int countImagesNotDone() {
        int countImagesNotDone = 0;
        for (Picture picture : pictures) {
            if (picture.getSyncStatus() != PictureSyncStatus.DONE) countImagesNotDone++;
        }
        return countImagesNotDone;
    }

    @Nonnull
    private Picture getNextPicture() {
        for (Picture picture : pictures) {
            if (picture.getSyncStatus() == PictureSyncStatus.NOT_STARTED)
                return picture;
        }

        // Should never reach this because we check for at least one NOT_STARTED before calling this
        throw new IllegalStateException("Erro crítico: próxima imagem para sync não encontrada!");
    }
}
