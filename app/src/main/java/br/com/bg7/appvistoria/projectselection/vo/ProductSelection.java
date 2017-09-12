package br.com.bg7.appvistoria.projectselection.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by: elison
 * Date: 2017-08-31
 */
public class ProductSelection {
    private String productType;
    private HashMap<String, Integer> categoryCounts;

    public String getProductType() {
        return productType;
    }

    public HashMap<String, Integer> getCategoryCounts() {
        return categoryCounts;
    }

    public static List<ProductSelection> fromProducts(List<Product> products) {
        HashMap<String, HashMap<String, Integer>> results = new HashMap<>();

        for (Product product : products) {
            String productType = product.getProductType();
            String category = product.getCategory().getName();

            if (!results.containsKey(productType)) {
                results.put(productType, new HashMap<String, Integer>());
            }

            HashMap<String, Integer> dataForProductType = results.get(productType);

            if (!dataForProductType.containsKey(category)) {
                dataForProductType.put(category, 0);
            }

            dataForProductType.put(
                    category,
                    dataForProductType.get(category) + 1
            );
        }

        ArrayList<ProductSelection> finalList = new ArrayList<>();

        for (Map.Entry<String, HashMap<String, Integer>> entry : results.entrySet()) {
            ProductSelection productSelection = new ProductSelection();
            productSelection.productType = entry.getKey();
            productSelection.categoryCounts = entry.getValue();

            finalList.add(productSelection);
        }

        return finalList;
    }
}
