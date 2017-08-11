package br.com.bg7.appvistoria.data;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import java.io.File;

import br.com.bg7.appvistoria.sync.PictureSyncStatus;

/**
 * Created by: elison
 * Date: 2017-08-04
 *
 * Represents a picture taken during an inspection
 */
public class Picture extends SugarRecord<Picture> {

    @SuppressWarnings({"FieldCanBeLocal", "unused"}) // Used by Sugar for persistence
    private Inspection inspection;
    private String path;
    @SuppressWarnings("unused") // Used by Sugar for persistence
    private String status;

    @Ignore
    private PictureSyncStatus syncStatus;

    @Ignore
    private File file;

    /**
     * Default constructor used by Sugar
     */
    @SuppressWarnings("unused")
    public Picture() {}

    Picture(Inspection inspection, File file) {
        this.inspection = inspection;
        this.file = file;
        this.path = file.getAbsolutePath();
        this.syncStatus = PictureSyncStatus.NOT_STARTED;
        this.status = syncStatus.toString();
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
        this.status = syncStatus.toString();
    }
}
