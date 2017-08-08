package br.com.bg7.appvistoria.data.source.local.fake;

import com.google.common.base.Predicate;
import com.google.common.collect.Maps;

import java.util.Map;

import javax.annotation.Nullable;

import br.com.bg7.appvistoria.data.ProductFile;
import br.com.bg7.appvistoria.data.source.local.ProductFileRepository;
import br.com.bg7.appvistoria.sync.SyncStatus;

/**
 * Created by: luciolucio
 * Date: 2017-07-31
 */

public class FakeProductInspectionFileRepository extends FakeRepository<SyncStatus, ProductFile> implements ProductFileRepository {

    @Override
    SyncStatus getKey(ProductFile entity) {
        return entity.getSyncStatus();
    }

    @Override
    public Iterable<ProductFile> findByProductInspectionIdAndSyncStatus(long productInspectionId, final SyncStatus status) {
        Map<SyncStatus, ProductFile> filtered = Maps.filterEntries(ENTITIES_BY_KEY, new Predicate<Map.Entry<SyncStatus, ProductFile>>() {
            @Override
            public boolean apply(@Nullable Map.Entry<SyncStatus, ProductFile> input) {
                return input != null && input.getKey().equals(status);
            }
        });

        return filtered.values();
    }

    @Override
    public Iterable<ProductFile> findByProductInspectionId(long productInspectionId) {
        return null;
    }


}
