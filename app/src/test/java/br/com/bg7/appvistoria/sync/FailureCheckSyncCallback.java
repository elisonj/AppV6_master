package br.com.bg7.appvistoria.sync;

import br.com.bg7.appvistoria.data.ProductInspection;
import br.com.bg7.appvistoria.data.source.remote.SyncCallback;

/**
 * Created by: luciolucio
 * Date: 2017-08-03
 */

abstract class FailureCheckSyncCallback implements SyncCallback {
    @Override
    public void onSuccess(ProductInspection productInspection) {

    }

    @Override
    public void onProgressUpdated(ProductInspection productInspection, double progress) {

    }
}
