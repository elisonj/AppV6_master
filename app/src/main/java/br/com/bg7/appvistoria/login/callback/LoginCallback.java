package br.com.bg7.appvistoria.login.callback;

import org.mindrot.jbcrypt.BCrypt;

import br.com.bg7.appvistoria.login.LoginContract;

/**
 * Created by: luciolucio
 * Date: 2017-07-31
 */

class LoginCallback {
    private LoginContract.View loginView;

    public LoginCallback(LoginContract.View loginView) {
        this.loginView = loginView;
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
}
