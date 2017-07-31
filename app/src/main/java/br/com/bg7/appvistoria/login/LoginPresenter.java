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
 *
 * TODO: Refactoring final do LoginPresenter
 */

class LoginPresenter implements LoginContract.Presenter {
    static final int UNAUTHORIZED_CODE = 401;

    private final TokenService tokenService;
    private final UserService userService;
    private final LoginContract.View loginView;
    private final UserRepository userRepository;

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
    public void login(String username, String password) {
        if (!checkInput(username, password)) return;

        User user = userRepository.findByUsername(username);
        boolean passwordMatches = user != null && BCrypt.checkpw(password, user.getPasswordHash());

        if (loginView.isConnected()) {
            attemptTokenLogin(username, password, user, passwordMatches);
            return;
        }

        if (user == null) {
            loginView.showCannotLoginError();
            return;
        }

        if (!passwordMatches) {
            loginView.showBadCredentialsError();
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
            return false;
        }

        if (Strings.isNullOrEmpty(password) || Strings.isNullOrEmpty(password.trim())) {
            loginView.showPasswordEmptyError();
            return false;
        }

        return true;
    }

    private void attemptTokenLogin(final String username, final String password, final User user, final boolean passwordMatches) {
        tokenService.getToken(username, password, new HttpCallback<Token>() {
            @Override
            public void onResponse(HttpResponse<Token> httpResponse) {
                onGetTokenResponse(username, password, httpResponse, user, passwordMatches);
            }

            @Override
            public void onFailure(Throwable t) {
                onGetTokenFailure(user, passwordMatches, t);
            }
        });
    }

    private void onGetTokenResponse(String username, String password, HttpResponse<Token> httpResponse, User user, boolean passwordMatches) {
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
                callUserService(username, password, token, user, passwordMatches);
                return;
            }
            if(user == null) {
                loginView.showCannotLoginError();
                return;
            }

            user = user.withToken(token.getAccessToken());
            user = user.withPasswordHash(hashpw(password));
            saveUserAndEnter(user);
            return;
        }

        if(httpResponse.code() == UNAUTHORIZED_CODE) {
            loginView.showBadCredentialsError();
            return;
        }

        if (user != null) {
            verifyPasswordAndEnter(passwordMatches);
        }
        loginView.showCannotLoginError();
    }

    private void verifyPasswordAndEnter(boolean passwordMatches) {
        if (!passwordMatches) {
            loginView.showBadCredentialsError();
            return;
        }
        loginView.showMainScreen();
    }

    private void onGetTokenFailure(User user, boolean passwordMatches, Throwable t) {
        if(user != null) {
            verifyPasswordAndEnter(passwordMatches);
            return;
        }

        loginView.showCannotLoginError();
    }

    private void callUserService(final String username, final String password, @NonNull final Token token, final User user, final boolean passwordMatches) {
        userService.getUser(token.getAccessToken(), token.getUserId(), new HttpCallback<UserResponse>() {
            @Override
            public void onResponse(HttpResponse<UserResponse> httpResponse) {
                onGetUserResponse(httpResponse, username, token, password, user, passwordMatches);
            }

            @Override
            public void onFailure(Throwable t) {
                onGetUserFailure(password, token, user, passwordMatches);
            }
        });
    }

    private void onGetUserResponse(HttpResponse<UserResponse> httpResponse, String username, Token token, String password, User user, boolean passwordMatches) {
        if(httpResponse.isSuccessful()) {
            UserResponse userResponse = httpResponse.body();

            if(userResponse != null) {
                if (userRepository.first(User.class) == null) {
                    userRepository.deleteAll(User.class);
                }

                user = new User(username, token.getAccessToken(), hashpw(password));
                saveUserAndEnter(user);
                return;
            }
        }

        if(httpResponse.code() == UNAUTHORIZED_CODE) {
            loginView.showBadCredentialsError();
            return;
        }

        if(user != null) {
            user = user.withToken(token.getAccessToken());
            if (!passwordMatches) {
                user = user.withPasswordHash(hashpw(password));
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

    private void onGetUserFailure(String password, @NonNull Token token, User user, boolean passwordMatches) {
        if(user != null) {
            user = user.withToken(token.getAccessToken());
            if (!passwordMatches) {
                user = user.withPasswordHash(hashpw(password));
            }
            saveUserAndEnter(user);
            return;
        }

        loginView.showCannotLoginError();
    }

    private String hashpw(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}
