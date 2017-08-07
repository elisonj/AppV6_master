package br.com.bg7.appvistoria.data.source.local.android;

import android.content.Context;
import android.content.SharedPreferences;

import br.com.bg7.appvistoria.data.source.local.AuthRepository;

/**
 * Created by: luciolucio
 * Date: 2017-08-07
 */

public class SharedPreferencesAuthRepository implements AuthRepository {
    private final static String AUTH_PREFS_NAME = "AuthPrefs";
    private final static String CURRENT_USER_NAME = "currentUsername";

    private Context context;

    public SharedPreferencesAuthRepository(Context context) {
        this.context = context;
    }

    @Override
    public String currentUsername() {
        SharedPreferences settings = context.getSharedPreferences(AUTH_PREFS_NAME, Context.MODE_PRIVATE);
        return settings.getString(CURRENT_USER_NAME, null);
    }
}
