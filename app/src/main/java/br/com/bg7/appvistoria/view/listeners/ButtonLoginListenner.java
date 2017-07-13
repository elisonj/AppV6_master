package br.com.bg7.appvistoria.view.listeners;

import android.content.Context;
import android.net.ConnectivityManager;
import android.view.View;

import br.com.bg7.appvistoria.activity.LoginActivity;
import br.com.bg7.appvistoria.view.LoginView;

/**
 * Created by: elison
 * Date: 2017-07-13
 */
public class ButtonLoginListenner implements View.OnClickListener{
    private LoginView view;
    private LoginActivity activity;

    public ButtonLoginListenner(LoginActivity activity, LoginView view) {
        this.activity = activity;
        this.view = view;
    }

    @Override
    public void onClick(View v) {
        if(isConnected()) {
            new ServiceLoginCommand().onClick(view);
        } else {
            new OfflineLoginCommand().onClick(view);
        }
    }

    /**
     *  Verify if exists internet connection
     */
    public  boolean isConnected() {
        boolean conectado;
        ConnectivityManager connectivtyManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivtyManager.getActiveNetworkInfo() != null
                && connectivtyManager.getActiveNetworkInfo().isAvailable()
                && connectivtyManager.getActiveNetworkInfo().isConnected()) {
            conectado = true;
        } else {
            conectado = false;
        }
        return conectado;
    }

}
