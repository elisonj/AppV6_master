package br.com.bg7.appvistoria.syncinspection;

import java.util.List;

import br.com.bg7.appvistoria.data.Inspection;
import br.com.bg7.appvistoria.data.source.local.InspectionRepository;
import br.com.bg7.appvistoria.sync.SyncManager;
import br.com.bg7.appvistoria.syncinspection.vo.SyncList;

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


    public SyncPresenter(InspectionRepository inspectionRepository, SyncManager syncManager, SyncContract.View view) {
        this.inspectionRepository = checkNotNull(inspectionRepository);
        this.syncManager = checkNotNull(syncManager);
        this.view = checkNotNull(view);
        this.view.setPresenter(this);
    }

    @Override
    public void start() {
        List<Inspection> inspectionsToReset = inspectionRepository.findBySyncStatus(InspectionStatus.COMPLETED);
        syncList = SyncList.fromInspections(inspectionsToReset);
        view.showInspections(syncList);
    }
}
