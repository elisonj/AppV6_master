package br.com.bg7.appvistoria.productselection.vo;

import android.content.Context;

import com.google.common.base.Objects;

import java.util.ArrayList;
import java.util.List;

import br.com.bg7.appvistoria.data.WorkOrder;
import br.com.bg7.appvistoria.data.WorkOrderCategory;
import br.com.bg7.appvistoria.data.WorkOrderProduct;

/**
 * Created by: elison
 * Date: 2017-09-13
 */

public class ProductSelectionItem {
    private Context context;
    private WorkOrderCategory category;
    private int count = 0;
    private int selected = 0;

    ProductSelectionItem(Context context, WorkOrderCategory category, int count) {
        this.context = context;
        this.category = category;
        this.count = count;
    }

    public WorkOrderCategory getCategory() {
        return category;
    }

    public int getCount() {
        return count;
    }

    public List<ProductSelectionItemQuantity> getQuantities() {
        ArrayList<ProductSelectionItemQuantity> list = new ArrayList<>();

        // Position 0 means 1 item
        // Position 1 means 2 items
        // etc

        for (int i = 0; i < count; i++) {
            list.add(new ProductSelectionItemQuantity(context, i + 1));
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

    public List<WorkOrderProduct> getSelectedProducts(WorkOrder workOrder) {
        ArrayList<WorkOrderProduct> products = new ArrayList<>();

        for (int i = 0; i < selected; i++) {
            products.add(new WorkOrderProduct(category, workOrder));
        }

        return products;
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
}
