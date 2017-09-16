package br.com.bg7.appvistoria.productselection.vo;

import android.content.Context;

import com.google.common.base.Objects;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by: luciolucio
 * Date: 2017-09-13
 */

public class ProductSelectionItem {
    private Context context;
    private String category;
    private int count = 0;
    private int selected = 0;

    ProductSelectionItem(Context context, String category, int count) {
        this.context = context;
        this.category = category;
        this.count = count;
    }

    public String getCategory() {
        return category;
    }

    public int getCount() {
        return count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductSelectionItem that = (ProductSelectionItem) o;
        return count == that.count &&
                Objects.equal(category, that.category);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(category, count);
    }

    public List<ProductSelectionItemQuantity> getQuantities() {
        ArrayList<ProductSelectionItemQuantity> list = new ArrayList<>();

        // Position 0 means 1 item
        // Position 1 means 2 items
        // etc

        for (int i = 0; i < count; i++) {
            list.add(new ProductSelectionItemQuantity(context, i + 1, this));
        }

        return list;
    }

    public void select(int quantity) {
        selected = quantity;
    }

    public boolean isSelected() {
        return selected > 0;
    }

    public Integer getSelectedQuantity() {
        return selected;
    }
}
