package br.com.bg7.appvistoria.auth;

import br.com.bg7.appvistoria.auth.callback.AuthCallback;
import br.com.bg7.appvistoria.data.User;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by: elison
 * Date: 2017-08-07
 */

public class Auth {

    private static AuthFacade authFacade;

    private Auth() { }

    public static synchronized void configure(AuthFacade authFacade) {
        Auth.authFacade = checkNotNull(authFacade);
    }

    public static void attempt(String username, String password, boolean connected, AuthCallback callback) {
        authFacade.attempt(username, password, connected, callback);
    }

    public static boolean check() {
        return authFacade.check();
    }

    public static User user() {
        return authFacade.user();
    }

    public static String token() {
        return authFacade.token();
    }

    public static void logout() {
        authFacade.logout();
    }
}
