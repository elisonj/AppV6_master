package br.com.bg7.appvistoria.data;

import com.orm.SugarRecord;

/**
 * Created by: elison
 * Date: 2017-07-17
 *
 * Represents a user's config settings
 */
public class Config extends SugarRecord<Config> {
    private boolean syncWithWifiOnly;
    private String languageName;

    /**
     * Default constructor used by Sugar
     */
    @SuppressWarnings("unused")
    public Config() {}

    public Config(String languageName, boolean updateOnlyWifi) {
        this.languageName = languageName;
        this.syncWithWifiOnly = updateOnlyWifi;
    }

    public String getLanguageName() {
        return languageName;
    }

    public boolean isSyncWithWifiOnly() {
        return syncWithWifiOnly;
    }
}
