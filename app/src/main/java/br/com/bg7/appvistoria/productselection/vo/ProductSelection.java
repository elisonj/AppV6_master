package br.com.bg7.appvistoria.productselection.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.bg7.appvistoria.productselection.ProductSelectionHeader;

/**
 * Created by: elison
 * Date: 2017-08-31
 */
public class ProductSelection {
    private ProductSelectionHeader header;
    private List<ProductSelectionItem> items;

    public ProductSelectionHeader getHeader() {
        return header;
    }

    public List<ProductSelectionItem> getItems() {
        return items;
    }

    public static List<ProductSelection> fromProducts(List<Product> products) {
        ArrayList<ProductSelection> results = new ArrayList<>();

        for (Product product : products) {
            String productType = product.getProductType();
            String category = product.getCategory().getName();
        }

        return finalList;
    }
}
