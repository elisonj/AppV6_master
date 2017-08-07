package br.com.bg7.appvistoria.auth;

import org.mindrot.jbcrypt.BCrypt;

import br.com.bg7.appvistoria.auth.callback.AuthCallback;
import br.com.bg7.appvistoria.auth.callback.TokenServiceCallback;
import br.com.bg7.appvistoria.auth.vo.LoginData;
import br.com.bg7.appvistoria.data.User;
import br.com.bg7.appvistoria.data.source.local.AuthRepository;
import br.com.bg7.appvistoria.data.source.local.UserRepository;
import br.com.bg7.appvistoria.data.source.remote.TokenService;
import br.com.bg7.appvistoria.data.source.remote.UserService;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Created by: luciolucio
 * Date: 2017-08-07
 */

public class Auth {
    private final UserService userService;
    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final AuthRepository authRepository;

    public Auth(UserService userService, TokenService tokenService, UserRepository userRepository, AuthRepository authRepository) {
        this.userService = checkNotNull(userService);
        this.tokenService = checkNotNull(tokenService);
        this.userRepository = checkNotNull(userRepository);
        this.authRepository = checkNotNull(authRepository);
    }

    public void attempt(String username, String password, boolean connected, AuthCallback callback) {
        username = checkNotNull(username);
        password = checkNotNull(password);
        callback = checkNotNull(callback);

        User localUser = userRepository.findByUsername(username);
        boolean passwordMatches = localUser != null && BCrypt.checkpw(password, localUser.getPasswordHash());

        Interceptor interceptor = new Interceptor(username, callback);

        if (connected) {
            attemptTokenLogin(new LoginData(username, password, localUser, passwordMatches), interceptor);
            return;
        }

        if (localUser == null) {
            interceptor.onCannotLogin();
            return;
        }

        if (!passwordMatches) {
            interceptor.onBadCredentials();
            return;
        }

        interceptor.onSuccess();
    }

    private void attemptTokenLogin(LoginData loginData, AuthCallback callback) {
        TokenServiceCallback tokenServiceCallback = new TokenServiceCallback(loginData, userService, userRepository, callback);
        tokenService.getToken(loginData.getUsername(), loginData.getPassword(), tokenServiceCallback);
    }

    public boolean check() {
        return authRepository.currentUsername() != null;
    }

    public String user() {
        return authRepository.currentUsername();
    }

    public void logout() {
        authRepository.clear();
    }

    /**
     * Intercepts callbacks in order to save auth info on success
     */
    private class Interceptor implements AuthCallback {

        String username;
        AuthCallback callback;

        Interceptor(String username, AuthCallback callback) {
            this.username = username;
            this.callback = callback;
        }

        @Override
        public void onCannotLogin() {
            callback.onCannotLogin();
        }

        @Override
        public void onBadCredentials() {
            callback.onBadCredentials();
        }

        @Override
        public void onSuccess() {
            authRepository.save(username);
            callback.onSuccess();
        }
    }
}
