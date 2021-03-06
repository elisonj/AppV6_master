package br.com.bg7.appvistoria.data.source.local;

import java.util.List;

import br.com.bg7.appvistoria.data.Inspection;
import br.com.bg7.appvistoria.sync.InspectionStatus;
import br.com.bg7.appvistoria.sync.SyncStatus;

/**
 * Created by: elison
 * Date: 2017-07-31
 */

public interface InspectionRepository extends Repository<Inspection> {
    Iterable<Inspection> findBySyncStatus(SyncStatus status);
    List<Inspection> findByStatus(InspectionStatus status);
}
