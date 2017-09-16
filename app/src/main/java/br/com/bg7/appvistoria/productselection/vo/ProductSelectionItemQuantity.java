package br.com.bg7.appvistoria.productselection.vo;

import android.content.Context;

import br.com.bg7.appvistoria.R;

/**
 * Created by: luciolucio
 * Date: 2017-09-16
 */

public class ProductSelectionItemQuantity {
    private Context context;
    private ProductSelectionItem item;
    private int quantity;

    ProductSelectionItemQuantity(Context context, int quantity, ProductSelectionItem item) {
        this.context = context;
        this.item = item;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        if (quantity == 1) {
            return context.getString(R.string.product_selection_item_single_format, quantity);
        }

        return context.getString(R.string.product_selection_item_multiple_format, quantity);
    }

    public int getQuantity() {
        return quantity;
    }

    public void select(int quantity) {
        item.select(quantity);
    }
}
