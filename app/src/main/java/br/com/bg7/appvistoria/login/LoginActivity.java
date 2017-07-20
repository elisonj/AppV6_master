package br.com.bg7.appvistoria.login;

import android.os.Bundle;
import android.support.annotation.NonNull;

import br.com.bg7.appvistoria.BaseActivity;
import br.com.bg7.appvistoria.R;
import br.com.bg7.appvistoria.data.source.TokenService;
import br.com.bg7.appvistoria.data.source.UserService;
import br.com.bg7.appvistoria.data.source.local.UserRepository;
import br.com.bg7.appvistoria.data.source.remote.retrofit.RetrofitTokenService;
import br.com.bg7.appvistoria.data.source.remote.retrofit.RetrofitUserService;

/**
 * Created by: elison
 * Date: 2017-07-10
 */

public class LoginActivity extends BaseActivity {

    private LoginPresenter loginPresenter;
    private final String baseUrl = getResources().getString(R.string.base_url);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LoginView loginView = new LoginView(this);
        UserRepository userRepository = new UserRepository();

        loginPresenter = new LoginPresenter(getTokenService(), getUserService(), userRepository, loginView);

        setContentView(loginView);
    }

    private UserService getUserService() {
        return new RetrofitUserService(baseUrl);
    }

    @NonNull
    private TokenService getTokenService() {
        String grantType = getResources().getString(R.string.grant_type);
        String clientId = getResources().getString(R.string.client_id);

        return new RetrofitTokenService(baseUrl, grantType, clientId);
    }

    @Override
    public void onResume() {
        super.onResume();
        loginPresenter.start();
    }
}
