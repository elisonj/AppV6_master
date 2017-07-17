package br.com.bg7.appvistoria.vo;

import com.orm.SugarRecord;

/**
 * Created by: elison
 * Date: 2017-07-17
 */
public class Config extends SugarRecord<Config> {
    private boolean updateOnlyWifi;
    private String actualLanguage;

    public Config() {}

    public Config(String actualLanguage, boolean updateOnlyWifi) {
        this.actualLanguage = actualLanguage;
        this.updateOnlyWifi = updateOnlyWifi;
    }

    public String getActualLanguage() {
        return actualLanguage;
    }

    public boolean isUpdateOnlyWifi() {
        return updateOnlyWifi;
    }
}
