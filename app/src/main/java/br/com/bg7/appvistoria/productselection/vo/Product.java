package br.com.bg7.appvistoria.productselection.vo;

/**
 * Created by: elison
 * Date: 2017-08-31
 */
public class Product {
    private Long id;
    private Category category;

    public Product(Long id, Category category) {
        this.id = id;
        this.category = category;
    }

    String getProductType() {
        return category.getProductTypeName();
    }

    Long getProductTypeId() {
        return category.getProductTypeId();
    }

    public Long getId() {
        return id;
    }

    public Category getCategory() {
        return category;
    }
}
