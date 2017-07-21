package br.com.bg7.appvistoria.config;

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
import java.util.ServiceConfigurationError;

import br.com.bg7.appvistoria.Constants;
import br.com.bg7.appvistoria.R;
import br.com.bg7.appvistoria.config.vo.Language;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by: elison
 * Date: 2017-07-13
 *
 * Fragment class for Config menu item
 */
public class ConfigFragment extends Fragment implements ConfigContract.View {

    private ConfigContract.Presenter configPresenter;

    private LinearLayout languagesContainer;
    private LinearLayout languagesLabel;
    private Spinner languageList;

    private LinearLayout syncLabel;
    private CheckBox syncWithWifiOnly;

    private LinearLayout buttonsContainer;
    private Button cancelButton;
    private Button confirmButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_config, container, false);

        initializeViewElements(root);
        initializeListeners();

        return root;
    }

    private void initializeViewElements(View root) {
        languagesContainer = root.findViewById(R.id.linear_language);
        languagesLabel = root.findViewById(R.id.linear_language_top);
        languageList = root.findViewById(R.id.spinner_language);

        syncLabel = root.findViewById(R.id.linear_wifi);
        syncWithWifiOnly = root.findViewById(R.id.checkBox_wifi);

        buttonsContainer = root.findViewById(R.id.linear_buttons);
        cancelButton = root.findViewById(R.id.button_cancel);
        confirmButton = root.findViewById(R.id.button_confirm);
    }

    private void initializeListeners() {
        languagesLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                configPresenter.languagesLabelClicked();
            }
        });

        syncLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                configPresenter.syncLabelClicked();
            }
        });

        syncWithWifiOnly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                configPresenter.syncWithWifiOnlyClicked();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                configPresenter.cancelClicked();
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Language selected = (Language) languageList.getSelectedItem();

                configPresenter.confirmClicked(selected.getLocale(), syncWithWifiOnly.isChecked());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        configPresenter.start();
    }

    @Override
    public List<Language> initLanguageList() {
        String locales[] = getResources().getStringArray(R.array.languageLocales);
        String displayNames[] = getResources().getStringArray(R.array.languageDisplayNames);

        if (locales.length != displayNames.length) {
            throw new ServiceConfigurationError("Locale list and language display name list do not match in size");
        }

        ArrayList<Language> languageList = new ArrayList<>();
        for (int i = 0; i < locales.length; i++) {
            languageList.add(new Language(locales[i], displayNames[i]));
        }

        return languageList;
    }

    @Override
    public void setPresenter(ConfigContract.Presenter presenter) {
        configPresenter = checkNotNull(presenter);
    }

    @Override
    public void hideButtons() {
        buttonsContainer.setVisibility(View.GONE);
    }

    @Override
    public void showButtons() {
        buttonsContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void setLanguage(int id) {
        languageList.setSelection(id);
    }

    @Override
    public void setSyncWithWifiOnly(boolean syncWithWifiOnly) {
        this.syncWithWifiOnly.setChecked(syncWithWifiOnly);
    }

    @Override
    public void toggleLanguagesVisibility() {
        toggleVisibility(languagesContainer);
    }

    @Override
    public void toggleSyncWithWifiOnly() {
        syncWithWifiOnly.setChecked(!syncWithWifiOnly.isChecked());
    }

    @Override
    public void hideLanguages() {
        languagesContainer.setVisibility(View.GONE);
    }

    @Override
    public void setLanguages(List<Language> languageList) {
        ArrayAdapter<Language> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, languageList);
        this.languageList.setAdapter(adapter);
    }

    @Override
    public void changeLanguage(String locale) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constants.PREFERENCE_LOCALE_KEY, locale);
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
