package br.com.bg7.appvistoria.sync;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import br.com.bg7.appvistoria.data.Inspection;
import br.com.bg7.appvistoria.data.StubInspection;
import br.com.bg7.appvistoria.data.WorkOrder;
import br.com.bg7.appvistoria.data.source.local.fake.FakeInspectionSyncRepository;
import br.com.bg7.appvistoria.data.source.remote.InspectionService;
import br.com.bg7.appvistoria.data.source.remote.PictureService;
import br.com.bg7.appvistoria.data.source.remote.callback.SyncCallback;
import br.com.bg7.appvistoria.projectselection.vo.Location;
import br.com.bg7.appvistoria.projectselection.vo.Project;
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

    @Captor
    ArgumentCaptor<SyncCallback> syncCallbackCaptor;

    @Mock
    SyncExecutor syncExecutor;

    private FakeInspectionSyncRepository fakeInspectionRepository = new FakeInspectionSyncRepository();


    private SyncList syncList;

    private SyncPresenter syncPresenter;
    private SyncList syncListObject;
    private List<Inspection> listInspections;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        syncPresenter = new SyncPresenter(
                fakeInspectionRepository,
                syncManager,
                syncView);

        populateRepository();

        listInspections = fakeInspectionRepository.findByStatus(InspectionStatus.COMPLETED);
        syncList = SyncList.fromInspections(listInspections);

        syncPresenter.start();
        verify(syncManager).subscribe(syncCallbackCaptor.capture());
    }

    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullRepository() {
        syncPresenter = new SyncPresenter(
                null,
                syncManager,
                syncView);
    }

    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullSyncManager() {
        syncPresenter = new SyncPresenter(
                fakeInspectionRepository,
                null,
                syncView);
    }

    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullSyncView() {
        syncPresenter = new SyncPresenter(
                fakeInspectionRepository,
                syncManager,
                null);
    }

    @Test
    public void shouldShowInspectionsWhenStart() {
        verify(syncView).showInspections(eq(syncList));
    }

    @Test
    public void shouldShowErrorMessageWhenFailSync() {
        syncCallbackCaptor.getValue().onFailure(listInspections.get(0), new Throwable());
        verify(syncView).showSyncErrorMessage();
    }

    @Test
    public void shouldShowSyncSuccessWhenSyncClickedAndSyncOk() {

        Inspection inspection = listInspections.get(0);

        syncPresenter.syncClicked(inspection.getId());
        syncCallbackCaptor.getValue().onSuccess(listInspections.get(0));

        verify(syncView).showUnderInProgress(listInspections.get(0).getId());
        verify(syncView).showUnderCompleted(listInspections.get(0).getId());

    }

    @Test
    public void shouldShowUnderNotStartedWhenRetryCicked() {
        syncPresenter.retryClicked(listInspections.get(0).getId());

        verify(syncView).showUnderNotStarted(listInspections.get(0).getId());
    }

    @Test
    public void shouldShowUnderInProgressWhenSyncClicked() {
        syncPresenter.syncClicked(listInspections.get(0).getId());

        verify(syncView).showUnderInProgress(listInspections.get(0).getId());
    }

    @Test
    public void shouldShowUnderErrorWhenFailSync() {
        syncCallbackCaptor.getValue().onFailure(listInspections.get(0), new Throwable());
        verify(syncView).showUnderError(listInspections.get(0).getId());
    }

    @Test
    public void shouldShowUnderCompletedWhenSync() {
        syncCallbackCaptor.getValue().onSuccess(listInspections.get(0));
        verify(syncView).showSyncSuccessMessage();
    }

    private void populateRepository() {
        WorkOrder workOrder = new WorkOrder(new Project(1L, "Name"), new Location(1L, "Address"));

        StubInspection inspection1 = new StubInspection(1L, "FORD/BB51");
        inspection1.setWorkOrder(workOrder);
        StubInspection inspection2 = new StubInspection(2L, "FORD/BB51");
        inspection2.setWorkOrder(workOrder);
        StubInspection inspection3 = new StubInspection(3L, "FORD/BB51");
        inspection3.setWorkOrder(workOrder);
        StubInspection inspection4 = new StubInspection(4L, "FORD/BB51");
        inspection4.setWorkOrder(workOrder);
        StubInspection inspection5 = new StubInspection(5L, "FORD/BB51");
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
