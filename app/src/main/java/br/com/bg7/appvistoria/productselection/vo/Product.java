package br.com.bg7.appvistoria.productselection.vo;

/**
 * Created by: elison
 * Date: 2017-08-31
 */
public class Product {
    private Long id;
    private String productType;
    private Category category;

    public Product(Long id, String productType, Category category) {
        this.id = id;
        this.productType = productType;
        this.category = category;
    }

    public String getProductType() {
        return productType;
    }

    public Long getId() {
        return id;
    }

    public Category getCategory() {
        return category;
    }

}
