package br.com.bg7.appvistoria.login;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by elison on 10/07/17.
 */

public class LoginActivity extends Activity {

    private LoginView view;
    private LoginController controller;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = new LoginView(LoginActivity.this);
        controller = new LoginController(LoginActivity.this, view);

        setContentView(view);

    }

}
