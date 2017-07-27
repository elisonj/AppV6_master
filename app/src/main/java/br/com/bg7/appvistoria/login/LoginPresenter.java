package br.com.bg7.appvistoria.login;

import android.support.annotation.NonNull;

import com.google.common.base.Strings;

import org.mindrot.jbcrypt.BCrypt;

import java.net.ConnectException;
import java.util.concurrent.TimeoutException;

import br.com.bg7.appvistoria.data.User;
import br.com.bg7.appvistoria.data.source.TokenService;
import br.com.bg7.appvistoria.data.source.UserService;
import br.com.bg7.appvistoria.data.source.local.UserRepository;
import br.com.bg7.appvistoria.data.source.remote.HttpCallback;
import br.com.bg7.appvistoria.data.source.remote.HttpResponse;
import br.com.bg7.appvistoria.data.source.remote.dto.Token;
import br.com.bg7.appvistoria.data.source.remote.dto.UserResponse;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by: luciolucio
 * Date: 2017-07-14
 */

class LoginPresenter implements LoginContract.Presenter {
    static final int UNAUTHORIZED_CODE = 401;

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
            loginView.showBadCredentialsError();
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
                verifyPasswordAndEnter(password);
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

            user = user.withToken(token.getAccessToken());
            saveUserAndEnter(user);
            return;
        }

        if(httpResponse.code() == UNAUTHORIZED_CODE) {
            loginView.showBadCredentialsError();
            return;
        }

        if (user != null) {
            verifyPasswordAndEnter(password);
        }
        loginView.showCannotLoginError();
    }

    private void verifyPasswordAndEnter(String password) {
        if (!checkpw(password, user.getPasswordHash())) {
            loginView.showBadCredentialsError();
            return;
        }
        loginView.showMainScreen();
    }

    private void onGetTokenFailure(String password, Throwable t) {
        if (t instanceof TimeoutException) {
            if(user == null) {
                loginView.showCannotLoginError();
                return;
            }
            verifyPasswordAndEnter(password);
            return;
        }

        if (t instanceof ConnectException) {
            loginView.showCannotLoginError();
            return;
        }

        if(user != null) {
            verifyPasswordAndEnter(password);
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
                onGetUserFailure(password, token);
            }
        });
    }

    private void onGetUserResponse(HttpResponse<UserResponse> httpResponse, String username, Token token, String password) {
        if(httpResponse.isSuccessful()) {
            UserResponse userResponse = httpResponse.body();

            if(userResponse != null) {
                User userFromRepository = userRepository.findByUsername(username);
                if(userFromRepository == null) {
                    User user = new User(username, token.getAccessToken(), hashpw(password));
                    saveUserAndEnter(user);
                    return;
                }

                loginView.showMainScreen();
                return;
            }
        }

        if(httpResponse.code() == UNAUTHORIZED_CODE) {
            loginView.showBadCredentialsError();
            return;
        }

        if(user != null) {
            user = user.withToken(token.getAccessToken());
            if (!checkpw(password, user.getPasswordHash())) {
                user = user.withPasswordHash(hashpw(password));
            }
            saveUserAndEnter(user);
            return;
        }

        loginView.showCannotLoginError();

    }

    private void saveUserAndEnter(User user) {
        try{
            userRepository.save(user);
            loginView.showMainScreen();
        } catch (Exception ex) {
            loginView.showCriticalError();
        }
    }

    private void onGetUserFailure(final String password, @NonNull final Token token) {
        if(user != null) {
            user = user.withToken(token.getAccessToken());
            if (!checkpw(password, user.getPasswordHash())) {
                user = user.withPasswordHash(hashpw(password));
            }
            saveUserAndEnter(user);
            return;
        }

        loginView.showCannotLoginError();
    }

    boolean checkpw(String password, String passwordHash) {
        return BCrypt.checkpw(password, passwordHash);
    }

    String hashpw(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}
