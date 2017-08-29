package br.com.bg7.appvistoria.config;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import br.com.bg7.appvistoria.UserLoggedInTest;
import br.com.bg7.appvistoria.auth.Auth;
import br.com.bg7.appvistoria.config.vo.Language;
import br.com.bg7.appvistoria.data.source.local.fake.FakeLanguageRepository;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;

/**
 * Created by: luciolucio
 * Date: 2017-07-21
 */

public class ConfigPresenterTest extends UserLoggedInTest {
    @Mock
    private ConfigContract.View configView;

    private FakeLanguageRepository languageRepository = new FakeLanguageRepository();

    private ConfigPresenter configPresenter;

    @Before
    public void setUp() throws IOException {
        super.setUp();
        MockitoAnnotations.initMocks(this);

        configPresenter = new ConfigPresenter(configRepository, languageRepository, configView);
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
        setUpConfig("en");

        configPresenter.start();

        verify(configView).setSelectedLanguage(new Language("en", "English"));
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
            verify(configView).setSelectedLanguage(new Language("pt", "Portugues"));
        }
    }

    /*
    @Test
    public void shouldShowButtonsWhenLanguageSelectedIsDifferent()
    {
        configPresenter.start();
        configPresenter.languageChangeClicked("en");
        verify(configView).showButtons();
    }

    @Test
    public void shouldNotShowButtonsWhenLanguageSelectedIsTheSame()
    {
        configPresenter.start();
        configPresenter.languageChangeClicked("pt");
        verify(configView, never()).showButtons();
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
        configPresenter.confirmClicked("pt");

        verify(configView).changeLanguage("pt");
    }
    */

    @Test
    public void shouldLogoutAndShowLoginScreenWhenLoggingOut()
    {
        configPresenter.confirmLogoutClicked();

        verify(configView).showLoginScreen();
        Assert.assertNull(Auth.user());
        Assert.assertFalse(Auth.check());
    }
}
