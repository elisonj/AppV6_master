package br.com.bg7.appvistoria.data;

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

    String getProductTypeName() {
        return productType.getName();
    }

    Long getProductTypeId() {
        return productType.getExternalId();
    }
}
