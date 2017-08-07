package br.com.bg7.appvistoria.login;

import com.google.common.base.Strings;

import br.com.bg7.appvistoria.auth.Auth;
import br.com.bg7.appvistoria.auth.callback.AuthCallback;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by: luciolucio
 * Date: 2017-07-14
 */

public class LoginPresenter implements LoginContract.Presenter {
    public static final int UNAUTHORIZED_CODE = 401;

    private final Auth auth;
    private final LoginContract.View loginView;

    LoginPresenter(Auth auth, LoginContract.View loginView) {

        this.auth = checkNotNull(auth, "auth cannot be null");
        this.loginView = checkNotNull(loginView, "loginView cannot be null");

        this.loginView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void login(String username, String password) {
        if (!checkInput(username, password)) return;

        auth.attempt(username, password, loginView.isConnected(), new AuthCallback() {
            @Override
            public void onCannotLogin() {
                loginView.showCannotLoginError();
            }

            @Override
            public void onBadCredentials() {
                loginView.showBadCredentialsError();
            }

            @Override
            public void onSuccess() {
                loginView.showMainScreen();
            }
        });
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
}
