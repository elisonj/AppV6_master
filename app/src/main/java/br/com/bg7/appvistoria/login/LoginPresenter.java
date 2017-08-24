package br.com.bg7.appvistoria.login;

import com.google.common.base.Strings;

import java.util.List;

import br.com.bg7.appvistoria.BuildConfig;
import br.com.bg7.appvistoria.auth.Auth;
import br.com.bg7.appvistoria.auth.callback.AuthCallback;
import br.com.bg7.appvistoria.config.vo.Language;
import br.com.bg7.appvistoria.data.Config;
import br.com.bg7.appvistoria.data.source.local.ConfigRepository;
import br.com.bg7.appvistoria.data.source.local.LanguageRepository;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by: luciolucio
 * Date: 2017-07-14
 */

public class LoginPresenter implements LoginContract.Presenter {
    public static final int UNAUTHORIZED_CODE = 401;

    private final LoginContract.View loginView;
    private final ConfigRepository configRepository;

    LoginPresenter(ConfigRepository configRepository, LoginContract.View loginView) {
        this.configRepository = checkNotNull(configRepository);
        this.loginView = checkNotNull(loginView, "loginView cannot be null");

        this.loginView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void login(String username, String password) {
        if (!checkInput(username, password)) return;

        Auth.attempt(username, password, loginView.isConnected(), new AuthCallback() {
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
                Config config = configRepository.findByUser(Auth.user());

                if(config == null) {
                    config = new Config(BuildConfig.DEFAULT_LANGUAGE_NAME, Auth.user());
                    configRepository.save(config);
                }

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
