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
import br.com.bg7.appvistoria.vo.User;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by: luciolucio
 * Date: 2017-07-14
 */

public class LoginPresenter implements LoginContract.Presenter {
    private final TokenService tokenService;
    private final UserService userService;
    private final LoginContract.View loginView;
    private final UserRepository userRepository;

    public LoginPresenter(
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
            // TODO: Tentar o login offline se der erro no online
            tokenService.getToken(username, password, new TokenCallback(username, password));
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

    boolean checkpw(String password, String passwordHash) {
        return BCrypt.checkpw(password, passwordHash);
    }

    private class TokenCallback implements HttpCallback<Token> {

        private String username;
        private String password;

        TokenCallback(String username, String password) {
            this.username = username;
            this.password = password;
        }

        @Override
        public void onResponse(HttpResponse<Token> httpResponse) {
            if (httpResponse.isSuccessful()) {
                Token token = httpResponse.body();
                if (token != null) {
                    userService.getUser("Bearer " + token.getAccessToken(), token.getUserId(), new UserCallback(username, password, token));
                }
            } else {
                loginView.showCannotLoginError();
            }
        }

        @Override
        public void onFailure(Throwable t) {
            if (t instanceof TimeoutException) {
                loginView.showCannotLoginOfflineError();
                return;
            }

            if (t instanceof ConnectException) {
                loginView.showCannotLoginOfflineError();
                return;
            }

            loginView.showCannotLoginError();
        }
    }

    private class UserCallback implements HttpCallback<UserResponse> {

        private String username;
        private String password;
        private Token token;

        UserCallback(String username, String password, Token token) {
            this.username = username;
            this.password = password;
            this.token = token;
        }

        @Override
        public void onResponse(HttpResponse<UserResponse> httpResponse) {
            if(httpResponse.isSuccessful()) {
                UserResponse userResponse = httpResponse.body();

                if(userResponse != null) {
                    User userFromRepository = userRepository.findByUsername(username);
                    if(userFromRepository == null) {
                        User user = new User(userResponse, token, password);
                        userRepository.save(user);
                    }

                    loginView.showMainScreen();
                }
            }
        }

        @Override
        public void onFailure(Throwable t) {
            loginView.showCannotLoginError();
        }
    }
}
