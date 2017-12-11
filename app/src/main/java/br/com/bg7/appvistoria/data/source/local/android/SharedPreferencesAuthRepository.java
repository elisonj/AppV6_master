package br.com.bg7.appvistoria.data.source.local.android;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.IOException;

import br.com.bg7.appvistoria.data.User;
import br.com.bg7.appvistoria.data.source.local.AuthRepository;

/**
 * Created by: elison
 * Date: 2017-08-07
 */

public class SharedPreferencesAuthRepository implements AuthRepository {

    private final static String AUTH_PREFS_NAME = "AuthPrefs";
    private final static String CURRENT_USER = "currentUser";
    private final static String CURRENT_TOKEN = "currentToken";

    private Context context;

    public SharedPreferencesAuthRepository(Context context) {
        this.context = context;
    }

    @Override
    public User currentUser() {
        return User.deserialize(getValue(CURRENT_USER));
    }

    @Override
    public String currentToken() {
        return getValue(CURRENT_TOKEN);
    }

    @Override
    public void save(User user, String token) {
        SharedPreferences.Editor editor = getEditor();

        editor.putString(CURRENT_USER, user.serialize());
        editor.putString(CURRENT_TOKEN, token);
        editor.apply();
    }

    @Override
    public void clear() {
        SharedPreferences.Editor editor = getEditor();

        editor.remove(CURRENT_USER);
        editor.remove(CURRENT_TOKEN);
        editor.apply();
    }

    private String getValue(String key) {
        SharedPreferences settings = getSettings();
        return settings.getString(key, null);
    }

    private SharedPreferences.Editor getEditor() {
        SharedPreferences settings = getSettings();
        return settings.edit();
    }

    private SharedPreferences getSettings() {
        return context.getSharedPreferences(AUTH_PREFS_NAME, Context.MODE_PRIVATE);
    }
}
