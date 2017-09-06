package br.com.bg7.appvistoria.projectselection.vo;

import java.util.HashMap;

/**
 * Created by: elison
 * Date: 2017-08-31
 */
public class ProductSelection {
    private Category category;
    private HashMap<Product, Integer> products;

    public ProductSelection(Category category, HashMap<Product, Integer> products) {
        this.category = category;
        this.products = products;
    }

    public HashMap<Product, Integer> getProducts() {
        return products;
    }

    public Category getCategory() {
        return category;
    }
}
