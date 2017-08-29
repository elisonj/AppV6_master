package br.com.bg7.appvistoria.config;

import java.util.List;

import br.com.bg7.appvistoria.BasePresenter;
import br.com.bg7.appvistoria.BaseView;
import br.com.bg7.appvistoria.config.vo.Language;

/**
 * Created by: luciolucio
 * Date: 2017-07-17
 *
 * {@link SuppressWarnings pois so as sub-classes sao implementadas, de proposito}
 */
@SuppressWarnings("unused")
interface ConfigContract {
    interface View extends BaseView<ConfigContract.Presenter> {
        void setLanguageList(List<Language> languageList);

        void setSelectedLanguage(String languageName);

        void changeLanguage(String language);

        void showLogoutConfirmation();

        void hideLogoutConfirmation();

        void showLanguageChangeConfirmation(String languageName);

        void hideLanguageChangeConfirmation();

        void showLoginScreen();
    }

    interface Presenter extends BasePresenter {
        void languageChangeClicked(String language);

        void confirmLanguageChangeClicked(String language);

        void cancelLanguageChangeClicked();

        void logoutClicked();

        void confirmLogoutClicked();

        void cancelLogoutClicked();
    }
}
