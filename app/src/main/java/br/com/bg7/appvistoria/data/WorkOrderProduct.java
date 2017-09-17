package br.com.bg7.appvistoria.data;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by: luciolucio
 * Date: 2017-09-17
 *
 * TODO: Merge disso com o data.Product
 */

@DatabaseTable(tableName = "workorderproducts")
public class WorkOrderProduct {
    @DatabaseField(generatedId = true)
    private Long id;

    @DatabaseField(canBeNull = false, foreign = true)
    private WorkOrderCategory category;

    public WorkOrderProduct(WorkOrderCategory category) {
        this.category = category;
    }

    public String getProductType() {
        return category.getProductTypeName();
    }

    public Long getProductTypeId() {
        return category.getProductTypeId();
    }

    public WorkOrderCategory getCategory() {
        return category;
    }
}
