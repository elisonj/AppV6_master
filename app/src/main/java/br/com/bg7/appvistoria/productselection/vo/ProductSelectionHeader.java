package br.com.bg7.appvistoria.productselection.vo;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.google.common.base.Objects;

import java.util.HashMap;

import br.com.bg7.appvistoria.data.WorkOrderProductType;

/**
 * Created by: elison
 * Date: 2017-09-14
 */

public class ProductSelectionHeader {

    private WorkOrderProductType productType;
    private static HashMap<Long, Drawable> drawables = new HashMap<>();

    ProductSelectionHeader(WorkOrderProductType productType) {
        this.productType = productType;
    }

    public String getTitle() {
        return productType.getName();
    }

    // TODO: Falta a categoria 24: Artes, Decoração & Colecionismo
    public Drawable getImage(Context context) {
        Long externalId = productType.getExternalId();

        if (!drawables.containsKey(externalId)) {
            int id = context.getResources().getIdentifier("product_type_" + externalId.toString(), "drawable", context.getPackageName());

            drawables.put(externalId, context.getDrawable(id));
        }

        return drawables.get(externalId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductSelectionHeader that = (ProductSelectionHeader) o;
        return Objects.equal(productType, that.productType);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(productType);
    }
}
