package br.com.bg7.appvistoria.sync;

import java.util.HashSet;

import br.com.bg7.appvistoria.data.Inspection;
import br.com.bg7.appvistoria.data.source.local.InspectionRepository;
import br.com.bg7.appvistoria.data.source.remote.callback.SyncCallback;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by: luciolucio
 * Date: 2017-08-02
 */

public class SyncManagerCallback implements SyncCallback {
    private HashSet<SyncCallback> subscribers = new HashSet<>();
    private InspectionRepository inspectionRepository;

    public SyncManagerCallback(InspectionRepository inspectionRepository) {
        this.inspectionRepository = inspectionRepository;
    }

    /**
     * Method that gets called when an {@code Inspection} finishes syncing successfully
     *
     * @param inspection The inspection whose sync succeeded
     */
    @Override
    public void onSuccess(Inspection inspection) {
        inspectionRepository.save(inspection);

        for (SyncCallback callback : subscribers) {
            callback.onSuccess(inspection);
        }
    }

    /**
     * Notifies all subscribers of the consolidated inspection progress
     *
     * @param inspection The inspection being synced
     * @param progress A 0-100 number indicating percentage completed
     */
    @Override
    public void onProgressUpdated(Inspection inspection, Integer progress) {
        for (SyncCallback callback : subscribers) {
            callback.onProgressUpdated(inspection, progress);
        }
    }

    /**
     * Method that gets called when an {@code Inspection} fails syncing
     *
     * @param inspection The inspection whose sync failed
     * @param t The error that caused it to fail
     */
    @Override
    public void onFailure(Inspection inspection, Throwable t) {
        inspectionRepository.save(inspection);

        for (SyncCallback callback : subscribers) {
            callback.onFailure(inspection, t);
        }
    }

    void subscribe(SyncCallback syncCallback) {
        subscribers.add(checkNotNull(syncCallback));
    }

    void unsubscribe(SyncCallback syncCallback) {
        subscribers.remove(checkNotNull(syncCallback));
    }
}
