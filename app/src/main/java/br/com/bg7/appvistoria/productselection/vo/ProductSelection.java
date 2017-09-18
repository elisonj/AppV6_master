package br.com.bg7.appvistoria.productselection.vo;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.bg7.appvistoria.data.WorkOrderCategory;
import br.com.bg7.appvistoria.data.WorkOrderProduct;
import br.com.bg7.appvistoria.data.WorkOrderProductType;

/**
 * Created by: elison
 * Date: 2017-08-31
 */
public class ProductSelection {
    private ProductSelectionHeader productType;
    private List<ProductSelectionItem> categories;

    public ProductSelectionHeader getProductType() {
        return productType;
    }

    public List<ProductSelectionItem> getCategories() {
        return categories;
    }

    public static List<ProductSelection> fromProducts(Context context, List<WorkOrderProduct> products) {
        ArrayList<ProductSelection> finalList = new ArrayList<>();
        HashMap<ProductSelectionHeader, HashMap<WorkOrderCategory, Integer>> map = new HashMap<>();

        for (WorkOrderProduct product : products) {
            WorkOrderProductType productType = product.getProductType();
            WorkOrderCategory category = product.getCategory();

            ProductSelectionHeader header = new ProductSelectionHeader(productType);

            if (!map.containsKey(header)) {
                map.put(header, new HashMap<WorkOrderCategory, Integer>());
            }

            if (!map.get(header).containsKey(category)) {
                map.get(header).put(category, 0);
            }

            map.get(header).put(category, map.get(header).get(category) + 1);
        }

        for (Map.Entry<ProductSelectionHeader, HashMap<WorkOrderCategory, Integer>> entry : map.entrySet()) {
            ProductSelection selection = new ProductSelection();
            ArrayList<ProductSelectionItem> items = new ArrayList<>();

            for (Map.Entry<WorkOrderCategory, Integer> categoryEntry : entry.getValue().entrySet()) {
                ProductSelectionItem item = new ProductSelectionItem(context, categoryEntry.getKey(), categoryEntry.getValue());

                items.add(item);
            }

            selection.productType = entry.getKey();
            selection.categories = items;

            finalList.add(selection);
        }

        return finalList;
    }
}
