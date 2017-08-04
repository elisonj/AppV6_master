package br.com.bg7.appvistoria.data;

import com.orm.SugarRecord;

import java.io.File;

import br.com.bg7.appvistoria.sync.SyncStatus;

/**
 * Created by: elison
 * Date: 2017-08-04
 *
 * represents a product's file to persists
 */
class ProductFile extends SugarRecord<ProductFile> {

    private long productInspectionId;
    private File file;
    private String path;
    private SyncStatus syncStatus;

    /**
     * Default constructor used by Sugar
     */
    @SuppressWarnings("unused")
    public ProductFile() {}

    ProductFile(long productInspectionId, File file) {
        this.productInspectionId = productInspectionId;
        this.file = file;
        this.path = file.getAbsolutePath();
        this.save();
    }

    public File getFile() {
        if(file != null) {
            return file;
        }
        file = new File(path);
        return file;
    }

    void setSyncStatus(SyncStatus syncStatus) {
        this.syncStatus = syncStatus;
        this.save();
    }
}
