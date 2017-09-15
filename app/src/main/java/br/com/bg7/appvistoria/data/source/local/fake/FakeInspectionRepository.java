package br.com.bg7.appvistoria.data.source.local.fake;

import com.google.common.base.Predicate;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import br.com.bg7.appvistoria.data.Inspection;
import br.com.bg7.appvistoria.data.source.local.InspectionRepository;
import br.com.bg7.appvistoria.sync.InspectionStatus;
import br.com.bg7.appvistoria.sync.SyncStatus;

/**
 * Created by: luciolucio
 * Date: 2017-07-31
 */

public class FakeInspectionRepository extends FakeRepository<SyncStatus, Inspection> implements InspectionRepository {
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

    @Override
    public List<Inspection> findByStatus(final InspectionStatus status) {
        return null;
    }

}
