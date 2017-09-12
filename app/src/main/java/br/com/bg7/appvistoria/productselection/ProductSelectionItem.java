package br.com.bg7.appvistoria.productselection;

import br.com.bg7.appvistoria.projectselection.vo.Product;

/**
 * Created by: elison
 * Date: 2017-09-08
 */
public class ProductSelectionItem {
    private Product product;
    private Integer quantity;

    ProductSelectionItem(Product product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    Integer getQuantity() {
        return quantity;
    }

    void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }
}
