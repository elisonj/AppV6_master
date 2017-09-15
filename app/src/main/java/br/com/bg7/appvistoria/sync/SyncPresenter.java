package br.com.bg7.appvistoria.sync;

import com.google.common.collect.Lists;

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
    private List<Inspection> inspections;

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
        inspections = inspectionRepository.findByStatus(InspectionStatus.COMPLETED);
        view.showInspections(SyncList.fromInspections(inspections));

        syncManager.subscribe(callback);
    }

    @Override
    public void syncClicked(Long inspectionId) {
        Inspection inspection = getInspectionById(inspectionId);
        if (inspection == null) return;

        inspection.readyToSync();
        inspectionRepository.save(inspection);
        view.showUnderInProgress(inspection.getId());
    }

    @Override
    public void retryClicked(Long inspectionId) {
        Inspection inspection = getInspectionById(inspectionId);
        if (inspection != null) {
            inspection.reset();
            inspectionRepository.save(inspection);
            view.showUnderNotStarted(inspectionId);
        }
    }

    private Inspection getInspectionById(Long inspectionId) {
        for (Inspection inspection : inspections) {
            if (inspection.getId().equals(inspectionId)) {
                return inspection;
            }
        }
        return null;
    }
}
