package br.com.bg7.appvistoria.login.callback;

import org.mindrot.jbcrypt.BCrypt;

import br.com.bg7.appvistoria.data.source.UserService;
import br.com.bg7.appvistoria.data.source.local.UserRepository;
import br.com.bg7.appvistoria.login.LoginContract;
import br.com.bg7.appvistoria.login.vo.LoginData;

/**
 * Created by: luciolucio
 * Date: 2017-07-31
 */

class LoginCallback {
    LoginData loginData;
    UserRepository userRepository;
    LoginContract.View loginView;

    LoginCallback(LoginContract.View loginView, UserRepository userRepository, LoginData loginData) {
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
}
