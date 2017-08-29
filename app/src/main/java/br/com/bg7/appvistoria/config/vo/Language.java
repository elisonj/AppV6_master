package br.com.bg7.appvistoria.config.vo;

import com.google.common.base.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Language language = (Language) o;
        return Objects.equal(name, language.name) &&
                Objects.equal(displayName, language.displayName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, displayName);
    }
}
