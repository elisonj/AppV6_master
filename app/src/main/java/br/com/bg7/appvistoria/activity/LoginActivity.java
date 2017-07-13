package br.com.bg7.appvistoria.activity;

import android.app.Activity;
import android.os.Bundle;

import br.com.bg7.appvistoria.view.LoginView;
import br.com.bg7.appvistoria.view.listeners.ButtonLoginListenner;

/**
 * Created by: elison
 * Date: 2017-07-10
 */

public class LoginActivity extends Activity {

    private LoginView view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = new LoginView(LoginActivity.this);

        configureListeners();

        setContentView(view);
    }

    private void configureListeners() {
        view.setButtonLoginListener(new ButtonLoginListenner(this, view));
    }
}
