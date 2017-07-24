package br.com.bg7.appvistoria.config;

import com.google.common.base.Strings;

import java.util.List;

import br.com.bg7.appvistoria.data.source.local.ConfigRepository;
import br.com.bg7.appvistoria.data.Config;
import br.com.bg7.appvistoria.config.vo.Language;
import br.com.bg7.appvistoria.data.source.local.LanguageRepository;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by: luciolucio
 * Date: 2017-07-17
 */

class ConfigPresenter implements ConfigContract.Presenter {
    private final ConfigContract.View configView;
    private final ConfigRepository configRepository;
    private final LanguageRepository languageRepository;

    ConfigPresenter(ConfigRepository configRepository, LanguageRepository languageRepository, ConfigContract.View configView) {
        this.configRepository = checkNotNull(configRepository);
        this.languageRepository = checkNotNull(languageRepository);
        this.configView = checkNotNull(configView);

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

            applyConfig(languageName, config.isSyncWithWifiOnly());
            return;
        }

        applyDefaultConfig(languageList);
    }

    private void applyDefaultConfig(List<Language> languageList) {
        applyConfig(languageList.get(0).getName(), true);
    }

    private void applyConfig(String name, boolean syncWithWifiOnly) {
        configView.setLanguage(name);
        configView.setSyncWithWifiOnly(syncWithWifiOnly);
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
