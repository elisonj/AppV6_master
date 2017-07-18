package br.com.bg7.appvistoria;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by: elison
 * Date: 2017-07-17
 */
public class BaseActivity extends AppCompatActivity {
    @Override
    protected void attachBaseContext(Context newBase) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(newBase);
        String defaultLanguage = preferences.getString("Language", "pt");

        super.attachBaseContext(LanguageContextWrap.wrap(newBase, defaultLanguage));
    }
}
