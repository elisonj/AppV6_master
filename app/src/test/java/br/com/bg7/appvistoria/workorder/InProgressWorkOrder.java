package br.com.bg7.appvistoria.workorder;

import java.util.ArrayList;

import br.com.bg7.appvistoria.data.WorkOrder;
import br.com.bg7.appvistoria.data.WorkOrderProduct;

import static br.com.bg7.appvistoria.data.TestableSummaryWorkOrder.LOCATION;
import static br.com.bg7.appvistoria.data.TestableSummaryWorkOrder.PROJECT;

/**
 * Created by: elison
 * Date: 2017-08-22
 */

public class InProgressWorkOrder extends WorkOrder {
    public InProgressWorkOrder() {
        super(PROJECT, LOCATION);
        this.start();
    }
}
