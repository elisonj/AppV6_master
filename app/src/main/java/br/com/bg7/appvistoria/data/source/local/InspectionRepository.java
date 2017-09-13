package br.com.bg7.appvistoria.data.source.local;

import java.util.List;

import br.com.bg7.appvistoria.data.Inspection;
import br.com.bg7.appvistoria.sync.SyncStatus;
import br.com.bg7.appvistoria.syncinspection.InspectionStatus;

/**
 * Created by: luciolucio
 * Date: 2017-07-31
 */

public interface InspectionRepository extends Repository<Inspection> {
    Iterable<Inspection> findBySyncStatus(SyncStatus status);
    List<Inspection> findBySyncStatus(InspectionStatus status);
}
