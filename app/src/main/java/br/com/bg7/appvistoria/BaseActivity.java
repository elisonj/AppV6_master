package br.com.bg7.appvistoria;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.akexorcist.localizationactivity.LocalizationActivity;

/**
 * Created by: elison
 * Date: 2017-07-17
 */
public abstract class BaseActivity extends LocalizationActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        String defaultLanguage = preferences.getString(
                Constants.PREFERENCE_LANGUAGE_KEY,
                Constants.DEFAULT_LANGUAGE);
        setLanguage(defaultLanguage);
    }
}
