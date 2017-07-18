package br.com.bg7.appvistoria.login;

import android.support.annotation.NonNull;

import com.google.common.base.Strings;

import org.mindrot.jbcrypt.BCrypt;

import java.net.ConnectException;
import java.util.concurrent.TimeoutException;

import br.com.bg7.appvistoria.data.UserRepository;
import br.com.bg7.appvistoria.service.LoginService;
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
            loginService.requestToken(callback, username, password);
            return;
        }

        attemptOfflineLogin(username, password);
    }

    private void attemptOfflineLogin(String username, String password) {
        // TODO: Limpar esta lógica: tirar os else
        // TODO: Limpar esta lógica: exibir mensagens melhores para o usuário
        User user = userRepository.findByUsername(username);

        if(user != null) {
            String passwordHash = user.getPassword();
            if (BCrypt.checkpw(password, passwordHash)) {
                loginView.showOfflineLoginSuccess();
            } else {
                loginView.showWrongPasswordError();
            }
        } else {
            loginView.showUserNotFoundError();
        }
    }

    private LoginCallback callback = new LoginCallback() {
        // TODO: Tentar o login offline se der erro no online
        @Override
        public void onFailure(Throwable t) {
            if(t instanceof TimeoutException || t instanceof ConnectException) {
                loginView.showCannotLoginOfflineError();
                return;
            }

            loginView.showCannotLoginError();
        }

        @Override
        public void onSucess() {
            loginView.showMainScreen();
        }
    };
}
