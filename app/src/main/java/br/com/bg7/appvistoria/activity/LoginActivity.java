package br.com.bg7.appvistoria.activity;

import android.app.Activity;
import android.os.Bundle;

import br.com.bg7.appvistoria.controller.LoginController;
import br.com.bg7.appvistoria.view.LoginView;

/**
 * Created by: elison
 * Date: 2017-07-10
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
