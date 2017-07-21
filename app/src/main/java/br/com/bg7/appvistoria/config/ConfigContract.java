package br.com.bg7.appvistoria.config;

import java.util.List;

import br.com.bg7.appvistoria.BasePresenter;
import br.com.bg7.appvistoria.BaseView;
import br.com.bg7.appvistoria.vo.Country;

/**
 * Created by: luciolucio
 * Date: 2017-07-17
 */

public interface ConfigContract {
    interface View extends BaseView<ConfigContract.Presenter> {
        void hideButtons();

        void toggleLanguagesVisibility();

        void showButtons();

        List<Country> initCountryList();

        void setCountries(List<Country> countryList);

        void setLanguage(int id);

        void setSyncWithWifiOnly(boolean syncWithWifiOnly);

        void toggleSyncWithWifiOnly();

        void hideLanguages();

        void changeLanguage(String language);

        void refresh();
    }

    interface Presenter extends BasePresenter {
        void languagesLabelClicked();

        void syncWithWifiOnlyClicked();

        void syncLabelClicked();

        void confirmClicked(String languageId, String language, boolean syncWithWifiOnly);

        void cancelClicked();
    }
}
