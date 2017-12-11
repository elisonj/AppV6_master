package br.com.bg7.appvistoria.workorder;

import br.com.bg7.appvistoria.data.WorkOrder;

import static br.com.bg7.appvistoria.data.TestableSummaryWorkOrder.LOCATION;
import static br.com.bg7.appvistoria.data.TestableSummaryWorkOrder.PROJECT;

/**
 * Created by: elison
 * Date: 2017-09-17
 */

class NotStartedWorkOrder extends WorkOrder {
    NotStartedWorkOrder() {
        super(PROJECT, LOCATION);
    }
}
