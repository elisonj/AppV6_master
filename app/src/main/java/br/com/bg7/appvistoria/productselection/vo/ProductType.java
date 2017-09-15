package br.com.bg7.appvistoria.productselection.vo;

/**
 * Created by: luciolucio
 * Date: 2017-09-15
 */

public class ProductType {
    private Long id;
    private String name;

    public ProductType(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    Long getId() {
        return id;
    }

    String getName() {
        return name;
    }
}
