package br.com.bg7.appvistoria.data.source.local.fake;

import br.com.bg7.appvistoria.data.ProductFile;
import br.com.bg7.appvistoria.data.source.local.ProductFileRepository;
import br.com.bg7.appvistoria.sync.SyncStatus;


public class FakeProductInspectionFileRepository extends FakeRepository<SyncStatus, ProductFile> implements ProductFileRepository {

    @Override
    SyncStatus getKey(ProductFile entity) {
        return entity.getSyncStatus();
    }

}
