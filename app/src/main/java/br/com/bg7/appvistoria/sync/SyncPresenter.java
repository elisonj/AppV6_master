package br.com.bg7.appvistoria.sync;

import java.util.List;

import br.com.bg7.appvistoria.data.Inspection;
import br.com.bg7.appvistoria.data.source.local.InspectionRepository;
import br.com.bg7.appvistoria.data.source.remote.InspectionService;
import br.com.bg7.appvistoria.data.source.remote.PictureService;
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
    private SyncList syncList;
    private List<Inspection> inspections;
    private PictureService pictureService;
    private InspectionService inspectionService;

    Runnable updateQueue;

    Runnable sync;


    public SyncPresenter(InspectionRepository inspectionRepository, SyncManager syncManager, SyncContract.View view,
                         PictureService pictureService, InspectionService inspectionService) {
        this.inspectionRepository = checkNotNull(inspectionRepository);
        this.syncManager = checkNotNull(syncManager);
        this.view = checkNotNull(view);
        this.pictureService = checkNotNull(pictureService);
        this.inspectionService = checkNotNull(inspectionService);

        this.view.setPresenter(this);
    }

    @Override
    public void start() {
        inspections = inspectionRepository.findBySyncStatus(InspectionStatus.COMPLETED);
        syncList = SyncList.fromInspections(inspections);
        view.showInspections(syncList);

        syncManager.subscribe(callbackPicture);
    }


    @Override
    public void syncClicked(Long inspectionId) {
       trySyncInspection(getInspectionById(inspectionId));
    }

    @Override
    public void retryClicked(Long inspectionId) {
        Inspection inspection = getInspectionById(inspectionId);
        if(inspection != null) {
            inspection.reset();
            view.showUnderNotStarted(inspectionId);
        }
    }

    private Inspection getInspectionById(Long inspectionId) {
        for(Inspection inspection: inspections) {
            if(inspection.getId().equals(inspectionId)) {
                return inspection;
            }
        }
        return null;
    }


    private void trySyncInspection(Inspection inspection) {
        if(inspection == null) return;

        view.showUnderInProgress(inspection.getId());

        if(inspection.canSyncPictures()) {
            inspection.sync(pictureService, callbackPicture);
            syncManager.subscribe(callbackPicture);
            return;
        }
        inspection.sync(inspectionService, callbackInspection);
        syncManager.subscribe(callbackPicture);

    }

        private SyncCallback callbackPicture = new SyncCallback() {
        @Override
        public void onSuccess(Inspection inspection) {
            inspection.sync(inspectionService, callbackInspection);
            view.showUnderInProgress(inspection.getId());
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

    private SyncCallback callbackInspection = new SyncCallback() {
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

}
