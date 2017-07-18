package br.com.bg7.appvistoria;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.akexorcist.localizationactivity.LocalizationActivity;

/**
 * Created by: elison
 * Date: 2017-07-17
 */
public class BaseActivity extends LocalizationActivity {

    public void changeLanguage() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String defaultLanguage = preferences.getString("Language", "pt");
        setLanguage(defaultLanguage);
    }
}
