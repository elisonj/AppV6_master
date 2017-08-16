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
public interface ConfigContract {
    interface View extends BaseView<ConfigContract.Presenter> {
        void hideButtons();

        void showButtons();

        void setLanguages(List<Language> languageList);

        void setLanguage(String languageName);

        void changeLanguage(String language);

        void showLoginScreen();
    }

    interface Presenter extends BasePresenter {
        void confirmClicked(String language);

        void cancelClicked();

        void logoutClicked();

        void languageSelected();
    }
}
