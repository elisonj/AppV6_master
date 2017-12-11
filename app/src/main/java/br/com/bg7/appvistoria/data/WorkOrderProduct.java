package br.com.bg7.appvistoria.data;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by: elison
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

    @DatabaseField(canBeNull = false, foreign = true)
    private WorkOrder workOrder;

    @SuppressWarnings("unused")
    WorkOrderProduct() {
        // used by ormlite
    }

    public WorkOrderProduct(WorkOrderCategory category) {
        this.category = category;
    }

    public WorkOrderProduct(WorkOrderCategory category, WorkOrder workOrder) {
        this(category);
        this.workOrder = workOrder;
    }

    public WorkOrderProductType getProductType() {
        return category.getProductType();
    }

    public WorkOrderCategory getCategory() {
        return category;
    }
}
