package br.com.bg7.appvistoria.vo;

/**
 * Created by: elison
 * Date: 2017-07-17
 */
public class Country {

    private String id;
    private String name;
    private String language;
    private String abbreviation;

    public Country(String id, String name, String language, String abbreviation) {
        this.id = id;
        this.name = name;
        this.language = language;
        this.abbreviation = abbreviation;
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
