package br.com.bg7.appvistoria.sync;

import org.junit.Assert;

import br.com.bg7.appvistoria.data.ProductInspection;
import br.com.bg7.appvistoria.data.source.remote.SyncCallback;

/**
 * Created by: luciolucio
 * Date: 2017-08-03
 */

class SyncCallbackCheck implements SyncCallback {

    private ProductInspection inspection;
    private Throwable throwable;

    SyncCallbackCheck(ProductInspection inspection) {
        this.inspection = inspection;
    }

    public SyncCallbackCheck(ProductInspection inspection, Throwable throwable) {
        this.inspection = inspection;
        this.throwable = throwable;
    }

    @Override
    public void onSuccess(ProductInspection productInspection) {
        Assert.assertEquals(inspection, productInspection);
    }

    @Override
    public void onProgressUpdated(ProductInspection productInspection, double progress) {
        Assert.assertEquals(inspection, productInspection);
    }

    @Override
    public void onFailure(ProductInspection productInspection, Throwable t) {
        Assert.assertEquals(inspection, productInspection);
        Assert.assertEquals(throwable, t);
    }
}
