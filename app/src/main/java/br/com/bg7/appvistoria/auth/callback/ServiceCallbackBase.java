package br.com.bg7.appvistoria.auth.callback;

import org.mindrot.jbcrypt.BCrypt;

import br.com.bg7.appvistoria.auth.vo.LoginData;
import br.com.bg7.appvistoria.data.source.local.UserRepository;
import br.com.bg7.appvistoria.login.LoginPresenter;

/**
 * Created by: luciolucio
 * Date: 2017-07-31
 */

abstract class ServiceCallbackBase {
    LoginData loginData;
    UserRepository userRepository;
    AuthCallback callback;

    ServiceCallbackBase(LoginData loginData, UserRepository userRepository, AuthCallback callback) {
        this.callback = callback;
        this.userRepository = userRepository;
        this.loginData = loginData;
    }

    void verifyPasswordAndEnter(boolean passwordMatches) {
        if (!passwordMatches) {
            callback.onBadCredentials();
            return;
        }

        callback.onSuccess();
    }

    String hashpw(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    void processNonSuccess(int responseCode) {
        if(responseCode == LoginPresenter.UNAUTHORIZED_CODE) {
            callback.onBadCredentials();
            return;
        }

        onFailure(null);
    }

    abstract void onFailure(Throwable t);
}
