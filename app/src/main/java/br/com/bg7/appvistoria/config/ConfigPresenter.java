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
 * Created by: elison
 * Date: 2017-07-17
 */

class ConfigPresenter implements ConfigContract.Presenter {

    private final ConfigContract.View configView;
    private final ConfigRepository configRepository;
    private final LanguageRepository languageRepository;
    private Language currentLanguage;

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

    @Override
    public void languageChangeClicked(Language language) {
        if(!language.equals(currentLanguage))
            configView.showLanguageChangeConfirmation(language);
    }

    @Override
    public void confirmLanguageChangeClicked(Language language) {
        Config config = new Config(language.getName(), Auth.user());
        Config existingConfig = configRepository.findByUser(Auth.user());

        if(existingConfig != null) {
            config = existingConfig
                    .withLanguage(language.getName());
        }

        configRepository.save(config);

        configView.hideLanguageChangeConfirmation();
        configView.changeLanguage(language);
    }

    @Override
    public void cancelLanguageChangeClicked() {
        configView.hideLanguageChangeConfirmation();
        start();
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

    private void loadConfig(Config config, List<Language> languageList) {
        String languageName = config.getLanguageName();

        if (Strings.isNullOrEmpty(languageName) || Strings.isNullOrEmpty(languageName.trim())) {
            languageName = BuildConfig.DEFAULT_LANGUAGE_NAME;
        }

        Language languageToSet = languageRepository.getDefaultLanguage();
        for (Language languageFromList : languageList) {
            if (languageName.equals(languageFromList.getName())) {
                languageToSet = languageFromList;
                break;
            }
        }

        applyConfig(languageToSet);
    }

    private void applyConfig(Language language) {
        configView.setSelectedLanguage(language);
        currentLanguage = language;
    }
}
