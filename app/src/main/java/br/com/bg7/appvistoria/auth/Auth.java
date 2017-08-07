package br.com.bg7.appvistoria.auth;

import org.mindrot.jbcrypt.BCrypt;

import br.com.bg7.appvistoria.auth.callback.AuthCallback;
import br.com.bg7.appvistoria.auth.callback.TokenServiceCallback;
import br.com.bg7.appvistoria.auth.vo.LoginData;
import br.com.bg7.appvistoria.data.User;
import br.com.bg7.appvistoria.data.source.local.UserRepository;
import br.com.bg7.appvistoria.data.source.remote.TokenService;
import br.com.bg7.appvistoria.data.source.remote.UserService;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Created by: luciolucio
 * Date: 2017-08-07
 */

public class Auth {
    private UserService userService;
    private TokenService tokenService;
    private UserRepository userRepository;

    public Auth(UserService userService, TokenService tokenService, UserRepository userRepository) {
        this.userService = checkNotNull(userService);
        this.tokenService = checkNotNull(tokenService);
        this.userRepository = checkNotNull(userRepository);
    }

    public void attempt(String username, String password, boolean connected, AuthCallback callback) {
        User localUser = userRepository.findByUsername(username);
        boolean passwordMatches = localUser != null && BCrypt.checkpw(password, localUser.getPasswordHash());

        if (connected) {
            attemptTokenLogin(new LoginData(username, password, localUser, passwordMatches), callback);
            return;
        }

        if (localUser == null) {
            callback.onCannotLogin();
            return;
        }

        if (!passwordMatches) {
            callback.onBadCredentials();
            return;
        }

        callback.onSuccess();
    }

    private void attemptTokenLogin(LoginData loginData, AuthCallback callback) {
        TokenServiceCallback tokenServiceCallback = new TokenServiceCallback(loginData, userService, userRepository, callback);
        tokenService.getToken(loginData.getUsername(), loginData.getPassword(), tokenServiceCallback);
    }
}
