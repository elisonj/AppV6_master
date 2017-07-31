package br.com.bg7.appvistoria.login.callback;

import android.support.annotation.NonNull;

import br.com.bg7.appvistoria.data.User;
import br.com.bg7.appvistoria.data.source.UserService;
import br.com.bg7.appvistoria.data.source.local.UserRepository;
import br.com.bg7.appvistoria.data.source.remote.HttpCallback;
import br.com.bg7.appvistoria.data.source.remote.HttpResponse;
import br.com.bg7.appvistoria.data.source.remote.dto.Token;
import br.com.bg7.appvistoria.data.source.remote.dto.UserResponse;
import br.com.bg7.appvistoria.login.LoginContract;
import br.com.bg7.appvistoria.login.LoginPresenter;
import br.com.bg7.appvistoria.login.vo.LoginData;

/**
 * Created by: luciolucio
 * Date: 2017-07-31
 */

public class TokenServiceCallback extends LoginCallback implements HttpCallback<Token> {
    private LoginData loginData;
    private UserService userService;
    private UserRepository userRepository;
    private LoginContract.View loginView;

    public TokenServiceCallback(LoginData loginData, UserService userService, UserRepository userRepository, LoginContract.View loginView) {
        super(loginView);

        this.loginData = loginData;
        this.userService = userService;
        this.userRepository = userRepository;
        this.loginView = loginView;
    }

    @Override
    public void onResponse(HttpResponse<Token> httpResponse) {
        String password = loginData.getPassword();
        User user = loginData.getUser();
        boolean passwordMatches = loginData.passwordMatches();

        if (httpResponse.isSuccessful()) {
            Token token = httpResponse.body();
            if (user == null && token == null) {
                loginView.showCannotLoginError();
                return;
            }
            if (user != null && token == null) {
                verifyPasswordAndEnter(passwordMatches);
                return;
            }

            if (loginView.isConnected()) {
                callUserService(loginData, token);
                return;
            }
            if(user == null) {
                loginView.showCannotLoginError();
                return;
            }

            user = user.withToken(token.getAccessToken());
            user = user.withPasswordHash(hashpw(password));
            userRepository.save(user);
            loginView.showMainScreen();
            return;
        }

        if(httpResponse.code() == LoginPresenter.UNAUTHORIZED_CODE) {
            loginView.showBadCredentialsError();
            return;
        }

        if (user != null) {
            verifyPasswordAndEnter(passwordMatches);
        }
        loginView.showCannotLoginError();
    }

    @Override
    public void onFailure(Throwable t) {
        User user = loginData.getUser();
        boolean passwordMatches = loginData.passwordMatches();

        if(user != null) {
            verifyPasswordAndEnter(passwordMatches);
            return;
        }

        loginView.showCannotLoginError();
    }

    private void callUserService(LoginData loginData, @NonNull final Token token) {
        HttpCallback<UserResponse> callback = new UserServiceCallback(loginData, token, userRepository, loginView);
        userService.getUser(token.getAccessToken(), token.getUserId(), callback);
    }
}
