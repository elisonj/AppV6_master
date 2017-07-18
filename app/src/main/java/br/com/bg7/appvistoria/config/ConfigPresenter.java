package br.com.bg7.appvistoria.config;

import java.util.List;

import br.com.bg7.appvistoria.vo.Config;

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

    }

    @Override
    public void topLanguagesClicked() {
        configView.showButtons();
        configView.toggleLanguagesVisibility();
    }

    @Override
    public void syncWithWifiOnlyClicked() {
        configView.showButtons();
        configView.toggleSyncWithWifiOnly();
    }

    @Override
    public void confirmClicked(String languageId, boolean syncWithWifiOnly) {
        configView.hideButtons();
        configView.hideLanguages();

        List<Config> list = Config.listAll(Config.class);
        if(list != null && list.size() > 0) {
            Config.deleteAll(Config.class);
        }
        Config config = new Config(languageId, syncWithWifiOnly);
        config.save();

        configView.changeLanguage();
        configView.refresh();
    }

    @Override
    public void cancelClicked() {
        configView.hideButtons();
        configView.hideLanguages();
    }
}
