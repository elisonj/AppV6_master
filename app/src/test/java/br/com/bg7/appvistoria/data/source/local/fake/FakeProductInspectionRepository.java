package br.com.bg7.appvistoria.data.source.local.fake;

import com.google.common.base.Predicate;
import com.google.common.collect.Maps;

import java.util.Map;

import javax.annotation.Nullable;

import br.com.bg7.appvistoria.data.ProductInspection;
import br.com.bg7.appvistoria.data.source.local.ProductInspectionRepository;
import br.com.bg7.appvistoria.sync.SyncStatus;

/**
 * Created by: luciolucio
 * Date: 2017-07-31
 */

public class FakeProductInspectionRepository extends FakeRepository<SyncStatus, ProductInspection> implements ProductInspectionRepository {
    @Override
    SyncStatus getKey(ProductInspection entity) {
        return entity.getSyncStatus();
    }

    @Override
    public Iterable<ProductInspection> findBySyncStatus(final SyncStatus status) {
        Map<SyncStatus, ProductInspection> filtered = Maps.filterEntries(ENTITIES_BY_KEY, new Predicate<Map.Entry<SyncStatus, ProductInspection>>() {
            @Override
            public boolean apply(@Nullable Map.Entry<SyncStatus, ProductInspection> input) {
                return input != null && input.getKey().equals(status);
            }
        });

        return filtered.values();
    }
}
