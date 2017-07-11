package br.com.bg7.appvistoria.controller;

import android.text.TextUtils;
import android.view.View;

import br.com.bg7.appvistoria.R;
import br.com.bg7.appvistoria.activity.LoginActivity;
import br.com.bg7.appvistoria.service.LoginService;
import br.com.bg7.appvistoria.util.Util;
import br.com.bg7.appvistoria.view.LoginView;
import br.com.bg7.appvistoria.view.listeners.ButtonLoginListener;

/**
 * Created by elison on 10/07/17.
 */

public class LoginController {

    private LoginActivity activity;
    private LoginView view;

    public LoginController(LoginActivity loginActivity, LoginView view) {
        this.activity = loginActivity;
        this.view = view;

        configureListenners();
    }

    /**
     * Configure listenners for the view
     */
    private void configureListenners() {
        view.setButtonLoginListener(new ButtonLoginListener(activity, view));
    }

}
