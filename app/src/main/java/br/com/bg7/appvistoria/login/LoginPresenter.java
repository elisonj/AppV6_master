package br.com.bg7.appvistoria.login;

import android.support.annotation.NonNull;

import com.google.common.base.Strings;

import org.mindrot.jbcrypt.BCrypt;

import java.net.ConnectException;
import java.util.concurrent.TimeoutException;

import br.com.bg7.appvistoria.data.source.TokenService;
import br.com.bg7.appvistoria.data.source.UserService;
import br.com.bg7.appvistoria.data.source.local.UserRepository;
import br.com.bg7.appvistoria.data.source.remote.HttpCallback;
import br.com.bg7.appvistoria.data.source.remote.HttpResponse;
import br.com.bg7.appvistoria.data.source.remote.dto.Token;
import br.com.bg7.appvistoria.data.source.remote.dto.UserResponse;
import br.com.bg7.appvistoria.data.User;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by: luciolucio
 * Date: 2017-07-14
 */

class LoginPresenter implements LoginContract.Presenter {
    final int UNAUTHORIZED_CODE = 401;

    private final TokenService tokenService;
    private final UserService userService;
    private final LoginContract.View loginView;
    private final UserRepository userRepository;
    private User user = null;

    LoginPresenter(
            @NonNull TokenService tokenService,
            @NonNull UserService userService,
            @NonNull UserRepository userRepository,
            @NonNull LoginContract.View loginView) {
        this.tokenService = checkNotNull(tokenService, "tokenService cannot be null");
        this.userService = checkNotNull(userService, "userService cannot be null");
        this.userRepository = checkNotNull(userRepository, "userRepository cannot be null");
        this.loginView = checkNotNull(loginView, "loginView cannot be null");

        this.loginView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void login(final String username, final String password) {
        if (checkInput(username, password)) return;

        user = userRepository.findByUsername(username);

        if (user == null) {
            if (loginView.isConnected()) {
                attemptTokenLogin(username, password);
                return;
            }

            loginView.showCannotLoginError();
            return;
        }

        if (!checkpw(password, user.getPasswordHash())) {
            if (loginView.isConnected()) {
                attemptTokenLogin(username, password);
                return;
            }
            loginView.showWrongPasswordError();
            return;
        }

        if (loginView.isConnected()) {
            attemptTokenLogin(username, password);
            return;
        }

        loginView.showMainScreen();
    }

    /**
     * Checks the login information of the user and tells the view what to do
     * @param username Username
     * @param password Password
     * @return true if the input is valid, false if it's not. Validity means that username
     * and password are both not null and not empty
     */
    private boolean checkInput(String username, String password) {
        if (Strings.isNullOrEmpty(username) || Strings.isNullOrEmpty(username.trim())) {
            loginView.showUsernameEmptyError();
            return true;
        }

        if (Strings.isNullOrEmpty(password) || Strings.isNullOrEmpty(password.trim())) {
            loginView.showPasswordEmptyError();
            return true;
        }

        return false;
    }

    private void attemptTokenLogin(final String username, final String password) {
        tokenService.getToken(username, password, new HttpCallback<Token>() {
            @Override
            public void onResponse(HttpResponse<Token> httpResponse) {
                onGetTokenResponse(username, password, httpResponse);
            }

            @Override
            public void onFailure(Throwable t) {
                onGetTokenFailure(password, t);
            }
        });
    }

    private void onGetTokenResponse(String username, String password, HttpResponse<Token> httpResponse) {
        if (httpResponse.isSuccessful()) {
            Token token = httpResponse.body();
            if (user == null && token == null) {
                loginView.showCannotLoginError();
                return;
            }
            if (user != null && token == null) {
                if (!checkpw(password, user.getPasswordHash())) {
                    loginView.showWrongPasswordError();
                    return;
                }
                loginView.showMainScreen();
                return;
            }

            if (loginView.isConnected()) {
                callUserService(username, password, token);
                return;
            }
            if(user == null) {
                loginView.showCannotLoginError();
                return;
            }

            user = user.clone(token.getAccessToken());
            userRepository.save(user);
            loginView.showMainScreen();
            return;
        }

        if(httpResponse.code() == UNAUTHORIZED_CODE) {
            loginView.showWrongPasswordError();
            return;
        }

        if (user != null) {
            if (!checkpw(password, user.getPasswordHash())) {
                loginView.showWrongPasswordError();
                return;
            }
            loginView.showMainScreen();
            return;
        }
        loginView.showCannotLoginError();
    }

    private void onGetTokenFailure(String password, Throwable t) {
        if (t instanceof TimeoutException) {
            if(user == null) {
                loginView.showCannotLoginOfflineError();
                return;
            }
            if (!checkpw(password, user.getPasswordHash())) {
                loginView.showWrongPasswordError();
                return;
            }
            loginView.showMainScreen();
            return;
        }

        if (t instanceof ConnectException) {
            loginView.showCannotLoginOfflineError();
            return;
        }

        if(user != null) {
            if (!checkpw(password, user.getPasswordHash())) {
                loginView.showWrongPasswordError();
                return;
            }
            loginView.showMainScreen();
            return;
        }

        loginView.showCannotLoginError();
    }

    private void callUserService(final String username, final String password, @NonNull final Token token) {
        userService.getUser(token.getAccessToken(), token.getUserId(), new HttpCallback<UserResponse>() {
            @Override
            public void onResponse(HttpResponse<UserResponse> httpResponse) {
                onGetUserResponse(httpResponse, username, token, password);
            }

            @Override
            public void onFailure(Throwable t) {
                onGetUserFailure();
            }
        });
    }

    private void onGetUserResponse(HttpResponse<UserResponse> httpResponse, String username, Token token, String password) {
        if(httpResponse.isSuccessful()) {
            UserResponse userResponse = httpResponse.body();

            if(userResponse != null) {
                User userFromRepository = userRepository.findByUsername(username);
                if(userFromRepository == null) {
                    String passwordHash = hashpw(password);
                    User user = new User(username, token.getAccessToken(), passwordHash);
                    userRepository.save(user);
                }

                loginView.showMainScreen();
            }
        }
    }

    private void onGetUserFailure() {
        loginView.showCannotLoginError();
    }

    boolean checkpw(String password, String passwordHash) {
        return BCrypt.checkpw(password, passwordHash);
    }

    String hashpw(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}
