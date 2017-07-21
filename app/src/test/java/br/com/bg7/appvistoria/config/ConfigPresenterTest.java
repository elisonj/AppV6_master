package br.com.bg7.appvistoria.config;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.exceptions.base.MockitoAssertionError;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.bg7.appvistoria.config.vo.Language;
import br.com.bg7.appvistoria.data.source.local.ConfigRepository;
import br.com.bg7.appvistoria.data.Config;
import br.com.bg7.appvistoria.data.source.local.LanguageRepository;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by: luciolucio
 * Date: 2017-07-21
 */

public class ConfigPresenterTest {
    @Mock
    private ConfigContract.View configView;

    @Mock
    private ConfigRepository configRepository;

    @Mock
    private LanguageRepository languageRepository;

    private ConfigPresenter configPresenter;

    private List<Language> languages;

    private Config config;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        configPresenter = new ConfigPresenter(configRepository, languageRepository, configView);

        setUpLanguages();
        setUpConfig("pt_BR", true);
    }

    private void setUpLanguages() {
        languages = Arrays.asList(
                new Language("pt_BR", null),
                new Language("en_US", null)
        );
        when(languageRepository.getLanguages()).thenReturn(languages);
    }

    private void setUpConfig(String language, boolean isSyncWithWifiOnly) {
        config = new Config(language, isSyncWithWifiOnly);
        when(configRepository.first(Config.class)).thenReturn(config);
    }

    @Test
    public void shouldSelectLanguageOnStart() {
        setUpConfig("en_US", true);

        configPresenter.start();

        verify(configView).setLanguage("en_US");
    }

    @Test
    public void shouldSelectSyncNetworkOptionOnStart() {
        configPresenter.start();

        verify(configView).setSyncWithWifiOnly(true);
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

            setUpConfig(configValueUnderTest, true);
            reset(configView);

            configPresenter.start();

            System.out.println("shouldSelectFirstItemIfConfigLanguageNotValid - " + description);
            verify(configView).setLanguage("pt_BR");
        }
    }
}
