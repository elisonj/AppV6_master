package br.com.bg7.appvistoria.config;

import java.util.List;

import br.com.bg7.appvistoria.vo.Config;
import br.com.bg7.appvistoria.vo.Country;

/**
 * Created by: luciolucio
 * Date: 2017-07-17
 */

public class ConfigPresenter implements ConfigContract.Presenter {
    private final ConfigContract.View configView;

    public ConfigPresenter(ConfigContract.View configView) {
        this.configView = configView;

        this.configView.setPresenter(this);
    }

    @Override
    public void start() {

        List<Country> countryList = configView.initCountryList();
        configView.setCountries(countryList);

        // TODO: Olhar isso quando descansado. Tem alguma responsabilidade da view perdida aqui
        List<Config> list = Config.listAll(Config.class);
        if(list != null && list.size() > 0) {
            int selected = 0;
            for (int i = 0; i < countryList.size(); i++) {
                Country country = countryList.get(i);
                if (country.getId().equals(list.get(0).getLanguage())) {
                    selected = i;
                }
            }
            configView.setLanguage(selected);
            configView.setSyncWithWifiOnly(list.get(0).isSyncWithWifiOnly());
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

        List<Config> list = Config.listAll(Config.class);
        if(list != null && list.size() > 0) {
            Config.deleteAll(Config.class);
        }
        Config config = new Config(languageId, syncWithWifiOnly);
        config.save();

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
