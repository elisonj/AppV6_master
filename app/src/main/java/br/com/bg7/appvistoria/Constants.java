package br.com.bg7.appvistoria;

import br.com.bg7.appvistoria.sync.SyncStatus;

/**
 * Created by: elison
 * Date: 2017-07-18
 */
public class Constants {

    public static final String PREFERENCE_LANGUAGE_KEY = "PREFERENCE_LANGUAGE_KEY";
    public static final String DEFAULT_LANGUAGE = "pt";

    public static final SyncStatus[] PENDING_INSPECTIONS_STATUS_INITIALIZATION_ORDER = {
            SyncStatus.PICTURES_BEING_SYNCED,
            SyncStatus.PICTURES_SYNCED,
            SyncStatus.INSPECTION_BEING_SYNCED,
            SyncStatus.READY
    };
}
