package br.com.bg7.appvistoria.productselection.vo;

import android.graphics.drawable.Drawable;

import com.google.common.base.Objects;

/**
 * Created by: luciolucio
 * Date: 2017-09-14
 */

public class ProductSelectionHeader {

    private long id;
    private String title;
    private Drawable drawable;

    public ProductSelectionHeader(long id, String title) {
        this.id = id;
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductSelectionHeader that = (ProductSelectionHeader) o;
        return id == that.id &&
                Objects.equal(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, title);
    }
}
