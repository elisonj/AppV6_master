package br.com.bg7.appvistoria.auth.callback;

import android.support.annotation.NonNull;

import br.com.bg7.appvistoria.auth.vo.LoginData;
import br.com.bg7.appvistoria.data.User;
import br.com.bg7.appvistoria.data.source.local.UserRepository;
import br.com.bg7.appvistoria.data.source.remote.UserService;
import br.com.bg7.appvistoria.data.source.remote.dto.Token;
import br.com.bg7.appvistoria.data.source.remote.http.HttpCallback;
import br.com.bg7.appvistoria.data.source.remote.http.HttpResponse;

/**
 * Created by: elison
 * Date: 2017-07-31
 */

public class TokenServiceCallback extends ServiceCallbackBase implements HttpCallback<Token> {

    private UserService userService;

    public TokenServiceCallback(LoginData loginData, UserService userService, UserRepository userRepository, AuthCallback callback) {
        super(loginData, userRepository, callback);

        this.userService = userService;
    }

    @Override
    public void onResponse(HttpResponse<Token> httpResponse) {
        if (httpResponse.isSuccessful()) {
            processSuccess(httpResponse.body(), loginData.getLocalUser(), loginData.passwordMatches());
            return;
        }

        processNonSuccess(httpResponse.code());
    }

    @Override
    public void onFailure(Throwable t) {
        if(loginData.getLocalUser() != null) {
            verifyPasswordAndEnter(loginData.passwordMatches());
            return;
        }

        callback.onCannotLogin();
    }

    private void processSuccess(Token token, User localUser, boolean passwordMatches) {
        if (token == null && localUser == null) {
            callback.onCannotLogin();
            return;
        }

        if (token == null) {
            verifyPasswordAndEnter(passwordMatches);
            return;
        }

        callUserService(loginData, token);
    }

    private void callUserService(LoginData loginData, @NonNull final Token token) {
        UserServiceCallback httpCallback = new UserServiceCallback(loginData, token, userRepository, callback);
        userService.getUser(token.getAccessToken(), token.getUserId(), httpCallback);
    }
}
