package br.com.bg7.appvistoria;

import br.com.bg7.appvistoria.sync.SyncStatus;

/**
 * Created by: elison
 * Date: 2017-07-18
 */
public class Constants {

    public static final String PREFERENCE_LANGUAGE_KEY = "PREFERENCE_LANGUAGE_KEY";
    public static final String DEFAULT_LANGUAGE = "pt";

    public static final String FONT_NAME_ROBOTO_LIGHT   =    "robotolight.ttf";
    public static final String FONT_NAME_ROBOTO_MEDIUM  =   "robotomedium.ttf";
    public static final String FONT_NAME_ROBOTO_REGULAR =  "robotoregular.ttf";
    public static final String FONT_NAME_ROBOTO_BOLD    =  "robotobold.ttf";

    public static final String FONT_NAME_NUNITO_LIGHT       =  "nunitolight.ttf";
    public static final String FONT_NAME_NUNITO_REGULAR     =  "nunitoregular.ttf";
    public static final String FONT_NAME_NUNITO_BOLD        =  "nunitobold.ttf";

    public static final String INTENT_EXTRA_PROJECT_KEY = "project";
    public static final String INTENT_EXTRA_LOCATION_KEY = "location";

    public static final SyncStatus[] PENDING_INSPECTIONS_RESETTABLE_STATUS_LIST = {
            SyncStatus.PICTURES_BEING_SYNCED,
            SyncStatus.INSPECTION_BEING_SYNCED,
    };

    public static final SyncStatus[] PENDING_INSPECTIONS_STATUS_INITIALIZATION_ORDER = {
            SyncStatus.INSPECTION_BEING_SYNCED,
            SyncStatus.PICTURES_SYNCED,
            SyncStatus.PICTURES_BEING_SYNCED,
            SyncStatus.READY
    };
}
