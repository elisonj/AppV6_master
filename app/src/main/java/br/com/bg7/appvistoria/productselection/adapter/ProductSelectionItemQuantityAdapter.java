package br.com.bg7.appvistoria.productselection.adapter;

import android.content.Context;

import br.com.bg7.appvistoria.R;
import br.com.bg7.appvistoria.productselection.vo.ProductSelectionItem;
import br.com.bg7.appvistoria.productselection.vo.ProductSelectionItemQuantity;
import me.srodrigo.androidhintspinner.HintAdapter;

/**
 * Created by: luciolucio
 * Date: 2017-09-16
 */

class ProductSelectionItemQuantityAdapter extends HintAdapter<ProductSelectionItemQuantity> {

    ProductSelectionItemQuantityAdapter(Context context, ProductSelectionItem item) {
        super(context, context.getString(R.string.spinner_hint), item.getQuantities());
    }
}
