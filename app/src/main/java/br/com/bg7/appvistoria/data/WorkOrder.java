package br.com.bg7.appvistoria.data;

import com.orm.SugarRecord;

import org.joda.time.DateTime;

import java.util.List;

import br.com.bg7.appvistoria.workorder.WorkOrderStatus;

/**
 * Created by: elison
 * Date: 2017-08-15
 */
public class WorkOrder extends SugarRecord<WorkOrder> {
    private String name;
    private String summary;
    private String shortSummary;
    private WorkOrderStatus status;
    private DateTime endAt;
    private String address;
    private Long externalId;
    private List<Inspection> inspections;

    /**
     * Default constructor used by Sugar
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
