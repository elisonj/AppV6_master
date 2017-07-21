package br.com.bg7.appvistoria.config.vo;

/**
 * Created by: elison
 * Date: 2017-07-17
 *
 * Represents a Language in the language selection screen
 */
public class Language {

    private final String locale;
    private final String displayName;

    public Language(String locale, String displayName) {
        this.locale = locale;
        this.displayName = displayName;
    }

    public String getLocale() {
        return locale;
    }

    @Override
    public String toString() {
        return displayName;
    }

}
