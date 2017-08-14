package br.com.bg7.appvistoria.config;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.bg7.appvistoria.auth.Auth;
import br.com.bg7.appvistoria.auth.FakeAuthFacade;
import br.com.bg7.appvistoria.config.vo.Language;
import br.com.bg7.appvistoria.data.Config;
import br.com.bg7.appvistoria.data.User;
import br.com.bg7.appvistoria.data.source.local.LanguageRepository;
import br.com.bg7.appvistoria.data.source.local.fake.FakeConfigRepository;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by: luciolucio
 * Date: 2017-07-21
 */

public class ConfigPresenterTest {
    private static final User USER = new User("username", "token", "pwd");

    @Mock
    private ConfigContract.View configView;

    private FakeConfigRepository configRepository = new FakeConfigRepository();

    @Mock
    private LanguageRepository languageRepository;

    private FakeAuthFacade authFacade = new FakeAuthFacade();

    private ConfigPresenter configPresenter;

    @Before
    public void setUp() throws IOException {
        MockitoAnnotations.initMocks(this);

        Auth.configure(authFacade);
        authFacade.fakeLogin(USER);

        configPresenter = new ConfigPresenter(configRepository, languageRepository, configView);

        setUpLanguages();
        setUpConfig("pt_BR");
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

    @Test
    public void shouldInitializePresenter()
    {
        verify(configView).setPresenter(configPresenter);
    }

    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullForConfigRepository()
    {
        new ConfigPresenter(null, languageRepository, configView);
    }

    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullForLanguageRepository()
    {
        new ConfigPresenter(configRepository, null, configView);
    }

    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullForConfigView()
    {
        new ConfigPresenter(configRepository, languageRepository, null);
    }

    @Test
    public void shouldSelectLanguageOnStart() {
        setUpConfig("en_US");

        configPresenter.start();

        verify(configView).setLanguage("en_US");
    }


    @Test
    public void shouldSelectFirstItemIfConfigLanguageNotValid() {
        HashMap<String, String> testCases = new HashMap<>();
        testCases.put("Not in list", "blah");
        testCases.put("Empty", "");
        testCases.put("Blank", "   ");
        testCases.put("Null", null);

        for (Map.Entry<String, String> testCase : testCases.entrySet()) {
            String description = testCase.getKey();
            String configValueUnderTest = testCase.getValue();

            setUpConfig(configValueUnderTest);
            reset(configView);

            configPresenter.start();

            System.out.println("shouldSelectFirstItemIfConfigLanguageNotValid - " + description);
            verify(configView).setLanguage("pt_BR");
        }
    }

    @Test
    public void shouldSetDefaultsWhenNoConfig() {
        configRepository.clear();

        configPresenter.start();

        verify(configView).setLanguage("pt_BR");
    }

    @Test
    public void shouldShowButtonsWhenLanguageSelectedIsDifferent()
    {
        configPresenter.languageSelected();
        verify(configView).showButtons();
    }

    @Test
    public void shouldHideElementsWhenCancelClicked()
    {
        configPresenter.cancelClicked();

        verify(configView).hideButtons();
    }

    @Test
    public void shouldHideButtonsAndLanguagesWhenConfirmClicked()
    {
        configPresenter.confirmClicked(null);

        verify(configView).hideButtons();
    }

    @Test
    public void shouldChangeLanguageWhenConfirmClicked()
    {
        configPresenter.confirmClicked("pt_BR");

        verify(configView).changeLanguage("pt_BR");
    }

    @Test
    public void shouldLogout()
    {
        configPresenter.logoutClicked();

        verify(configView).logoutApplication();
    }
}
