package br.com.bg7.appvistoria.auth;

import java.io.IOException;

import br.com.bg7.appvistoria.data.Config;
import br.com.bg7.appvistoria.data.User;
import br.com.bg7.appvistoria.data.source.local.fake.FakeConfigRepository;

/**
 * Created by: elison
 * Date: 2017-08-23
 */
public class AuthTest {


    private static final User USER = new User("username", "token", "pwd");

    private FakeConfigRepository configRepository;
    private FakeAuthFacade authFacade;

    public AuthTest(FakeConfigRepository configRepository) throws IOException {
        this.configRepository = configRepository;

        authFacade = new FakeAuthFacade();
        Auth.configure(authFacade);
        authFacade.fakeLogin(USER);
    }

    public void setUpConfig(String language) {
        Config config = new Config(language, USER);
        configRepository.save(config);
    }

    public FakeAuthFacade getAuthFacade() {
        return authFacade;
    }
}
