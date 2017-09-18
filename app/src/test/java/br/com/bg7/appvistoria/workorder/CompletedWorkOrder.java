package br.com.bg7.appvistoria.workorder;

import java.util.ArrayList;

import br.com.bg7.appvistoria.data.WorkOrder;
import br.com.bg7.appvistoria.data.WorkOrderProduct;

import static br.com.bg7.appvistoria.data.TestableSummaryWorkOrder.LOCATION;
import static br.com.bg7.appvistoria.data.TestableSummaryWorkOrder.PROJECT;

/**
 * Created by: luciolucio
 * Date: 2017-08-22
 */

 class CompletedWorkOrder extends WorkOrder {
     CompletedWorkOrder() {
        super(PROJECT, LOCATION);
        this.finish();
    }
}
