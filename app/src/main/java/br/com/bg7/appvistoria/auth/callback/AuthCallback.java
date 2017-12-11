package br.com.bg7.appvistoria.auth.callback;

/**
 * Created by: elison
 * Date: 2017-08-07
 */

public interface AuthCallback {
    void onCannotLogin();
    void onBadCredentials();
    void onSuccess();
}
