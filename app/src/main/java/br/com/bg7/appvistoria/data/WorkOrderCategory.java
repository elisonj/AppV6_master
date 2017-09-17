package br.com.bg7.appvistoria.data;

import com.google.common.base.Objects;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by: luciolucio
 * Date: 2017-09-17
 *
 * TODO: Merge disso com o data.Category
 */

@DatabaseTable(tableName = "workordercategories")
public class WorkOrderCategory {
    @DatabaseField(generatedId = true)
    private Long id;

    @DatabaseField(canBeNull = false)
    private String name;

    @DatabaseField(canBeNull = false, foreign = true)
    private WorkOrderProductType productType;

    public WorkOrderCategory(String name, WorkOrderProductType productType) {
        this.name = name;
        this.productType = productType;
    }

    public String getName() {
        return name;
    }

    WorkOrderProductType getProductType() {
        return productType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkOrderCategory that = (WorkOrderCategory) o;
        return Objects.equal(id, that.id) &&
                Objects.equal(name, that.name) &&
                Objects.equal(productType, that.productType);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, name, productType);
    }
}
