package br.com.bg7.appvistoria.projectselection.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by: elison
 * Date: 2017-08-31
 */
public class Project {
    private Long id;
    private String description;
    private List<Product> products = new ArrayList<>();

    public Project(Long id, String description) {
        this.id = id;
        this.description = description;
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
}
