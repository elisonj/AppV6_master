package br.com.bg7.appvistoria.sync;

import junit.framework.Assert;

import br.com.bg7.appvistoria.data.Inspection;
import br.com.bg7.appvistoria.data.source.remote.callback.SyncCallback;

/**
 * Created by: elison
 * Date: 2017-08-05
 */

class FailWhenOnFailureCalledCallback implements SyncCallback {
    @Override
    public void onSuccess(Inspection inspection) {

    }

    @Override
    public void onProgressUpdated(Inspection inspection, Integer progress) {

    }

    @Override
    public void onFailure(Inspection inspection, Throwable t) {
        Assert.fail("onFailure called");
    }
}
