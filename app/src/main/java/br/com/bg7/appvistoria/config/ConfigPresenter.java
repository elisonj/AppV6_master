package br.com.bg7.appvistoria.config;

import java.util.List;

import br.com.bg7.appvistoria.data.source.local.ConfigRepository;
import br.com.bg7.appvistoria.data.Config;
import br.com.bg7.appvistoria.config.vo.Language;

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

        List<Language> languageList = configView.initLanguageList();
        configView.setLanguages(languageList);

        // TODO: Olhar isso quando descansado. Tem alguma responsabilidade da view perdida aqui
        Config config = configRepository.first(Config.class);
        if(config != null) {
            int selected = 0;
            for (int i = 0; i < languageList.size(); i++) {
                Language language = languageList.get(i);
                if (language.getId().equals(config.getLanguage())) {
                    selected = i;
                }
            }
            configView.setLanguage(selected);
            configView.setSyncWithWifiOnly(config.isSyncWithWifiOnly());
        }
    }

    @Override
    public void languagesLabelClicked() {
        configView.showButtons();
        configView.toggleLanguagesVisibility();
    }

    @Override
    public void syncWithWifiOnlyClicked() {
        configView.showButtons();
    }

    @Override
    public void syncLabelClicked() {
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
