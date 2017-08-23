package br.com.bg7.appvistoria.workorder;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.List;

import br.com.bg7.appvistoria.UserLoggedInTest;
import br.com.bg7.appvistoria.auth.Auth;
import br.com.bg7.appvistoria.data.Config;
import br.com.bg7.appvistoria.data.WorkOrder;
import br.com.bg7.appvistoria.data.source.local.fake.FakeWorkOrderRepository;

import static org.mockito.Mockito.verify;

/**
 * Created by: elison
 * Date: 2017-08-17
 */
public class WorkOrderPresenterTest extends UserLoggedInTest {

    @Mock
    WorkOrderContract.View workOrderView;

    private FakeWorkOrderRepository workOrderRepository = new FakeWorkOrderRepository();

    private WorkOrderPresenter workOrderPresenter;

    private WorkOrder workOrder = new WorkOrder();

    @Before
    public void setUp() throws IOException {
        super.setUp();
        MockitoAnnotations.initMocks(this);


        workOrderPresenter =  new WorkOrderPresenter(workOrderRepository, workOrderView, configRepository);

        workOrderPresenter.start();
    }

    @Test
    public void shouldInitializePresenter()
    {
        verify(workOrderView).setPresenter(workOrderPresenter);
    }

    @Test
    public void shouldShowListItemsWhenStart() {
        Config config = configRepository.findByUser(Auth.user());
        String language = config.getLanguageName();
        List<WorkOrder> workOrderList =  workOrderRepository.findAllOrderByStatus(language);
        verify(workOrderView).showList(ArgumentMatchers.eq(workOrderList),ArgumentMatchers.eq(true));
    }

    @Test
    public void shouldExpandMoreInfoAndHighLightWhenMoreInfoClicked() {
        workOrderPresenter.moreInfoClicked(workOrder);
        verify(workOrderView).expandInfoPanel(workOrder);
        verify(workOrderView).highlightInfoButton(workOrder);
    }

    @Test
    public void shouldShrinkMoreInfoAndRemoveHighLightWhenMoreInfoClicked() {
        workOrderPresenter.hideInfoClicked(workOrder);
        verify(workOrderView).shrinkInfoPanel(workOrder);
        verify(workOrderView).removeInfoButtonHighlight(workOrder);
    }
}
