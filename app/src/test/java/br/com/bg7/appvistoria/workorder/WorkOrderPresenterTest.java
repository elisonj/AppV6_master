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

    private static final int MAX_SUMMARY = 40;

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

        DateTime date = new DateTime(2017, 8, 22, 0, 0, 0);
        DateTimeUtils.setCurrentMillisFixed(date.getMillis());

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

    @Test
    public void shouldShowLocalizedDates() {
        WorkOrder wo = new InProgressWorkOrder("", "");

        String date = wo.getEndAt(new Locale("pt", "BR"));
        Assert.assertEquals("22/08/2017", date);

        date = wo.getEndAt(new Locale("en", "US"));
        Assert.assertEquals("8/22/2017", date);

        date = wo.getEndAt(new Locale("en", "GB"));
        Assert.assertEquals("22/08/2017", date);
    }

    @Test
    public void shouldCropShortSummaries() {

        ArrayList<SummaryTestCase> testCases = new ArrayList<>();
        testCases.add(new SummaryTestCase("Carros: 50, motos: 30, caminhões: 20",
                "Carros: 50, motos: 30, caminhões: 20"));
        testCases.add(new SummaryTestCase("Carros: 500, motos: 300, caminhões: 2305",
                "Carros: 500, motos: 300, caminhões: 2305"));
        testCases.add(new SummaryTestCase("Carros: 50, motos: 30, caminhões: 20, vans: 13, empilhadeiras: 5, trator: 1",
                                    "Carros: 50, motos: 30, caminhões: 20..."));
        testCases.add(new SummaryTestCase("Motos: 300, caminhões: 200, trator: 14567",
                "Motos: 300, caminhões: 200..."));
        testCases.add(new SummaryTestCase("Motos: 30023, trator: 14567, caminhões: 200",
                "Motos: 30023, trator: 14567..."));
        testCases.add(new SummaryTestCase("Motos: 30023, trator: 14567, vans: 1333, caminhões: 200",
                "Motos: 30023, trator: 14567, vans: 1333..."));

        for (SummaryTestCase testCase: testCases) {
            testSummary(testCase.actual, testCase.expected);
        }
    }

    private void testSummary(String actual, String expect) {
        WorkOrder workOrder = new WorkOrder("Projeto", actual);
        Assert.assertEquals(expect, workOrder.getShortSummary(MAX_SUMMARY));
    }

    private class SummaryTestCase {
        String actual;
        String expected;

        SummaryTestCase(String actual, String expected) {
            this.actual = actual;
            this.expected = expected;
        }
    }
}
