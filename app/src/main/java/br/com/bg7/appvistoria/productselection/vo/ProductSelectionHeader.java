package br.com.bg7.appvistoria.productselection.vo;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.google.common.base.Objects;

import java.util.HashMap;

/**
 * Created by: luciolucio
 * Date: 2017-09-14
 */

public class ProductSelectionHeader {

    private Long id;
    private String title;
    private static HashMap<Long, Drawable> drawables = new HashMap<>();

    ProductSelectionHeader(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    // TODO: Falta a categoria 24: Artes, Decoração & Colecionismo
    public Drawable getDrawable(Context context) {
        if (!drawables.containsKey(this.id)) {
            int id = context.getResources().getIdentifier("product_type_" + this.id.toString(), "drawable", context.getPackageName());

            drawables.put(this.id, context.getDrawable(id));
        }

        return drawables.get(this.id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductSelectionHeader that = (ProductSelectionHeader) o;
        return Objects.equal(id, that.id) &&
                Objects.equal(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, title);
    }
}
