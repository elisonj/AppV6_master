package br.com.bg7.appvistoria;

import org.junit.Before;

import java.io.IOException;

import br.com.bg7.appvistoria.auth.Auth;
import br.com.bg7.appvistoria.auth.FakeAuthFacade;
import br.com.bg7.appvistoria.data.Config;
import br.com.bg7.appvistoria.data.User;
import br.com.bg7.appvistoria.data.source.local.fake.FakeConfigRepository;

/**
 * Created by: luciolucio
 * Date: 2017-08-23
 */

public abstract class UserLoggedInTest {
    private static final User USER = new User("username", "token", "pwd");

    protected FakeConfigRepository configRepository = new FakeConfigRepository();

    @Before
    public void setUp() throws IOException {
        FakeAuthFacade authFacade = new FakeAuthFacade();
        Auth.configure(authFacade);

        authFacade.fakeLogin(USER);
        setUpConfig("pt");
    }

    protected void setUpConfig(String language) {
        Config config = new Config(language, USER);
        configRepository.save(config);
    }
}
