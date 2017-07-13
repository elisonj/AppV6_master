package br.com.bg7.appvistoria.view.listeners;

import android.content.Context;
import android.net.ConnectivityManager;
import android.view.View;

import java.net.ConnectException;
import java.util.concurrent.TimeoutException;

import br.com.bg7.appvistoria.Applic;
import br.com.bg7.appvistoria.R;
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
            new ServiceLoginCommand().onClick(callback, view);
        } else {
            new OfflineLoginCommand().onClick(view);
        }
    }

    /**
     *  Verify if exists internet connection
     */
    public  boolean isConnected() {
        boolean connected;
        ConnectivityManager connectivtyManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivtyManager.getActiveNetworkInfo() != null
                && connectivtyManager.getActiveNetworkInfo().isAvailable()
                && connectivtyManager.getActiveNetworkInfo().isConnected()) {
            connected = true;
        } else {
            connected = false;
        }
        return connected;
    }


    /**
     * Callback used to handle service responses
     */
    private LoginCallback callback = new LoginCallback() {
        @Override
        public void onFailure(Throwable t) {
            if(t instanceof TimeoutException || t instanceof ConnectException) {
                new OfflineLoginCommand().onClick(view);
            }
        }

        @Override
        public void onSucess() {
            view.showDialog(Applic.getInstance().getString(R.string.success),
                    Applic.getInstance().getString(R.string.login_success));
        }
    };
}
