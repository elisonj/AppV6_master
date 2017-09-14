package br.com.bg7.appvistoria.sync;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import br.com.bg7.appvistoria.data.Inspection;
import br.com.bg7.appvistoria.data.StubInspection;
import br.com.bg7.appvistoria.data.WorkOrder;
import br.com.bg7.appvistoria.data.source.local.fake.FakeInspectionRepository;
import br.com.bg7.appvistoria.data.source.remote.InspectionService;
import br.com.bg7.appvistoria.data.source.remote.PictureService;
import br.com.bg7.appvistoria.sync.vo.SyncList;
import br.com.bg7.appvistoria.sync.vo.SyncListItem;
import br.com.bg7.appvistoria.sync.vo.SyncListItemDetails;
import br.com.bg7.appvistoria.sync.vo.SyncListStatus;

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


    FakeInspectionRepository fakeInspectionRepository = new FakeInspectionRepository();

    private SyncPresenter syncPresenter;
    private SyncList syncListObject;

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

        syncListObject = getSyncList();

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
        List<Inspection> list = fakeInspectionRepository.findBySyncStatus(InspectionStatus.COMPLETED);

        syncList = SyncList.fromInspections(list);

        syncPresenter.start();
        verify(syncView).showInspections(eq(syncList));
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


    private SyncList getSyncList() {

        SyncList syncList = new SyncList();

        List<SyncListItem> syncListItem = new ArrayList<>();

        SyncListItem listItemNotStarted = new SyncListItem(SyncListStatus.NOT_STARTED);
        SyncListItem listItemInProgress = new SyncListItem(SyncListStatus.IN_PROGRESS);
        SyncListItem listItemError = new SyncListItem(SyncListStatus.ERROR);
        SyncListItem listItemCompleted = new SyncListItem(SyncListStatus.COMPLETED);

        List<SyncListItemDetails> detailNotStarted = new ArrayList<>();
        List<SyncListItemDetails> detailInProgress = new ArrayList<>();
        List<SyncListItemDetails> detailItemError = new ArrayList<>();
        List<SyncListItemDetails> detailItemCompleted = new ArrayList<>();

        SyncListItemDetails detail1 = new SyncListItemDetails();
        detail1.setDescription("Description 1");
        detail1.setProject("Project 1");
        detail1.setId(1L);

        SyncListItemDetails detail2 = new SyncListItemDetails();
        detail2.setDescription("Description 2");
        detail2.setProject("Project 2");
        detail2.setId(2L);

        SyncListItemDetails detail3 = new SyncListItemDetails();
        detail3.setDescription("Description 3");
        detail3.setProject("Project 3");
        detail3.setId(3L);

        SyncListItemDetails detail4 = new SyncListItemDetails();
        detail4.setDescription("Description 4");
        detail4.setProject("Project 4");
        detail4.setId(4L);

        SyncListItemDetails detail5 = new SyncListItemDetails();
        detail5.setDescription("Description 5");
        detail5.setProject("Project 5");
        detail5.setId(5L);

        detailNotStarted.add(detail1);
        detailNotStarted.add(detail2);
        detailInProgress.add(detail3);
        detailItemError.add(detail4);
        detailItemCompleted.add(detail5);

        listItemNotStarted.setCount(listItemNotStarted.getInspections().size());
        listItemInProgress.setCount(listItemInProgress.getInspections().size());
        listItemError.setCount(listItemError.getInspections().size());
        listItemCompleted.setCount(listItemCompleted.getInspections().size());

        syncListItem.add(listItemNotStarted);
        syncListItem.add(listItemInProgress);
        syncListItem.add(listItemCompleted);
        syncListItem.add(listItemError);

        syncList.setSyncListItem(syncListItem);

        return syncList;
    }


}
