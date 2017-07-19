package br.com.bg7.appvistoria.login;

import android.support.annotation.NonNull;

import br.com.bg7.appvistoria.data.UserRepository;
import br.com.bg7.appvistoria.service.LoginService;

class TestableLoginPresenter extends LoginPresenter {

    boolean checkpw;

    TestableLoginPresenter(@NonNull LoginService loginService, @NonNull UserRepository userRepository, @NonNull LoginContract.View loginView) {
        super(loginService, userRepository, loginView);
    }

    @Override
    protected boolean checkpw(String password, String passwordHash) {
        return checkpw;
    }
}
