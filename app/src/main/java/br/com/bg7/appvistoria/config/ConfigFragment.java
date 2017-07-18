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

import br.com.bg7.appvistoria.R;
import br.com.bg7.appvistoria.vo.Config;
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
    private CheckBox wifi;
    private Button cancel;
    private Button confirm;

    private static final String ARG_TEXT_KEY = "ARG_TEXT_KEY";
    private String text;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_config, container, false);

        synchronize = root.findViewById(R.id.linear_wifi);
        languages = root.findViewById(R.id.linear_language);
        topLanguages = root.findViewById(R.id.linear_language_top);
        buttons = root.findViewById(R.id.linear_buttons);
        wifi = root.findViewById(R.id.checkBox_wifi);
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
                configPresenter.syncWithWifiOnlyClicked();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Country selected = (Country) languageSelected.getSelectedItem();

                configPresenter.confirmClicked(selected.getId(), wifi.isChecked());
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                configPresenter.cancelClicked();
            }
        });

        ArrayList<Country> countryList = new ArrayList<>();

        countryList.add(new Country("1", getContext().getString(R.string.portuguese_br), "pt", "BR"));
        countryList.add(new Country("2", getContext().getString(R.string.english), "en", "US"));

        ArrayAdapter<Country> adapter = new ArrayAdapter<Country>(getContext(), android.R.layout.simple_spinner_dropdown_item, countryList);
        languageSelected.setAdapter(adapter);

        List<Config> list = Config.listAll(Config.class);
        if(list != null && list.size() > 0) {
            int selected = 0;
            for (int i=0; i < countryList.size(); i++) {
                Country country = countryList.get(i);
                if(country.getId().equals(list.get(0).getActualLanguage())) {
                    selected = i;
                }
            }
                languageSelected.setSelection(selected);
        }

        wifi.setChecked(list.get(0).isUpdateOnlyWifi());

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        configPresenter.start();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(ARG_TEXT_KEY, text);
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
    public void toggleLanguagesVisibility() {
        toggleVisibility(languages);
    }

    @Override
    public void toggleSyncWithWifiOnly() {
        buttons.setVisibility(View.VISIBLE);
        wifi.setChecked(!wifi.isChecked());
    }

    @Override
    public void hideLanguages() {
        languages.setVisibility(View.GONE);
    }

    @Override
    public void changeLanguage() {
        Country country = (Country) languageSelected.getSelectedItem();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Language", country.getLanguage());
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