package br.com.bg7.appvistoria.data;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by: elison
 * Date: 2017-07-17
 *
 * Represents a user's config settings
 */
@DatabaseTable(tableName = "configs")
public class Config {

    @DatabaseField(generatedId = true)
    private Long id;

    @DatabaseField(canBeNull = false)
    private boolean syncWithWifiOnly;

    @DatabaseField(canBeNull = false)
    private String languageName;

    @DatabaseField(canBeNull = false, foreign = true, columnName = USER_ID_FIELD)
    private User user;
    public static final String USER_ID_FIELD = "user_id";

    @SuppressWarnings("unused")
    Config() {
        // used by ormlite
    }

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
        Config config = new Config(this.languageName, this.syncWithWifiOnly, this.user);

        config.id = this.id;

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
