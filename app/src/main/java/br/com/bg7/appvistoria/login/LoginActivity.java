package br.com.bg7.appvistoria.login;

import android.os.Bundle;
import android.support.annotation.NonNull;

import br.com.bg7.appvistoria.Applic;
import br.com.bg7.appvistoria.BaseActivity;
import br.com.bg7.appvistoria.R;
import br.com.bg7.appvistoria.data.UserRepository;
import br.com.bg7.appvistoria.service.LoginService;
import br.com.bg7.appvistoria.ws.RetrofitClient;
import br.com.bg7.appvistoria.ws.TokenService;

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

        loginPresenter = new LoginPresenter(getLoginService(), new UserRepository(), loginView);

        setContentView(loginView);
    }

    @NonNull
    private LoginService getLoginService() {
        TokenService tokenService = RetrofitClient.getClient(getResources().getString(R.string.base_url)).
                create(TokenService.class);

        String grantType = getResources().getString(R.string.grant_type);
        String clientId = getResources().getString(R.string.client_id);

        return new LoginService(tokenService, grantType, clientId);
    }

    @Override
    public void onResume() {
        super.onResume();
        loginPresenter.start();
    }
}
