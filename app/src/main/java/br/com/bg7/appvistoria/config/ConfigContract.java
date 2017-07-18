package br.com.bg7.appvistoria.config;

import br.com.bg7.appvistoria.BasePresenter;
import br.com.bg7.appvistoria.BaseView;

/**
 * Created by: luciolucio
 * Date: 2017-07-17
 */

public interface ConfigContract {
    interface View extends BaseView<ConfigContract.Presenter> {
        void hideButtons();

        void toggleLanguagesVisibility();

        void showButtons();

        void toggleSyncWithWifiOnly();

        void hideLanguages();

        void changeLanguage();

        void refresh();
    }

    interface Presenter extends BasePresenter {
        void topLanguagesClicked();

        void syncWithWifiOnlyClicked();

        void confirmClicked(String languageId, boolean syncWithWifiOnly);

        void cancelClicked();
    }
}
