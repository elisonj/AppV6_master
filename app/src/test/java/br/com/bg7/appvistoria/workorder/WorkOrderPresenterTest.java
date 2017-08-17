package br.com.bg7.appvistoria.workorder;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

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
