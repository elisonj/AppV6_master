package br.com.bg7.appvistoria.login;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import org.mindrot.jbcrypt.BCrypt;

import java.net.ConnectException;
import java.util.List;
import java.util.concurrent.TimeoutException;

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

    public LoginPresenter(@NonNull LoginService loginService, @NonNull LoginContract.View loginView) {
        this.loginService = checkNotNull(loginService, "loginService cannot be null");
        this.loginView = checkNotNull(loginView, "loginView cannot be null");

        this.loginView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void login(String username, String password) {
        if (TextUtils.isEmpty(username)) {
            loginView.showUsernameEmptyError();
            return;
        }

        if (TextUtils.isEmpty(password)) {
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
        List<User> user = User.find(User.class, "user_name = ?", username);
        if(user != null && user.size() > 0) {
            String passwordHash = user.get(0).getPassword();
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
