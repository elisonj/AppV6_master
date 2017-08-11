package br.com.bg7.appvistoria.data.source.local.fake;

import com.google.common.base.Predicate;
import com.google.common.collect.Maps;

import java.util.Map;

import javax.annotation.Nullable;

import br.com.bg7.appvistoria.data.Inspection;
import br.com.bg7.appvistoria.data.source.local.ProductInspectionRepository;
import br.com.bg7.appvistoria.sync.SyncStatus;

/**
 * Created by: luciolucio
 * Date: 2017-07-31
 */

public class FakeProductInspectionRepository extends FakeRepository<SyncStatus, Inspection> implements ProductInspectionRepository {
    @Override
    SyncStatus getKey(Inspection entity) {
        return entity.getSyncStatus();
    }

    @Override
    public Iterable<Inspection> findBySyncStatus(final SyncStatus status) {
        Map<SyncStatus, Inspection> filtered = Maps.filterEntries(ENTITIES_BY_KEY, new Predicate<Map.Entry<SyncStatus, Inspection>>() {
            @Override
            public boolean apply(@Nullable Map.Entry<SyncStatus, Inspection> input) {
                return input != null && input.getKey().equals(status);
            }
        });

        return filtered.values();
    }
}
