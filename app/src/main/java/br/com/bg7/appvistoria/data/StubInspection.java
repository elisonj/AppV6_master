package br.com.bg7.appvistoria.data;

import br.com.bg7.appvistoria.sync.SyncStatus;
import br.com.bg7.appvistoria.sync.InspectionStatus;

/**
 * Created by: elison
 * Date: 2017-09-13
 */
public class StubInspection extends Inspection {

    public StubInspection(Long id, String description) {
        this.id = id;
        this.description = description;
        status = InspectionStatus.COMPLETED;
    }

    public void setSyncStatus(SyncStatus syncStatus) {
        this.syncStatus = syncStatus;
    }


}
