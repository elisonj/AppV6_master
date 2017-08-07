package br.com.bg7.appvistoria.config;

import com.google.common.base.Strings;

import java.util.List;

import br.com.bg7.appvistoria.auth.Auth;
import br.com.bg7.appvistoria.config.vo.Language;
import br.com.bg7.appvistoria.data.Config;
import br.com.bg7.appvistoria.data.source.local.ConfigRepository;
import br.com.bg7.appvistoria.data.source.local.LanguageRepository;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by: luciolucio
 * Date: 2017-07-17
 */

class ConfigPresenter implements ConfigContract.Presenter {
    private static final boolean DEFAULT_WIFI_OPTION = true;
    private static final int DEFAULT_LANGUAGE_INDEX = 0;

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
        List<Language> languageList = languageRepository.getLanguages();
        configView.setLanguages(languageList);

        Config config = configRepository.findByUsername(Auth.user());
        if(config == null) {
            applyDefaultConfig(languageList);
            return;
        }

        loadConfig(config, languageList);
    }

    private void loadConfig(Config config, List<Language> languageList) {
        String languageName = config.getLanguageName();
        if (Strings.isNullOrEmpty(languageName) || Strings.isNullOrEmpty(languageName.trim())) {
            languageName = languageList.get(DEFAULT_LANGUAGE_INDEX).getName();
        }

        boolean languageExists = false;
        for (Language language : languageList) {
            if (languageName.equals(language.getName())) {
                languageExists = true;
                break;
            }
        }

        if (!languageExists) {
            languageName = languageList.get(DEFAULT_LANGUAGE_INDEX).getName();
        }

        applyConfig(languageName, config.isSyncWithWifiOnly());
    }

    private void applyDefaultConfig(List<Language> languageList) {
        applyConfig(languageList.get(DEFAULT_LANGUAGE_INDEX).getName(), DEFAULT_WIFI_OPTION);
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

        Config config = configRepository.findByUsername(Auth.user());
        if(config != null) {
            configRepository.delete(config);
        }

        config = new Config(language, syncWithWifiOnly, Auth.user());
        configRepository.save(config);

        configView.changeLanguage(language);
    }

    @Override
    public void cancelClicked() {
        configView.hideButtons();
        configView.hideLanguages();
        start();
    }
}
