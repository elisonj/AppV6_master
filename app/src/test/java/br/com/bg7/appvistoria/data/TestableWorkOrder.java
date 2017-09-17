package br.com.bg7.appvistoria.data;

/**
 * Created by: luciolucio
 * Date: 2017-09-17
 */

class TestableWorkOrder extends WorkOrder {

    private String summary;

    TestableWorkOrder(String name, String address, String summary) {
        super(name, address);
        this.summary = summary;
    }

    @Override
    protected String ellipsizeShortSummary(String summary, int maxSize) {
        return super.ellipsizeShortSummary(this.summary, maxSize);
    }
}
