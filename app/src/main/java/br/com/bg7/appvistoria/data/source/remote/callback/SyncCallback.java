package br.com.bg7.appvistoria.data.source.remote.callback;

import br.com.bg7.appvistoria.data.ProductInspection;

/**
 * Created by: elison
 * Date: 2017-07-27
 */
public interface SyncCallback {
    void onSuccess(ProductInspection productInspection);
    void onProgressUpdated(ProductInspection productInspection, double progress);
    void onFailure(ProductInspection productInspection, Throwable t);
}
