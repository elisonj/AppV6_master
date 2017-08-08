package br.com.bg7.appvistoria.data;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import java.io.File;

import br.com.bg7.appvistoria.sync.SyncStatus;

/**
 * Created by: elison
 * Date: 2017-08-04
 *
 * represents a product's file to persists
 */
public class ProductFile extends SugarRecord<ProductFile> {

    private long productInspectionId;
    private String path;
    private SyncStatus syncStatus;

    @Ignore
    private File file;

    /**
     * Default constructor used by Sugar
     */
    @SuppressWarnings("unused")
    public ProductFile() {}

    ProductFile(long productInspectionId, File file) {
        this.productInspectionId = productInspectionId;
        this.file = file;
        this.path = file.getAbsolutePath();
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
    }
}
