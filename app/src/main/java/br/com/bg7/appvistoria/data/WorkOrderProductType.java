package br.com.bg7.appvistoria.data;

import com.google.common.base.Objects;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by: elison
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

    @DatabaseField(canBeNull = false)
    private String name;

    @SuppressWarnings("unused")
    WorkOrderProductType() {
        // used by ormlite
    }

    public WorkOrderProductType(Long externalId, String name) {
        this.externalId = externalId;
        this.name = name;
    }

    public Long getExternalId() {
        return externalId;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkOrderProductType that = (WorkOrderProductType) o;
        return Objects.equal(id, that.id) &&
                Objects.equal(externalId, that.externalId) &&
                Objects.equal(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, externalId, name);
    }
}
