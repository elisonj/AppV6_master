package br.com.bg7.appvistoria.sync;

import junit.framework.Assert;

import br.com.bg7.appvistoria.data.ProductInspection;
import br.com.bg7.appvistoria.data.source.remote.callback.SyncCallback;

/**
 * Created by: luciolucio
 * Date: 2017-08-05
 */

class FailWhenOnFailureCalledCallback implements SyncCallback {
    @Override
    public void onSuccess(ProductInspection productInspection) {

    }

    @Override
    public void onProgressUpdated(ProductInspection productInspection, double progress) {

    }

    @Override
    public void onFailure(ProductInspection productInspection, Throwable t) {
        Assert.fail("onFailure called");
    }
}
