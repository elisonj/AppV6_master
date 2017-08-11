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
    private User user;

    /**
     * Default constructor used by Sugar
     */
    @SuppressWarnings("unused")
    public Config() {}

    public Config(String languageName, boolean updateOnlyWifi, User user) {
        this.languageName = languageName;
        this.syncWithWifiOnly = updateOnlyWifi;
        this.user = user;
    }

    public Config withLanguage(String language) {
        Config config = cloneConfig();
        config.languageName = language;

        return config;
    }

    public Config withSyncWithWifiOnly(boolean syncWithWifiOnly) {
        Config config = cloneConfig();
        config.syncWithWifiOnly = syncWithWifiOnly;

        return config;
    }

    private Config cloneConfig() {
        Config config = new Config();

        config.id = this.id;
        config.languageName = this.languageName;
        config.syncWithWifiOnly = this.syncWithWifiOnly;
        config.user = this.user;

        return config;
    }

    public String getLanguageName() {
        return languageName;
    }

    public boolean isSyncWithWifiOnly() {
        return syncWithWifiOnly;
    }

    public User getUser() {
        return user;
    }
}
