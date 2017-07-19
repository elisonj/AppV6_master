package br.com.bg7.appvistoria.vo;

import com.orm.SugarRecord;

/**
 * Created by: elison
 * Date: 2017-07-17
 */
public class Config extends SugarRecord<Config> {
    private boolean updateOnlyWifi;
    private String language;

    public Config() {}

    public Config(String language, boolean updateOnlyWifi) {
        this.language = language;
        this.updateOnlyWifi = updateOnlyWifi;
    }

    public String getLanguage() {
        return language;
    }

    public boolean isUpdateOnlyWifi() {
        return updateOnlyWifi;
    }
}
