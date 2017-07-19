package br.com.bg7.appvistoria.view.listeners;

/**
 * Created by: elison
 * Date: 2017-07-13
 */
public interface LoginCallback {
    void onTimeout();
    void onConnectionFailed();
    void onError();
    void onSucess();
}
