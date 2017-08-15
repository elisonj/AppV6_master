package br.com.bg7.appvistoria.data;

import org.joda.time.DateTime;

import java.util.List;

import br.com.bg7.appvistoria.workorder.WorkOrderStatus;

/**
 * Created by: elison
 * Date: 2017-08-15
 */
public class WorkOrder {
    private String name;
    private String sumary;
    private String shortSumary;
    private WorkOrderStatus status;
    private DateTime endAt;
    private String address;
    private Long externalId;
    private List<Inspection> inspections;
}
