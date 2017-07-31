package br.com.bg7.appvistoria.login.callback;

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

class UserServiceCallback extends LoginCallback implements HttpCallback<UserResponse> {
    private Token token;

    UserServiceCallback(LoginData loginData, Token token, UserRepository userRepository, LoginContract.View loginView) {
        super(loginView, userRepository, loginData);

        this.token = token;
    }

    @Override
    public void onResponse(HttpResponse<UserResponse> httpResponse) {
        if(httpResponse.isSuccessful()) {
            UserResponse userResponse = httpResponse.body();

            if(userResponse != null) {
                if (userRepository.first(User.class) == null) {
                    userRepository.deleteAll(User.class);
                }

                User user = new User(loginData.getUsername(), token.getAccessToken(), hashpw(loginData.getPassword()));
                saveUserAndEnter(user);
                return;
            }
        }

        if(httpResponse.code() == LoginPresenter.UNAUTHORIZED_CODE) {
            loginView.showBadCredentialsError();
            return;
        }

        onFailure(null);
    }

    @Override
    public void onFailure(Throwable t) {
        if(loginData.getLocalUser() != null) {
            User user = loginData.getLocalUser().withToken(token.getAccessToken());
            if (!loginData.passwordMatches()) {
                user = user.withPasswordHash(hashpw(loginData.getPassword()));
            }
            saveUserAndEnter(user);
            return;
        }

        loginView.showCannotLoginError();
    }

    private void saveUserAndEnter(User user) {
        userRepository.save(user);
        loginView.showMainScreen();
    }
}
