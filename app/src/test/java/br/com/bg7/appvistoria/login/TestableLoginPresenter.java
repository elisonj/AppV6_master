package br.com.bg7.appvistoria.login;

import android.support.annotation.NonNull;

import br.com.bg7.appvistoria.data.source.TokenService;
import br.com.bg7.appvistoria.data.source.UserService;
import br.com.bg7.appvistoria.data.source.local.UserRepository;

class TestableLoginPresenter extends LoginPresenter {

    boolean checkpw = true;

    TestableLoginPresenter(
            @NonNull TokenService tokenService,
            @NonNull UserService userService,
            @NonNull UserRepository userRepository,
            @NonNull LoginContract.View loginView) {
        super(tokenService, userService, userRepository, loginView);
    }

    @Override
    String hashpw(String password) {
        return "";
    }

    @Override
    protected boolean checkpw(String password, String passwordHash) {
        return checkpw;
    }
}
