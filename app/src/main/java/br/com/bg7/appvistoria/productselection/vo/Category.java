package br.com.bg7.appvistoria.productselection.vo;

/**
 * Created by: elison
 * Date: 2017-08-31
 */
public class Category {
    private Long id;
    private String name;

    public Category(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
