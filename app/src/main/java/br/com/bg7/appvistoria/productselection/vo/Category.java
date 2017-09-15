package br.com.bg7.appvistoria.productselection.vo;

/**
 * Created by: elison
 * Date: 2017-08-31
 */
public class Category {
    private String name;
    private ProductType productType;

    public Category(String name, ProductType productType) {
        this.name = name;
        this.productType = productType;
    }

    public String getName() {
        return name;
    }

    String getProductTypeName() {
        return productType.getName();
    }

    Long getProductTypeId() {
        return productType.getId();
    }
}
