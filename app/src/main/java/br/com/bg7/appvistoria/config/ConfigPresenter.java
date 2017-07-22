package br.com.bg7.appvistoria.config;

import com.google.common.base.Strings;

import java.util.List;

import br.com.bg7.appvistoria.data.source.local.ConfigRepository;
import br.com.bg7.appvistoria.data.Config;
import br.com.bg7.appvistoria.config.vo.Language;
import br.com.bg7.appvistoria.data.source.local.LanguageRepository;

/**
 * Created by: luciolucio
 * Date: 2017-07-17
 */

class ConfigPresenter implements ConfigContract.Presenter {
    private final ConfigContract.View configView;
    private final ConfigRepository configRepository;
    private final LanguageRepository languageRepository;

    ConfigPresenter(ConfigRepository configRepository, LanguageRepository languageRepository, ConfigContract.View configView) {
        this.configRepository = configRepository;
        this.languageRepository = languageRepository;
        this.configView = configView;

        this.configView.setPresenter(this);
    }

    @Override
    public void start() {
        // TODO: Agora que o teste passou, refactoring disso aqui
        List<Language> languageList = languageRepository.getLanguages();
        configView.setLanguages(languageList);

        Config config = configRepository.first(Config.class);
        if(config != null) {
            String languageName = config.getLanguageName();
            if (Strings.isNullOrEmpty(languageName) || Strings.isNullOrEmpty(languageName.trim())) {
                languageName = languageList.get(0).getName();
            }

            boolean languageExists = false;
            for (Language language : languageList) {
                if (languageName.equals(language.getName())) {
                    languageExists = true;
                    break;
                }
            }

            if (!languageExists) {
                languageName = languageList.get(0).getName();
            }

            configView.setLanguage(languageName);
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
    public void confirmClicked(String language, boolean syncWithWifiOnly) {
        configView.hideButtons();
        configView.hideLanguages();

        Config config = configRepository.first(Config.class);
        if(config != null) {
            configRepository.deleteAll(Config.class);
        }

        config = new Config(language, syncWithWifiOnly);
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
