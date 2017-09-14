package br.com.bg7.appvistoria.productselection.vo;

import com.google.common.base.Objects;

/**
 * Created by: luciolucio
 * Date: 2017-09-13
 */

public class ProductSelectionItem {
    private String title;
    private int count;

    public String getTitle() {
        return title;
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
                Objects.equal(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(title, count);
    }
}
