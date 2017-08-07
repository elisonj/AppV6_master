package br.com.bg7.appvistoria.login;

import android.os.Bundle;

import br.com.bg7.appvistoria.BaseActivity;
import br.com.bg7.appvistoria.BuildConfig;
import br.com.bg7.appvistoria.data.source.remote.TokenService;
import br.com.bg7.appvistoria.data.source.remote.UserService;
import br.com.bg7.appvistoria.data.source.local.sugar.SugarUserRepository;
import br.com.bg7.appvistoria.data.source.remote.retrofit.RetrofitTokenService;
import br.com.bg7.appvistoria.data.source.remote.retrofit.RetrofitUserService;

/**
 * Created by: elison
 * Date: 2017-07-10
 */

public class LoginActivity extends BaseActivity {

    private LoginPresenter loginPresenter;
    private static final String BASE_URL = BuildConfig.BASE_URL;
    private static final String GRANT_TYPE = BuildConfig.GRANT_TYPE;
    private static final String CLIENT_ID = BuildConfig.CLIENT_ID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LoginView loginView = new LoginView(this);
        UserService userService = new RetrofitUserService(BASE_URL);
        TokenService tokenService = new RetrofitTokenService(BASE_URL, GRANT_TYPE, CLIENT_ID);
        SugarUserRepository userRepository = new SugarUserRepository();

        loginPresenter = new LoginPresenter(tokenService, userService, userRepository, loginView);

        setContentView(loginView);
    }

    @Override
    public void onResume() {
        super.onResume();
        loginPresenter.start();
    }
}
