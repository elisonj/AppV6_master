package br.com.bg7.appvistoria.data;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by: luciolucio
 * Date: 2017-09-17
 *
 * TODO: Merge disso com o data.ProductType
 */

@DatabaseTable(tableName = "workorderproducttypes")
public class WorkOrderProductType {
    @DatabaseField(generatedId = true)
    private Long id;

    @DatabaseField(canBeNull = false)
    private Long externalId;

    @DatabaseField(canBeNull = false, foreign = true)
    private String name;

    public WorkOrderProductType(Long externalId, String name) {
        this.externalId = externalId;
        this.name = name;
    }

    Long getExternalId() {
        return externalId;
    }

    String getName() {
        return name;
    }
}
