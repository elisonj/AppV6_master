package br.com.bg7.appvistoria.auth;

import br.com.bg7.appvistoria.auth.callback.AuthCallback;
import br.com.bg7.appvistoria.data.User;

/**
 * Created by: luciolucio
 * Date: 2017-08-07
 */

public interface AuthFacade {
    void attempt(String username, String password, boolean connected, AuthCallback callback);
    boolean check();
    User user();
    String token();
    void logout();
}
