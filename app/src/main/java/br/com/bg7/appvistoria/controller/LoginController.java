package br.com.bg7.appvistoria.controller;

import android.view.View;

import br.com.bg7.appvistoria.activity.LoginActivity;
import br.com.bg7.appvistoria.service.LoginService;
import br.com.bg7.appvistoria.util.Util;
import br.com.bg7.appvistoria.view.LoginView;

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
        view.getButtonLogin().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateFields()) {
                    LoginService service = new LoginService();
                    service.requestToken(view.getEditTextUser().getText().toString(), view.getEditTextUser().getText().toString());
                }
            }
        });
    }

    /**
     * Validate Login fields
     * @return
     */
    private boolean validateFields() {
        boolean valid = false;

        if(view.getEditTextUser().getText() != null && view.getEditTextUser().getText().toString().length() > 0) {
            if(view.getEditTextPassword().getText() != null && view.getEditTextPassword().getText().toString().length() > 0) {
                valid = true;
            } else { // message error password
                Util.showDialog(activity, "Atenção", "Preencha o campo Usuário para continuar.");
            }
        } else { // message error user
            Util.showDialog(activity, "Atenção", "Preencha o campo Senha para continuar.");
        }
        return valid;
    }
}
