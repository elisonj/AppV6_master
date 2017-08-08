package br.com.bg7.appvistoria.data.source.local;

import br.com.bg7.appvistoria.data.ProductFile;
import br.com.bg7.appvistoria.sync.SyncStatus;

/**
 * Created by: elison
 * Date: 2017-08-08
 */
public interface ProductFileRepository extends Repository<ProductFile> {
    Iterable<ProductFile> findByProductInspectionIdAndSyncStatus(long productInspectionId, SyncStatus status);
    Iterable<ProductFile> findByProductInspectionId(long productInspectionId);
}
