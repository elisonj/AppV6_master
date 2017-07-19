package br.com.bg7.appvistoria.login;

import android.os.Bundle;
import android.support.annotation.NonNull;

import br.com.bg7.appvistoria.BaseActivity;
import br.com.bg7.appvistoria.R;
import br.com.bg7.appvistoria.data.source.local.UserRepository;
import br.com.bg7.appvistoria.data.source.remote.retrofit.RetrofitTokenService;

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
        UserRepository userRepository = new UserRepository();

        loginPresenter = new LoginPresenter(getLoginService(userRepository), userRepository, loginView);

        setContentView(loginView);
    }

    @NonNull
    private LoginService getLoginService(UserRepository userRepository) {
        String baseUrl = getResources().getString(R.string.base_url);
        String grantType = getResources().getString(R.string.grant_type);
        String clientId = getResources().getString(R.string.client_id);

        RetrofitTokenService tokenService = new RetrofitTokenService(baseUrl, grantType, clientId);

        return new LoginService(tokenService, userRepository);
    }

    @Override
    public void onResume() {
        super.onResume();
        loginPresenter.start();
    }
}
