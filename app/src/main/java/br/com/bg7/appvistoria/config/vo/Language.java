package br.com.bg7.appvistoria.config.vo;

/**
 * Created by: elison
 * Date: 2017-07-17
 *
 * Represents a Language in the language selection screen
 */
public class Language {

    private final String name;
    private final String displayName;

    public Language(String name, String displayName) {
        this.name = name;
        this.displayName = displayName;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

}
