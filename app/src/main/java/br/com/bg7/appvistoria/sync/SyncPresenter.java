package br.com.bg7.appvistoria.sync;

import android.annotation.SuppressLint;

import java.util.HashMap;
import java.util.List;

import br.com.bg7.appvistoria.data.Inspection;
import br.com.bg7.appvistoria.data.source.local.InspectionRepository;
import br.com.bg7.appvistoria.data.source.remote.callback.SyncCallback;
import br.com.bg7.appvistoria.sync.vo.SyncList;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by: elison
 * Date: 2017-09-12
 */
public class SyncPresenter implements SyncContract.Presenter {

    private InspectionRepository inspectionRepository;
    private SyncManager syncManager;
    private SyncContract.View view;

    // Esse lint sugere uso de uma classe no SDK Android, que nao usamos no presenter
    @SuppressLint("UseSparseArrays")
    private HashMap<Long, Inspection> inspections = new HashMap<>();

    private SyncCallback callback = new SyncCallback() {
        @Override
        public void onSuccess(Inspection inspection) {
            view.showUnderCompleted(inspection.getId());
            view.showSyncSuccessMessage();
        }

        @Override
        public void onProgressUpdated(Inspection inspection, Integer progress) {
            view.showPercentage(inspection.getPercentageCompleted(), inspection.getId());
        }

        @Override
        public void onFailure(Inspection inspection, Throwable t) {
            view.showUnderError(inspection.getId());
            view.showSyncErrorMessage();
        }
    };

    public SyncPresenter(InspectionRepository inspectionRepository, SyncManager syncManager, SyncContract.View view) {
        this.inspectionRepository = checkNotNull(inspectionRepository);
        this.syncManager = checkNotNull(syncManager);
        this.view = checkNotNull(view);

        this.view.setPresenter(this);
    }

    @Override
    public void start() {
        List<Inspection> inspectionsFromRepository = inspectionRepository.findByStatus(InspectionStatus.COMPLETED);
        for (Inspection inspection : inspectionsFromRepository) {
            inspections.put(inspection.getId(), inspection);
        }

        view.showInspections(SyncList.fromInspections(inspectionsFromRepository));

        syncManager.subscribe(callback);
    }

    @Override
    public void syncClicked(Long inspectionId) {
        Inspection inspection = inspections.get(inspectionId);
        if (inspection == null) return;

        inspection.readyToSync();
        inspectionRepository.save(inspection);
        view.showUnderInProgress(inspection.getId());
    }

    @Override
    public void retryClicked(Long inspectionId) {
        Inspection inspection = inspections.get(inspectionId);

        if (inspection != null) {
            inspection.reset();
            inspectionRepository.save(inspection);
            view.showUnderNotStarted(inspectionId);
        }
    }
}
