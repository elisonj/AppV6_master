package br.com.bg7.appvistoria.data.source;

/**
 * Created by: elison
 * Date: 2017-07-13
 */
public interface RequestTokenCallback {
    void onTimeout();
    void onConnectionFailed();
    void onError();
    void onSucess();
}
