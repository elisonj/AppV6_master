package br.com.bg7.appvistoria.workorder;

import junit.framework.Assert;

import org.apache.commons.lang3.StringUtils;
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

import br.com.bg7.appvistoria.auth.Auth;
import br.com.bg7.appvistoria.auth.FakeAuthFacade;
import br.com.bg7.appvistoria.config.vo.Language;
import br.com.bg7.appvistoria.data.Config;
import br.com.bg7.appvistoria.data.User;
import br.com.bg7.appvistoria.data.WorkOrder;
import br.com.bg7.appvistoria.data.source.local.LanguageRepository;
import br.com.bg7.appvistoria.data.source.local.fake.FakeConfigRepository;
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
    private WorkOrder ob1 = new WorkOrder("Projeto 1", "Resumo completo - 50 carros, 30 motos, 20 caminhões, 13 vans, 5 empilhadeiras, 1 trator", WorkOrderStatus.IN_PROGRESS);
    private WorkOrder ob2 = new WorkOrder("Projeto 2", "Resumo completo --- 50 carros, 30 motos, 20 caminhões, 13 vans, 5 empilhadeiras, 1 trator", WorkOrderStatus.COMPLETED);
    private WorkOrder ob3 = new WorkOrder("Projeto 3", "Resumo completo ------ 50 carros, 30 motos, 20 caminhões, 13 vans, 5 empilhadeiras, 1 trator", WorkOrderStatus.NOT_STARTED);

    private Locale localePtBr = new Locale("pt", "BR");
    private Locale localeEnglish = new Locale("en");

    @Mock
    WorkOrderContract.View workOrderView;

    private FakeConfigRepository configRepository = new FakeConfigRepository();

    @Mock
    private LanguageRepository languageRepository;

    @Mock
    FakeWorkOrderRepository fakeWorkOrderRepository;

    private WorkOrderPresenter workOrderPresenter;

    private FakeAuthFacade authFacade = new FakeAuthFacade();

    private WorkOrder workOrder = new WorkOrder();

    @Before
    public void setUp() throws IOException {
        MockitoAnnotations.initMocks(this);

        Auth.configure(authFacade);
        authFacade.fakeLogin(USER);

        workOrderList.add(ob1);
        workOrderList.add(ob2);
        workOrderList.add(ob3);

        workOrderPresenter =  new WorkOrderPresenter(fakeWorkOrderRepository, workOrderView, configRepository);

        setUpLanguages();
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
            Assert.assertTrue(workOrder.getShortSummary().length() <= WorkOrder.MAX_SIZE_SHORT_SUMMARY+3);
        }
    }

    @Test
    public void shouldShortSummaryNotEndWithNumeric() {
        for (WorkOrder workOrder: workOrderList) {
            String lastElement = workOrder.getShortSummary().substring(workOrder.getShortSummary().lastIndexOf(" "), workOrder.getShortSummary().length()-3);
            Assert.assertFalse(StringUtils.isNumeric(lastElement.trim()));
        }
    }

    @Test
    public void shouldShowDateBr() {
        String stringDate = ob1.getEndAt(localePtBr);
        Assert.assertTrue(stringDate.contains("/"));
    }

    @Test
    public void shouldShowDateUS() {
        String stringDate = ob1.getEndAt(localeEnglish);
        Assert.assertTrue(stringDate.contains("-"));
    }

    private void showInitialItems() {
        workOrderPresenter.start();
        verify(workOrderView).showList(ArgumentMatchers.<WorkOrder>anyList(),anyBoolean());
    }

    private void setUpLanguages() {
        List<Language> languages = Arrays.asList(
                new Language("pt_BR", null),
                new Language("en_US", null)
        );
        when(languageRepository.getLanguages()).thenReturn(languages);
    }

    private void setUpConfig(String language) {
        Config config = new Config(language, USER);
        configRepository.save(config);

    }

}
