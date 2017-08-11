package br.com.bg7.appvistoria.data.source.remote.callback;

import br.com.bg7.appvistoria.data.Inspection;

/**
 * Created by: elison
 * Date: 2017-07-27
 */
public interface SyncCallback {
    void onSuccess(Inspection inspection);
    void onProgressUpdated(Inspection inspection, double progress);
    void onFailure(Inspection inspection, Throwable t);
}
