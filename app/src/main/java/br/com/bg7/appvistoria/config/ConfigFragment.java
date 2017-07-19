package br.com.bg7.appvistoria.config;

/**
 * Created by: elison
 * Date: 2017-07-13
 */

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import br.com.bg7.appvistoria.Constants;
import br.com.bg7.appvistoria.R;
import br.com.bg7.appvistoria.vo.Country;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Fragment class for Config menu item
 */
public class ConfigFragment extends Fragment implements ConfigContract.View {

    ConfigContract.Presenter configPresenter;

    private LinearLayout topLanguages;
    private LinearLayout synchronize;
    private LinearLayout languages;
    private Spinner languageSelected;
    private LinearLayout buttons;
    private CheckBox syncWithWifiOnly;
    private Button cancel;
    private Button confirm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_config, container, false);

        synchronize = root.findViewById(R.id.linear_wifi);
        languages = root.findViewById(R.id.linear_language);
        topLanguages = root.findViewById(R.id.linear_language_top);
        buttons = root.findViewById(R.id.linear_buttons);
        syncWithWifiOnly = root.findViewById(R.id.checkBox_wifi);
        cancel = root.findViewById(R.id.button_cancel);
        confirm = root.findViewById(R.id.button_confirm);
        languageSelected = root.findViewById(R.id.spinner_language);

        topLanguages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                configPresenter.topLanguagesClicked();
            }
        });
        synchronize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                configPresenter.syncWithWifiOnlyLineClicked();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Country selected = (Country) languageSelected.getSelectedItem();

                configPresenter.confirmClicked(selected.getId(), selected.getLanguage(), syncWithWifiOnly.isChecked());
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                configPresenter.cancelClicked();
            }
        });

        syncWithWifiOnly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                configPresenter.syncWithWifiOnlyClicked();
            }
        });

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        configPresenter.start();
    }

    @Override
    public List<Country> initCountryList() {
        String languageIds [] = getContext().getResources().getStringArray(R.array.languageIds);
        String languageNames [] = getContext().getResources().getStringArray(R.array.languageNames);
        String languages [] = getContext().getResources().getStringArray(R.array.languages);
        String abbreviations [] = getContext().getResources().getStringArray(R.array.abbreviations);

        ArrayList<Country> countryList = new ArrayList<>();
        for(int i=0;i<languageIds.length;i++) {
            countryList.add(new Country(languageIds[i], languageNames[i],languages[i], abbreviations[i]));
        }
        return countryList;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void setPresenter(ConfigContract.Presenter presenter) {
        configPresenter = checkNotNull(presenter);
    }

    @Override
    public void hideButtons() {
        buttons.setVisibility(View.GONE);
    }

    @Override
    public void showButtons() {
        buttons.setVisibility(View.VISIBLE);
    }

    @Override
    public void setLanguage(int id) {
        languageSelected.setSelection(id);
    }

    @Override
    public void setSyncWithWifiOnly(boolean syncWithWifiOnly) {
        this.syncWithWifiOnly.setChecked(syncWithWifiOnly);
    }

    @Override
    public void toggleLanguagesVisibility() {
        toggleVisibility(languages);
    }

    @Override
    public void toggleSyncWithWifiOnly() {
        syncWithWifiOnly.setChecked(!syncWithWifiOnly.isChecked());
    }

    @Override
    public void hideLanguages() {
        languages.setVisibility(View.GONE);
    }


    @Override
    public void setCountries(List<Country> countryList) {
        ArrayAdapter<Country> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, countryList);
        languageSelected.setAdapter(adapter);
    }

    @Override
    public void changeLanguage(String language) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constants.PREFERENCE_LANGUAGE_KEY, language);
        editor.apply();
    }

    @Override
    public void refresh() {
        getActivity().recreate();
    }

    private void toggleVisibility(View view) {
        if (view.isShown()) {
            view.setVisibility(View.GONE);
            return;
        }

        view.setVisibility(View.VISIBLE);
    }
}