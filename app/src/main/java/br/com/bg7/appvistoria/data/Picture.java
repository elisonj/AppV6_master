package br.com.bg7.appvistoria.data;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.File;

import br.com.bg7.appvistoria.sync.PictureSyncStatus;

/**
 * Created by: elison
 * Date: 2017-08-04
 *
 * Represents a picture taken during an inspection
 */
@DatabaseTable(tableName = "pictures")
public class Picture {

    @DatabaseField(id = true, generatedId = true)
    private Long id;

    @DatabaseField(canBeNull = false)
    private String path;

    @DatabaseField(canBeNull = false)
    private PictureSyncStatus syncStatus;

    @DatabaseField(canBeNull = false, foreign = true)
    private Inspection inspection;

    private File file;

    @SuppressWarnings("unused")
    Picture() {
        // used by ormlite
    }

    Picture(Inspection inspection, File file) {
        this.inspection = inspection;
        this.file = file;
        this.path = file.getAbsolutePath();
        this.syncStatus = PictureSyncStatus.NOT_STARTED;
    }

    public File getFile() {
        if(file != null) {
            return file;
        }

        file = new File(path);
        return file;
    }

     PictureSyncStatus getSyncStatus() {
        return syncStatus;
    }

    void setSyncStatus(PictureSyncStatus syncStatus) {
        this.syncStatus = syncStatus;
    }
}
