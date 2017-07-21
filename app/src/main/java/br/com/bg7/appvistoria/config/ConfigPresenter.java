package br.com.bg7.appvistoria.config;

import java.util.List;

import br.com.bg7.appvistoria.data.source.local.ConfigRepository;
import br.com.bg7.appvistoria.vo.Config;
import br.com.bg7.appvistoria.vo.Country;

/**
 * Created by: luciolucio
 * Date: 2017-07-17
 */

public class ConfigPresenter implements ConfigContract.Presenter {
    private final ConfigContract.View configView;
    private final ConfigRepository configRepository;

    public ConfigPresenter(ConfigRepository configRepository, ConfigContract.View configView) {
        this.configView = configView;
        this.configRepository = configRepository;

        this.configView.setPresenter(this);
    }

    @Override
    public void start() {

        List<Country> countryList = configView.initCountryList();
        configView.setCountries(countryList);

        // TODO: Olhar isso quando descansado. Tem alguma responsabilidade da view perdida aqui
        Config config = configRepository.first(Config.class);
        if(config != null) {
            int selected = 0;
            for (int i = 0; i < countryList.size(); i++) {
                Country country = countryList.get(i);
                if (country.getId().equals(config.getLanguage())) {
                    selected = i;
                }
            }
            configView.setLanguage(selected);
            configView.setSyncWithWifiOnly(config.isSyncWithWifiOnly());
        }
    }

    @Override
    public void topLanguagesClicked() {
        configView.showButtons();
        configView.toggleLanguagesVisibility();
    }

    @Override
    public void syncWithWifiOnlyClicked() {
        configView.showButtons();
    }

    @Override
    public void syncWithWifiOnlyLineClicked() {
        configView.showButtons();
        configView.toggleSyncWithWifiOnly();
    }

    @Override
    public void confirmClicked(String languageId, String language, boolean syncWithWifiOnly) {
        configView.hideButtons();
        configView.hideLanguages();

        Config config = configRepository.first(Config.class);
        if(config != null) {
            configRepository.deleteAll(Config.class);
        }

        config = new Config(languageId, syncWithWifiOnly);
        configRepository.save(config);

        configView.changeLanguage(language);
        configView.refresh();
    }

    @Override
    public void cancelClicked() {
        configView.hideButtons();
        configView.hideLanguages();
        start();
    }
}
