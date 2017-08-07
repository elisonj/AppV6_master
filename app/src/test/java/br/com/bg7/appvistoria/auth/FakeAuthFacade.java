package br.com.bg7.appvistoria.auth;

import br.com.bg7.appvistoria.auth.callback.AuthCallback;
import br.com.bg7.appvistoria.data.source.local.fake.FakeAuthRepository;

/**
 * Created by: luciolucio
 * Date: 2017-08-07
 */

public class FakeAuthFacade implements AuthFacade {
    private FakeAuthRepository authRepository = new FakeAuthRepository();

    @Override
    public void attempt(String username, String password, boolean connected, AuthCallback callback) {

    }

    public void fakeLogin(String username) {
        authRepository.save(username, "");
    }

    @Override
    public boolean check() {
        return authRepository.currentUsername() != null;
    }

    @Override
    public String user() {
        return authRepository.currentUsername();
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
