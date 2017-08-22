package br.com.bg7.appvistoria.workorder;

import junit.framework.Assert;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import br.com.bg7.appvistoria.BuildConfig;
import br.com.bg7.appvistoria.auth.Auth;
import br.com.bg7.appvistoria.auth.FakeAuthFacade;
import br.com.bg7.appvistoria.config.vo.Language;
import br.com.bg7.appvistoria.data.Config;
import br.com.bg7.appvistoria.data.User;
import br.com.bg7.appvistoria.data.WorkOrder;
import br.com.bg7.appvistoria.data.source.local.fake.FakeConfigRepository;
import br.com.bg7.appvistoria.data.source.local.fake.FakeLanguageRepository;
import br.com.bg7.appvistoria.data.source.local.fake.FakeWorkOrderRepository;

import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by: elison
 * Date: 2017-08-17
 */
public class WorkOrderPresenterTest {

    private static final User USER = new User("username", "token", "pwd");

    private List<WorkOrder> workOrderList = new ArrayList<>();
    private WorkOrder ob1 = new InProgressWorkOrder("Projeto 1", "Resumo completo - 50 carros, 30 motos, 20 caminhões, 13 vans, 5 empilhadeiras, 1 trator");
    private WorkOrder ob2 = new CompletedWorkOrder("Projeto 2", "Resumo completo --- 50 carros, 30 motos, 20 caminhões, 13 vans, 5 empilhadeiras, 1 trator");
    private WorkOrder ob3 = new WorkOrder("Projeto 3", "Resumo completo ------ 50 carros, 30 motos, 20 caminhões, 13 vans, 5 empilhadeiras, 1 trator");

    @Mock
    WorkOrderContract.View workOrderView;

    private FakeConfigRepository configRepository = new FakeConfigRepository();

    private FakeLanguageRepository languageRepository = new FakeLanguageRepository();

    private FakeWorkOrderRepository workOrderRepository = new FakeWorkOrderRepository();

    private WorkOrderPresenter workOrderPresenter;

    private FakeAuthFacade authFacade = new FakeAuthFacade();

    private WorkOrder workOrder = new WorkOrder();

    @Before
    public void setUp() throws IOException {
        MockitoAnnotations.initMocks(this);

        Auth.configure(authFacade);
        authFacade.fakeLogin(USER);

        DateTime date = new DateTime(2017, 8, 22, 0, 0, 0);
        DateTimeUtils.setCurrentMillisFixed(date.getMillis());

        workOrderList.add(ob1);
        workOrderList.add(ob2);
        workOrderList.add(ob3);

        workOrderPresenter =  new WorkOrderPresenter(workOrderRepository, workOrderView, configRepository);

        setUpConfig("pt_BR");
    }


    @Test
    public void shouldInitializePresenter()
    {
        verify(workOrderView).setPresenter(workOrderPresenter);
    }

    @Test
    public void shouldShowListItemsWhenStart() {
        showInitialItems();
    }

    @Test
    public void shouldExpandMoreInfoAndHighLightWhenMoreInfoClicked() {
        showInitialItems();

        workOrderPresenter.moreInfoClicked(workOrder);
        verify(workOrderView).expandInfoPanel(workOrder);
        verify(workOrderView).highlightInfoButton(workOrder);
    }

    @Test
    public void shouldShrinkMoreInfoAndRemoveHighLightWhenMoreInfoClicked() {
        showInitialItems();

        workOrderPresenter.hideInfoClicked(workOrder);
        verify(workOrderView).shrinkInfoPanel(workOrder);
        verify(workOrderView).removeInfoButtonHighlight(workOrder);
    }

    @Test
    public void shouldCropShortSummary() {
        for (WorkOrder workOrder: workOrderList) {
            Assert.assertTrue(workOrder.getShortSummary(BuildConfig.MAX_SIZE_SHORT_SUMMARY).length() <= BuildConfig.MAX_SIZE_SHORT_SUMMARY + 3);
        }
    }

    @Test
    public void shouldShortSummaryNotEndWithNumeric() {
        for (WorkOrder workOrder: workOrderList) {
            String lastElement = workOrder.getShortSummary(BuildConfig.MAX_SIZE_SHORT_SUMMARY)
                    .substring(
                            workOrder.getShortSummary(BuildConfig.MAX_SIZE_SHORT_SUMMARY).lastIndexOf(" "),
                            workOrder.getShortSummary(BuildConfig.MAX_SIZE_SHORT_SUMMARY).length() - 3
                    );

            Assert.assertFalse(StringUtils.isNumeric(lastElement.trim()));
        }
    }

    @Test
    public void shouldShowLocalizedDates() {
        String date = ob1.getEndAt(new Locale("pt", "BR"));
        Assert.assertEquals("22/08/2017", date);

        date = ob1.getEndAt(new Locale("en", "US"));
        Assert.assertEquals("8/22/2017", date);

        date = ob1.getEndAt(new Locale("en", "GB"));
        Assert.assertEquals("22/08/2017", date);
    }

    private void showInitialItems() {
        workOrderPresenter.start();
        verify(workOrderView).showList(ArgumentMatchers.<WorkOrder>anyList(),anyBoolean());
    }

    private void setUpConfig(String language) {
        Config config = new Config(language, USER);
        configRepository.save(config);

    }

}
