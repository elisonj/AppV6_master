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

    private UserService userService;

    public TokenServiceCallback(LoginData loginData, UserService userService, UserRepository userRepository, LoginContract.View loginView) {
        super(loginView, userRepository, loginData);

        this.userService = userService;
    }

    @Override
    public void onResponse(HttpResponse<Token> httpResponse) {
        if (httpResponse.isSuccessful()) {
            processSuccess(httpResponse.body(), loginData.getLocalUser(), loginData.passwordMatches());
            return;
        }

        if(httpResponse.code() == LoginPresenter.UNAUTHORIZED_CODE) {
            loginView.showBadCredentialsError();
            return;
        }

        onFailure(null);
    }

    private void processSuccess(Token token, User localUser, boolean passwordMatches) {
        if (localUser == null && token == null) {
            loginView.showCannotLoginError();
            return;
        }

        if (localUser != null && token == null) {
            verifyPasswordAndEnter(passwordMatches);
            return;
        }

        if (loginView.isConnected()) {
            callUserService(loginData, token);
            return;
        }

        if(localUser == null) {
            loginView.showCannotLoginError();
            return;
        }

        User user = localUser.withToken(token.getAccessToken());
        user = user.withPasswordHash(hashpw(loginData.getPassword()));
        userRepository.save(user);
        loginView.showMainScreen();
    }

    @Override
    public void onFailure(Throwable t) {
        if(loginData.getLocalUser() != null) {
            verifyPasswordAndEnter(loginData.passwordMatches());
            return;
        }

        loginView.showCannotLoginError();
    }

    private void callUserService(LoginData loginData, @NonNull final Token token) {
        HttpCallback<UserResponse> callback = new UserServiceCallback(loginData, token, userRepository, loginView);
        userService.getUser(token.getAccessToken(), token.getUserId(), callback);
    }
}
