package br.com.bg7.appvistoria.data;

import java.util.Collection;

import javax.annotation.Nonnull;

import br.com.bg7.appvistoria.sync.PictureSyncStatus;

/**
 * Created by: luciolucio
 * Date: 2017-08-21
 */

class PictureCollection {
    private Collection<Picture> pictures;

    PictureCollection(Collection<Picture> pictures) {
        this.pictures = pictures;
    }

    public void add(Picture picture) {
        pictures.add(picture);
    }

    boolean isEmpty() {
        return pictures.size() == 0;
    }

    boolean hasOneNotStarted() {
        return internalNext() != null;
    }

    public void reset() {
        for (Picture picture : pictures) {
            if (picture.getSyncStatus() == PictureSyncStatus.BEING_SYNCED) {
                picture.setSyncStatus(PictureSyncStatus.NOT_STARTED);
            }
        }
    }

    boolean allDone() {
        return quantityToSync() == 0;
    }


    int quantityToSync() {
        int count = 0;
        for (Picture picture : pictures) {
            if (picture.getSyncStatus() != PictureSyncStatus.DONE) count++;
        }
        return count;
    }

    int getPicturesSize() {
        return pictures.size();
    }

    @Nonnull
    Picture next() {
        Picture picture = internalNext();
        if (picture != null) {
            return picture;
        }

        // Should never reach this because we check for at least one NOT_STARTED before calling this
        throw new IllegalStateException("Erro crítico: próxima imagem para sync não encontrada!");
    }

    private Picture internalNext() {
        for (Picture picture : pictures) {
            if (picture.getSyncStatus() == PictureSyncStatus.NOT_STARTED)
                return picture;
        }

        return null;
    }
}
