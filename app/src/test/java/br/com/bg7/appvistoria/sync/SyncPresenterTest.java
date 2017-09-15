package br.com.bg7.appvistoria.sync;

import com.google.common.collect.Lists;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import br.com.bg7.appvistoria.data.Inspection;
import br.com.bg7.appvistoria.data.StubInspection;
import br.com.bg7.appvistoria.data.WorkOrder;
import br.com.bg7.appvistoria.data.source.local.fake.FakeInspectionSyncRepository;
import br.com.bg7.appvistoria.data.source.remote.InspectionService;
import br.com.bg7.appvistoria.data.source.remote.PictureService;
import br.com.bg7.appvistoria.sync.vo.SyncList;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

/**
 * Created by: elison
 * Date: 2017-09-14
 */
public class SyncPresenterTest {

    @Mock
    SyncManager syncManager;

    @Mock
    SyncContract.View syncView;

    @Mock
    InspectionService inspectionService;

    @Mock
    PictureService pictureService;

    SyncList syncList;

    @Mock
    SyncExecutor syncExecutor;


    FakeInspectionSyncRepository fakeInspectionRepository = new FakeInspectionSyncRepository();

    private SyncPresenter syncPresenter;
    private SyncList syncListObject;
    private List<Inspection> listInspections;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        syncPresenter  = new SyncPresenter(
                fakeInspectionRepository,
                syncManager,
                syncView,
                pictureService,
                inspectionService);

        populateRepository();

        listInspections = Lists.newArrayList(fakeInspectionRepository.findByStatus(InspectionStatus.COMPLETED));
        syncList = SyncList.fromInspections(listInspections);

        syncPresenter.start();
//        syncListObject = getSyncList();

    }

    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullRepository() {
        syncPresenter  = new SyncPresenter(
                null,
                syncManager,
                syncView,
                pictureService,
                inspectionService);
    }

    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullSyncManager() {
        syncPresenter  = new SyncPresenter(
                fakeInspectionRepository,
                null,
                syncView,
                pictureService,
                inspectionService);
    }

    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullSyncView() {
        syncPresenter  = new SyncPresenter(
                fakeInspectionRepository,
                syncManager,
                null,
                pictureService,
                inspectionService);
    }


    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullPictureService() {
        syncPresenter  = new SyncPresenter(
                fakeInspectionRepository,
                syncManager,
                syncView,
                null,
                inspectionService);
    }

    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullInspectionService() {
        syncPresenter  = new SyncPresenter(
                fakeInspectionRepository,
                syncManager,
                syncView,
                pictureService,
                null);
    }


    @Test
    public void shouldShowInspectionsWhenStart() {
        verify(syncView).showInspections(eq(syncList));
    }

    @Test
    public void shouldShowErrorMessageWhenFailSync() {

    }


    @Test
    public void shouldShowSyncSuccessWhenSyncClickedAndSyncOk() {

        Inspection inspection = listInspections.get(0);

        syncPresenter.syncClicked(inspection.getId());

        verify(syncView).showUnderInProgress(listInspections.get(0).getId());
        verify(syncView).showUnderCompleted(listInspections.get(0).getId());
    }

    @Test
    public void shouldShowUnderNotStartedWhenRetryCicked() {

    }

    @Test
    public void shouldShowUnderInProgressWhenSyncClicked() {
        syncPresenter.syncClicked(listInspections.get(0).getId());

        verify(syncView).showUnderInProgress(listInspections.get(0).getId());
    }

    @Test
    public void shouldShowUnderErrorWhenFailSync() {

    }

    @Test
    public void shouldShowUnderCompletedWhenSyncAll() {

    }

    private void populateRepository() {
        WorkOrder workOrder = new WorkOrder("Name","Summary", "Address");

        StubInspection inspection1 = new StubInspection(1L,"FORD/BB51");
        inspection1.setWorkOrder(workOrder);
        StubInspection inspection2 = new StubInspection(2L,"FORD/BB51");
        inspection2.setWorkOrder(workOrder);
        StubInspection inspection3 = new StubInspection(3L,"FORD/BB51");
        inspection3.setWorkOrder(workOrder);
        StubInspection inspection4 = new StubInspection(4L,"FORD/BB51");
        inspection4.setWorkOrder(workOrder);
        StubInspection inspection5 = new StubInspection(5L,"FORD/BB51");
        inspection5.setWorkOrder(workOrder);

        inspection1.readyToSync();
        inspection2.setSyncStatus(SyncStatus.INSPECTION_BEING_SYNCED);
        inspection3.setSyncStatus(SyncStatus.FAILED);
        inspection4.setSyncStatus(SyncStatus.DONE);
        inspection5.readyToSync();

        fakeInspectionRepository.save(inspection1);
        fakeInspectionRepository.save(inspection2);
        fakeInspectionRepository.save(inspection3);
        fakeInspectionRepository.save(inspection4);
        fakeInspectionRepository.save(inspection5);
    }
}
