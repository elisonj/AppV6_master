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
    private String sumary;
    private String shortSumary;
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

    public WorkOrder(String name, String shortSumary, WorkOrderStatus status) {
        this.name = name;
        this.shortSumary = shortSumary;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public String getShortSumary() {
        return shortSumary;
    }

    public WorkOrderStatus getStatus() {
        return status;
    }
}
