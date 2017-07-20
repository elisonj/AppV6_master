package br.com.bg7.appvistoria.vo;

import com.orm.SugarRecord;

/**
 * Created by: elison
 * Date: 2017-07-17
 *
 * Represents a user's config settings
 */
public class Config extends SugarRecord<Config> {
    private boolean syncWithWifiOnly;
    private String language;

    /**
     * Default constructor used by Sugar
     */
    @SuppressWarnings("unused")
    public Config() {}

    public Config(String language, boolean updateOnlyWifi) {
        this.language = language;
        this.syncWithWifiOnly = updateOnlyWifi;
    }

    public String getLanguage() {
        return language;
    }

    public boolean isSyncWithWifiOnly() {
        return syncWithWifiOnly;
    }
}
