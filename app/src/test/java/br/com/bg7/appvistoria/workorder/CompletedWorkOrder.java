package br.com.bg7.appvistoria.workorder;

import br.com.bg7.appvistoria.data.WorkOrder;

/**
 * Created by: luciolucio
 * Date: 2017-08-22
 */

 class CompletedWorkOrder extends WorkOrder {
     CompletedWorkOrder(String name, String summary) {
        super(name, summary);
        this.finish();
    }
}
