package br.com.bg7.appvistoria.login.callback;

import org.mindrot.jbcrypt.BCrypt;

import br.com.bg7.appvistoria.data.source.local.UserRepository;
import br.com.bg7.appvistoria.login.LoginContract;
import br.com.bg7.appvistoria.login.LoginPresenter;
import br.com.bg7.appvistoria.login.vo.LoginData;

/**
 * Created by: luciolucio
 * Date: 2017-07-31
 */

abstract class LoginCallback {
    LoginData loginData;
    UserRepository userRepository;
    LoginContract.View loginView;

    LoginCallback(LoginData loginData, UserRepository userRepository, LoginContract.View loginView) {
        this.loginView = loginView;
        this.userRepository = userRepository;
        this.loginData = loginData;
    }

    void verifyPasswordAndEnter(boolean passwordMatches) {
        if (!passwordMatches) {
            loginView.showBadCredentialsError();
            return;
        }
        loginView.showMainScreen();
    }

    String hashpw(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    void processNonSuccess(int responseCode) {
        if(responseCode == LoginPresenter.UNAUTHORIZED_CODE) {
            loginView.showBadCredentialsError();
            return;
        }

        onFailure(null);
    }

    abstract void onFailure(Throwable t);
}
