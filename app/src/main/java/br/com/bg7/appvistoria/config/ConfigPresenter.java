package br.com.bg7.appvistoria.config;

import com.google.common.base.Strings;

import java.util.List;

import br.com.bg7.appvistoria.BuildConfig;
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

    private final ConfigContract.View configView;
    private final ConfigRepository configRepository;
    private final LanguageRepository languageRepository;
    private String currentLanguage;

    ConfigPresenter(ConfigRepository configRepository, LanguageRepository languageRepository, ConfigContract.View configView) {
        this.configRepository = checkNotNull(configRepository);
        this.languageRepository = checkNotNull(languageRepository);
        this.configView = checkNotNull(configView);

        this.configView.setPresenter(this);
    }

    @Override
    public void start() {
        List<Language> languageList = languageRepository.getLanguages();
        configView.setLanguageList(languageList);

        Config config = configRepository.findByUser(Auth.user());
        loadConfig(config, languageList);
    }

    private void loadConfig(Config config, List<Language> languageList) {
        String languageName = config.getLanguageName();

        if (Strings.isNullOrEmpty(languageName) || Strings.isNullOrEmpty(languageName.trim())) {
            languageName = BuildConfig.DEFAULT_LANGUAGE_NAME;
        }

        boolean languageExists = false;
        for (Language language : languageList) {
            if (languageName.equals(language.getName())) {
                languageExists = true;
                break;
            }
        }

        if (!languageExists) {
            languageName = BuildConfig.DEFAULT_LANGUAGE_NAME;
        }

        applyConfig(languageName);
    }

    private void applyConfig(String name) {
        configView.setSelectedLanguage(name);
        currentLanguage = name;
    }

    @Override
    public void languageChangeClicked(String language) {
        if(!language.equals(currentLanguage))
            configView.showLanguageChangeConfirmation(language);
    }

    @Override
    public void confirmLanguageChangeClicked(String language) {
        Config config = new Config(language, Auth.user());
        Config existingConfig = configRepository.findByUser(Auth.user());

        if(existingConfig != null) {
            config = existingConfig
                    .withLanguage(language);
        }

        configRepository.save(config);

        configView.hideLanguageChangeConfirmation();
        configView.changeLanguage(language);
    }

    @Override
    public void cancelLanguageChangeClicked() {
        configView.hideLanguageChangeConfirmation();
    }

    @Override
    public void logoutClicked() {
        configView.showLogoutConfirmation();
    }

    @Override
    public void confirmLogoutClicked() {
        Auth.logout();

        configView.hideLogoutConfirmation();
        configView.showLoginScreen();
    }

    @Override
    public void cancelLogoutClicked() {
        configView.hideLogoutConfirmation();
    }
}
