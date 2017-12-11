package br.com.bg7.appvistoria.data;

import junit.framework.Assert;

import java.util.ArrayList;

import static br.com.bg7.appvistoria.data.TestableSummaryWorkOrder.LOCATION;
import static br.com.bg7.appvistoria.data.TestableSummaryWorkOrder.PROJECT;

/**
 * Created by: elison
 * Date: 2017-09-17
 */
class WorkOrderSummaryTestCase {

    private ArrayList<WorkOrderProduct> products = new ArrayList<>();
    private String expectedSummary;

    private WorkOrderSummaryTestCase(String expectedSummary) {
        this.expectedSummary = expectedSummary;
    }

    static WorkOrderSummaryTestCase expectedSummary(String expectedSummary) {
        return new WorkOrderSummaryTestCase(expectedSummary);
    }

    WorkOrderSummaryTestCase withProducts(WorkOrderCategory category, int quantity) {
        for (int i = 0; i < quantity; i++) {
            products.add(new WorkOrderProduct(category));
        }

        return this;
    }

    void run() {
        WorkOrder workOrder = new WorkOrder(PROJECT, LOCATION);
        workOrder.addProducts(this.products);

        Assert.assertEquals(this.expectedSummary, workOrder.getSummary());
    }
}
