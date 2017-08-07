package br.com.bg7.appvistoria.auth;

import org.mindrot.jbcrypt.BCrypt;

import br.com.bg7.appvistoria.RemoteLocalAuth;
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

    private static AuthFacade authFacade;

    private Auth() { }

    public static synchronized void configure(AuthFacade authFacade) {
        Auth.authFacade = authFacade;
    }

    public static void attempt(String username, String password, boolean connected, AuthCallback callback) {
        authFacade.attempt(username, password, connected, callback);
    }

    public static boolean check() {
        return authFacade.check();
    }

    public static String user() {
        return authFacade.user();
    }

    public static String token() {
        return authFacade.token();
    }

    public static void logout() {
        authFacade.logout();
    }
}
