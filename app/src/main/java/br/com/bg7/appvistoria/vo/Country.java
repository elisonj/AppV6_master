package br.com.bg7.appvistoria.vo;

/**
 * Created by: elison
 * Date: 2017-07-17
 *
 * Represents a Country, like the ones that appear in the language
 * selection screen
 */
public class Country {

    private final String id;
    private final String name;
    private final String language;

    public Country(String id, String name, String language) {
        this.id = id;
        this.name = name;
        this.language = language;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLanguage() {
        return language;
    }

    @Override
    public String toString() {
        return name;
    }

}
