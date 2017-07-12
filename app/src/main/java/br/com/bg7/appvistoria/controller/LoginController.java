package br.com.bg7.appvistoria.controller;

import br.com.bg7.appvistoria.activity.LoginActivity;
import br.com.bg7.appvistoria.view.LoginView;
import br.com.bg7.appvistoria.view.listeners.ButtonLoginListener;

/**
 * Created by: elison
 * Date: 2017-07-10
 */

public class LoginController {

    private LoginActivity activity;
    private LoginView view;

    public LoginController(LoginActivity loginActivity, LoginView view) {
        this.activity = loginActivity;
        this.view = view;

        configureListeners();
    }

    /**
     * Configure listeners for the view
     */
    private void configureListeners() {
        view.setButtonLoginListener(new ButtonLoginListener(activity, view));
    }

}
