package br.com.bg7.appvistoria.data;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import java.io.File;

import br.com.bg7.appvistoria.sync.PictureSyncStatus;

/**
 * Created by: elison
 * Date: 2017-08-04
 *
 * represents a product's file to persists
 */
public class ProductFile extends SugarRecord<ProductFile> {

    private ProductInspection productInspection;
    private String path;
    private String status;

    @Ignore
    private PictureSyncStatus syncStatus;

    @Ignore
    private File file;

    /**
     * Default constructor used by Sugar
     */
    @SuppressWarnings("unused")
    public ProductFile() {}

    ProductFile(ProductInspection productInspection, File file) {
        this.productInspection = productInspection;
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
