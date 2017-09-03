package br.com.bg7.appvistoria.login;

import android.os.Bundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.bg7.appvistoria.BaseActivity;
import br.com.bg7.appvistoria.BuildConfig;
import br.com.bg7.appvistoria.auth.Auth;
import br.com.bg7.appvistoria.auth.RemoteLocalAuth;
import br.com.bg7.appvistoria.data.servicelocator.ServiceLocator;

/**
 * Created by: elison
 * Date: 2017-07-10
 */

public class LoginActivity extends BaseActivity {

    private static final Logger LOG = LoggerFactory.getLogger(LoginActivity.class);

    private LoginPresenter loginPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ServiceLocator services = ServiceLocator.create(BuildConfig.BUILD_TYPE, this, this);
        LoginView loginView = new LoginView(this);

        RemoteLocalAuth remoteLocalAuth = new RemoteLocalAuth(
                services.getUserService(),
                services.getTokenService(),
                services.getUserRepository(),
                services.getAuthRepository()
        );
        Auth.configure(remoteLocalAuth);

        loginPresenter = new LoginPresenter(services.getConfigRepository(), loginView);

        setContentView(loginView);
    }

    @Override
    public void onResume() {
        super.onResume();
        loginPresenter.start();
    }
}
