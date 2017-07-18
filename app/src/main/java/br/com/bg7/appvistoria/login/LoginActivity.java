package br.com.bg7.appvistoria.login;

import android.os.Bundle;

import br.com.bg7.appvistoria.BaseActivity;
import br.com.bg7.appvistoria.service.LoginService;

/**
 * Created by: elison
 * Date: 2017-07-10
 */

public class LoginActivity extends BaseActivity {

    private LoginPresenter loginPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LoginView loginView = new LoginView(this);
        loginPresenter = new LoginPresenter(new LoginService(), loginView);

        setContentView(loginView);
    }

    @Override
    public void onResume() {
        super.onResume();
        loginPresenter.start();
    }
}
