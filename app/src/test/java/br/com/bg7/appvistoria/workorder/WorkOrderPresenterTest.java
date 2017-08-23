package br.com.bg7.appvistoria.workorder;

import junit.framework.Assert;

import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import br.com.bg7.appvistoria.auth.AuthTest;
import br.com.bg7.appvistoria.data.WorkOrder;
import br.com.bg7.appvistoria.data.source.local.fake.FakeConfigRepository;
import br.com.bg7.appvistoria.data.source.local.fake.FakeWorkOrderRepository;

import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.verify;

/**
 * Created by: elison
 * Date: 2017-08-17
 */
public class WorkOrderPresenterTest {

    @Mock
    WorkOrderContract.View workOrderView;

    private FakeConfigRepository configRepository = new FakeConfigRepository();

    private FakeWorkOrderRepository workOrderRepository = new FakeWorkOrderRepository();

    private WorkOrderPresenter workOrderPresenter;


    private WorkOrder workOrder = new WorkOrder();

    @Before
    public void setUp() throws IOException {
        MockitoAnnotations.initMocks(this);

        AuthTest auth = new AuthTest(configRepository);

        workOrderPresenter =  new WorkOrderPresenter(workOrderRepository, workOrderView, configRepository);
        auth.setUpConfig("pt_BR");

        workOrderPresenter.start();
    }

    @Test
    public void shouldInitializePresenter()
    {
        verify(workOrderView).setPresenter(workOrderPresenter);
    }

    @Test
    public void shouldShowListItemsWhenStart() {
        verify(workOrderView).showList(ArgumentMatchers.<WorkOrder>anyList(),anyBoolean());
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
