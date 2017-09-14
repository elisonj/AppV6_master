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
        ArrayList<ProductSelection> finalList = new ArrayList<>();
        HashMap<ProductSelectionHeader, List<ProductSelectionItem>> map = new HashMap<>();

        for (Product product : products) {
            String productType = product.getProductType();
            String category = product.getCategory().getName();

            ProductSelectionHeader header = new ProductSelectionHeader(product.getProductTypeId(), productType);
            ProductSelectionItem item = new ProductSelectionItem(category);

            if (!map.containsKey(header)) {
                map.put(header, new ArrayList<ProductSelectionItem>());
            }

            map.get(header).add(item);
        }

        for (Map.Entry<ProductSelectionHeader, List<ProductSelectionItem>> entry : map.entrySet()) {
            ProductSelection selection = new ProductSelection();

            selection.header = entry.getKey();
            selection.items = entry.getValue();

            finalList.add(selection);
        }

        return finalList;
    }
}
