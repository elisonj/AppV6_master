package br.com.bg7.appvistoria.login;

import android.support.annotation.NonNull;

import com.google.common.base.Strings;

import org.mindrot.jbcrypt.BCrypt;

import br.com.bg7.appvistoria.data.User;
import br.com.bg7.appvistoria.data.source.TokenService;
import br.com.bg7.appvistoria.data.source.UserService;
import br.com.bg7.appvistoria.data.source.local.UserRepository;
import br.com.bg7.appvistoria.login.callback.TokenServiceCallback;
import br.com.bg7.appvistoria.login.vo.LoginData;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by: luciolucio
 * Date: 2017-07-14
 */

public class LoginPresenter implements LoginContract.Presenter {
    public static final int UNAUTHORIZED_CODE = 401;

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
            attemptTokenLogin(new LoginData(username, password, user, passwordMatches));
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

    private void attemptTokenLogin(final LoginData loginData) {
        TokenServiceCallback callback = new TokenServiceCallback(loginData, userService, userRepository, loginView);
        tokenService.getToken(loginData.getUsername(), loginData.getPassword(), callback);
    }
}
