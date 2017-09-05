package br.com.bg7.appvistoria.projectselection.vo;

/**
 * Created by: elison
 * Date: 2017-08-31
 */
public class Product {
    private Long id;
    private String type;
    private Category category;

    public Product(Long id, String type, Category category) {
        this.id = id;
        this.type = type;
        this.category = category;
    }

}
