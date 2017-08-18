package br.com.bg7.appvistoria.data;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Collection;

import br.com.bg7.appvistoria.workorder.WorkOrderStatus;

/**
 * Created by: elison
 * Date: 2017-08-15
 */
@DatabaseTable(tableName = "workorders")
public class WorkOrder {

    @DatabaseField(generatedId = true)
    private Long id;

    @DatabaseField(canBeNull = false)
    private String name;

    @DatabaseField(canBeNull = false)
    private String summary;

    @DatabaseField(canBeNull = false)
    private String shortSummary;

    @DatabaseField(index = true, canBeNull = false)
    private WorkOrderStatus status;

    @DatabaseField(canBeNull = false)
    private DateTime endAt;

    @DatabaseField(canBeNull = false)
    private String address;

    @DatabaseField(canBeNull = false)
    private Long externalId;

    @ForeignCollectionField
    private Collection<Inspection> inspections = new ArrayList<>();

    /**
     * Default constructor used by ormlite
     */
    @SuppressWarnings("unused")
    public WorkOrder() {}

    public WorkOrder(String name,String summary, String shortSummary, WorkOrderStatus status) {
        this.name = name;
        this.summary = summary;
        this.shortSummary = shortSummary;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public String getShortSummary() {
        return shortSummary;
    }

    public String getSummary() {
        return summary;
    }

    public WorkOrderStatus getStatus() {
        return status;
    }
}
