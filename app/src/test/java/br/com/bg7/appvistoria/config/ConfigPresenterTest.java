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

import br.com.bg7.appvistoria.data.source.local.ConfigRepository;
import br.com.bg7.appvistoria.data.Config;
import br.com.bg7.appvistoria.config.vo.Country;

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

    private ConfigPresenter configPresenter;

    private List<Country> countries;

    private Config config;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        configPresenter = new ConfigPresenter(configRepository, configView);

        setUpCountries();
        setUpConfig("lang", true);
    }

    private void setUpCountries() {
        countries = Arrays.asList(
                new Country("lang", null, null),
                new Country("lang2", null, null)
        );
        when(configView.initCountryList()).thenReturn(countries);
    }

    private void setUpConfig(String language, boolean isSyncWithWifiOnly) {
        config = new Config(language, isSyncWithWifiOnly);
        when(configRepository.first(Config.class)).thenReturn(config);
    }

    @Test
    public void shouldInitCountryListOnStart() {
        configPresenter.start();

        verify(configView).initCountryList();
    }

    @Test
    public void shouldSelectLanguageOnStart() {
        setUpConfig("lang2", true);

        configPresenter.start();

        verify(configView).setLanguage(1);
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
        testCases.put("Blank", "");
        testCases.put("Null", "");

        for (Map.Entry<String, String> testCase : testCases.entrySet()) {
            String description = testCase.getKey();
            String configValueUnderTest = testCase.getValue();

            setUpConfig(testCases.get(configValueUnderTest), true);
            reset(configView);

            configPresenter.start();

            try {
                verify(configView).setLanguage(0);
            } catch (MockitoAssertionError error) {
                throw new MockitoAssertionError(error, "Config test case - " + description);
            }
        }
    }
}
