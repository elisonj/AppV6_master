package br.com.bg7.appvistoria.data.source.local.fake;

import com.google.common.base.Predicate;
import com.google.common.collect.Maps;

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

public class FakeInspectionSyncRepository extends FakeRepository<InspectionStatus, Inspection> implements InspectionRepository {
    @Override
    InspectionStatus getKey(Inspection entity) {
        return entity.getStatus();
    }

    @Override
    public Iterable<Inspection> findBySyncStatus(final SyncStatus status) {
        return null;
    }

    @Override
    public Iterable<Inspection> findByStatus(final InspectionStatus status) {
        Map<InspectionStatus, Inspection> filtered = Maps.filterEntries(ENTITIES_BY_KEY,
                new Predicate<Map.Entry<InspectionStatus, Inspection>>() {
            @Override
            public boolean apply(@Nullable Map.Entry<InspectionStatus, Inspection> input) {
                return input != null && input.getKey().equals(status);
            }
        });

        return filtered.values();
    }
}
