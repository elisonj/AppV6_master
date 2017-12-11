package br.com.bg7.appvistoria.auth;

import java.io.IOException;

import br.com.bg7.appvistoria.auth.callback.AuthCallback;
import br.com.bg7.appvistoria.data.User;
import br.com.bg7.appvistoria.data.source.local.fake.FakeAuthRepository;

/**
 * Created by: elison
 * Date: 2017-08-07
 */

public class FakeAuthFacade implements AuthFacade {
    private FakeAuthRepository authRepository = new FakeAuthRepository();

    @Override
    public void attempt(String username, String password, boolean connected, AuthCallback callback) {

    }

    public void fakeLogin(User user) throws IOException {
        authRepository.save(user, "");
    }

    @Override
    public boolean check() {
        return authRepository.currentUser() != null;
    }

    @Override
    public User user() {
        return authRepository.currentUser();
    }

    @Override
    public String token() {
        return authRepository.currentToken();
    }

    @Override
    public void logout() {
        authRepository.clear();
    }
}
