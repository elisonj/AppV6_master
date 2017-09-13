package br.com.bg7.appvistoria.data;

import br.com.bg7.appvistoria.sync.SyncStatus;

/**
 * Created by: elison
 * Date: 2017-09-13
 */
public class StubInspection extends Inspection {

    public StubInspection(String description) {
        this.description = description;
    }

    public void setStatus(SyncStatus syncStatus) {
        this.syncStatus = syncStatus;
    }
}
