package br.com.bg7.appvistoria.login;

import android.support.annotation.NonNull;

import com.google.common.base.Strings;

import org.mindrot.jbcrypt.BCrypt;

import br.com.bg7.appvistoria.data.local.UserRepository;
import br.com.bg7.appvistoria.view.listeners.LoginCallback;
import br.com.bg7.appvistoria.vo.User;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by: luciolucio
 * Date: 2017-07-14
 */

public class LoginPresenter implements LoginContract.Presenter {
    private final LoginService loginService;
    private final LoginContract.View loginView;
    private final UserRepository userRepository;

    public LoginPresenter(@NonNull LoginService loginService, @NonNull UserRepository userRepository, @NonNull LoginContract.View loginView) {
        this.loginService = checkNotNull(loginService, "loginService cannot be null");
        this.userRepository = checkNotNull(userRepository, "userRepository cannot be null");
        this.loginView = checkNotNull(loginView, "loginView cannot be null");

        this.loginView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void login(String username, String password) {
        username = Strings.nullToEmpty(username).trim();
        if (Strings.isNullOrEmpty(username)) {
            loginView.showUsernameEmptyError();
            return;
        }

        password = Strings.nullToEmpty(password).trim();
        if (Strings.isNullOrEmpty(password)) {
            loginView.showPasswordEmptyError();
            return;
        }

        if (loginView.isConnected()) {
            loginService.requestToken(username, password, callback);
            return;
        }

        attemptOfflineLogin(username, password);
    }

    private void attemptOfflineLogin(String username, String password) {
        // TODO: Limpar esta lógica: exibir mensagens melhores para o usuário
        User user = userRepository.findByUsername(username);

        if (user == null) {
            loginView.showUserNotFoundError();
            return;
        }

        if (!checkpw(password, user.getPassword())) {
            loginView.showWrongPasswordError();
            return;
        }

        loginView.showMainScreen();
    }

    protected boolean checkpw(String password, String passwordHash) {
        return BCrypt.checkpw(password, passwordHash);
    }

    private LoginCallback callback = new LoginCallback() {
        // TODO: Tentar o login offline se der erro no online
        @Override
        public void onError() {
            loginView.showCannotLoginError();
        }

        @Override
        public void onTimeout() {
            loginView.showCannotLoginOfflineError();
        }

        @Override
        public void onConnectionFailed() {
            loginView.showCannotLoginOfflineError();
        }

        @Override
        public void onSucess() {
            loginView.showMainScreen();
        }
    };
}
