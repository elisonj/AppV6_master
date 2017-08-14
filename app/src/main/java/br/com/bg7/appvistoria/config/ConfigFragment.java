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
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.List;

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
    private LinearLayout logout;
    private Spinner languageSpinner;
    private List<Language> languageList;

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
        languageSpinner = root.findViewById(R.id.spinner_language);
        logout = root.findViewById(R.id.linear_logout);

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

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                configPresenter.logoutClicked();
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
                Language selectedLanguage = (Language) languageSpinner.getSelectedItem();

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        configPresenter.start();
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
    public void setLanguage(String languageName) {
        int selected = 0;
        for (int i = 0; i < languageList.size(); i++) {
            Language languageFromList = languageList.get(i);
            if (languageFromList.getName().equals(languageName)) {
                selected = i;
            }
        }

        languageSpinner.setSelection(selected);
    }

    @Override
    public void toggleLanguagesVisibility() {
        toggleVisibility(languagesContainer);
    }

    @Override
    public void hideLanguages() {
        languagesContainer.setVisibility(View.GONE);
    }

    @Override
    public void setLanguages(List<Language> languageList) {
        ArrayAdapter<Language> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, languageList);
        this.languageSpinner.setAdapter(adapter);
        this.languageList = languageList;
    }

    @Override
    public void changeLanguage(String language) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constants.PREFERENCE_LANGUAGE_KEY, language);
        editor.apply();

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
