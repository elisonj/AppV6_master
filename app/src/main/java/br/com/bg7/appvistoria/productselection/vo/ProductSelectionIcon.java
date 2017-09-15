package br.com.bg7.appvistoria.productselection.vo;

import android.content.Context;
import android.graphics.drawable.Drawable;

import java.util.HashMap;

import br.com.bg7.appvistoria.R;

/**
 * Created by: luciolucio
 * Date: 2017-09-14
 */

public class ProductSelectionIcon {
    private HashMap<String, Drawable> drawableCategories = new HashMap<>();
    private Context context;

    public ProductSelectionIcon(Context context) {
        this.context = context;
    }

    Drawable icon(Integer productTypeId) {
        int id = context.getResources().getIdentifier("category_" + productTypeId.toString(), "drawable", context.getPackageName());

        return context.getDrawable(id);
    }
}
