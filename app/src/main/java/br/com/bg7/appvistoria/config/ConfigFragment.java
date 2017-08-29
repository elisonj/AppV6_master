package br.com.bg7.appvistoria.config;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.List;

import br.com.bg7.appvistoria.ConfirmDialog;
import br.com.bg7.appvistoria.Constants;
import br.com.bg7.appvistoria.R;
import br.com.bg7.appvistoria.config.vo.Language;
import br.com.bg7.appvistoria.login.LoginActivity;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by: elison
 * Date: 2017-07-13
 *
 * Fragment class for Config menu item
 */
public class ConfigFragment extends Fragment implements ConfigContract.View {

    private ConfigContract.Presenter configPresenter;

    private LinearLayout logout;
    private Spinner languageSpinner;
    private List<Language> languageList;

    private ConfirmDialog logoutDialog = new ConfirmDialog(getActivity(), getString(R.string.confirm_logout));
    private ConfirmDialog languageChangeDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_config, container, false);

        initializeViewElements(root);
        initializeListeners();

        return root;
    }

    private void initializeViewElements(View root) {
        languageSpinner = root.findViewById(R.id.spinner_language);
        logout = root.findViewById(R.id.linear_logout);
    }

    private void initializeListeners() {
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                configPresenter.logoutClicked();
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
    public void setSelectedLanguage(String languageName) {
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
    public void setLanguageList(final List<Language> languageList) {
        ArrayAdapter<Language> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, languageList);
        languageSpinner.setAdapter(adapter);

        languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                configPresenter.languageChangeClicked(languageList.get(i).getName());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

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

    @Override
    public void showLogoutConfirmation() {
        View.OnClickListener logoutConfirmListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                configPresenter.confirmLogoutClicked();
            }
        };

        View.OnClickListener logoutCancelListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                configPresenter.cancelLogoutClicked();
            }
        };

        logoutDialog.show(logoutConfirmListener, logoutCancelListener);
    }

    @Override
    public void hideLogoutConfirmation() {
        logoutDialog.hide();
    }

    @Override
    public void showLanguageChangeConfirmation(final String languageName) {
        String messageFormat = getString(R.string.confirm_change_language_format);
        String message = String.format(messageFormat, languageName);

        languageChangeDialog = new ConfirmDialog(getActivity(), message);

        View.OnClickListener languageConfirmListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                configPresenter.confirmLanguageChangeClicked(languageName);
            }
        };

        View.OnClickListener languageCancelListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                configPresenter.cancelLanguageChangeClicked();
            }
        };

        languageChangeDialog.show(languageConfirmListener, languageCancelListener);
    }

    @Override
    public void hideLanguageChangeConfirmation() {
        languageChangeDialog.hide();
    }

    @Override
    public void showLoginScreen() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        getActivity().finish();
    }
}
