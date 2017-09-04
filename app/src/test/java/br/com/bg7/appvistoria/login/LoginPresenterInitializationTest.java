package br.com.bg7.appvistoria.login;

import org.junit.Test;

import java.io.IOException;

import br.com.bg7.appvistoria.data.User;
import br.com.bg7.appvistoria.data.source.local.ConfigRepository;
import br.com.bg7.appvistoria.data.source.local.fake.FakeConfigRepository;

import static org.mockito.Mockito.verify;

/**
 * Created by: luciolucio
 * Date: 2017-07-18
 */

public class LoginPresenterInitializationTest extends LoginPresenterTestBase {

    private ConfigRepository configRepository = new FakeConfigRepository();

    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullViewWhenCreated() {
        new LoginPresenter(configRepository, null);
    }

    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullConfigRepositoryWhenCreated() {
        new LoginPresenter(null, loginView);
    }

    @Test
    public void shouldSetPresenterToViewWhenCreated() {
        verify(loginView).setPresenter(loginPresenter);
    }

    @Test
    public void shouldRedirectToMainScreenIfAuthenticated() throws IOException {
        authRepository.save(new User("", "", ""), "");

        LoginPresenter presenter = new LoginPresenter(configRepository, loginView);
        presenter.start();

        verify(loginView).showMainScreen();
    }
}
