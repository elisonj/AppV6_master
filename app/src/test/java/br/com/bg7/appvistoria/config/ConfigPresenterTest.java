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

import static br.com.bg7.appvistoria.data.source.local.fake.FakeLanguageRepository.EN_LANGUAGE;
import static br.com.bg7.appvistoria.data.source.local.fake.FakeLanguageRepository.PT_LANGUAGE;
import static org.mockito.ArgumentMatchers.any;
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

        verify(configView).setSelectedLanguage(EN_LANGUAGE);
    }


    @Test
    public void shouldSelectFirstItemIfConfigLanguageNotValid() {
        HashMap<String, String> testCases = new HashMap<>();
        testCases.put("Not in list", "blah");
        testCases.put("Empty", "");
        testCases.put("Blank", "   ");
        testCases.put("Null", null);

        for (Map.Entry<String, String> testCase : testCases.entrySet()) {
            String configValueUnderTest = testCase.getValue();

            setUpConfig(configValueUnderTest);
            reset(configView);

            configPresenter.start();

            verify(configView).setSelectedLanguage(PT_LANGUAGE);
        }
    }

    @Test
    public void shouldShowConfirmationWhenLanguageSelectedIsDifferent()
    {
        configPresenter.start();
        configPresenter.languageChangeClicked(EN_LANGUAGE);
        verify(configView).showLanguageChangeConfirmation(EN_LANGUAGE);
    }

    @Test
    public void shouldNotShowConfirmationWhenLanguageSelectedIsTheSame()
    {
        configPresenter.start();
        configPresenter.languageChangeClicked(PT_LANGUAGE);
        verify(configView, never()).showLanguageChangeConfirmation(any(Language.class));
    }

    @Test
    public void shouldHideLanguageConfirmationWhenCancelClicked()
    {
        configPresenter.languageChangeClicked(EN_LANGUAGE);
        configPresenter.cancelLanguageChangeClicked();

        verify(configView).setSelectedLanguage(PT_LANGUAGE); // Previous value
        verify(configView).hideLanguageChangeConfirmation();
    }

    @Test
    public void shouldHideLanguageConfirmationWhenConfirmClicked()
    {
        configPresenter.confirmLanguageChangeClicked(EN_LANGUAGE);

        verify(configView).hideLanguageChangeConfirmation();
    }

    @Test
    public void shouldChangeLanguageWhenConfirmClicked()
    {
        configPresenter.confirmLanguageChangeClicked(PT_LANGUAGE);

        verify(configView).changeLanguage(PT_LANGUAGE);
        Assert.assertEquals(PT_LANGUAGE.getName(), configRepository.findByUser(Auth.user()).getLanguageName());
    }

    @Test
    public void shouldShowConfirmationWhenLogoutClicked() {
        configPresenter.logoutClicked();

        verify(configView).showLogoutConfirmation();
    }

    @Test
    public void shouldHideConfirmationWhenCancelLogoutClicked() {
        configPresenter.cancelLogoutClicked();

        verify(configView).hideLogoutConfirmation();
    }

    @Test
    public void shouldLogoutHideConfirmationAndShowLoginScreenWhenLoggingOut()
    {
        configPresenter.confirmLogoutClicked();

        verify(configView).hideLogoutConfirmation();
        verify(configView).showLoginScreen();
        Assert.assertFalse(Auth.check());
    }
}
