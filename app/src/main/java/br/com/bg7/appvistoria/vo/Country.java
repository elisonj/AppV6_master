package br.com.bg7.appvistoria.vo;

/**
 * Created by: elison
 * Date: 2017-07-17
 */
public class Country {

    private String id;
    private String name;
    private String language;
    private String sigla;

    public Country(String id, String name, String language, String sigla) {
        this.id = id;
        this.name = name;
        this.language = language;
        this.sigla = sigla;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    //to display object as a string in spinner
    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Country){
            Country c = (Country )obj;
            if(c.sigla.equals(sigla) && c.getId()==id ) return true;
        }

        return false;
    }

}
