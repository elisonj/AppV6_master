package br.com.bg7.appvistoria.login;

import org.junit.Test;

import static org.mockito.Mockito.verify;

/**
 * Created by: luciolucio
 * Date: 2017-07-18
 */

public class LoginPresenterInitializationTest extends LoginPresenterBaseTest {
    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullServiceWhenCreated() {
        new LoginPresenter(null, userRepository, loginView);
    }

    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullRepositoryWhenCreated() {
        new LoginPresenter(loginService, null, loginView);
    }

    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullViewWhenCreated() {
        new LoginPresenter(loginService, userRepository, null);
    }

    @Test
    public void shouldSetPresenterToViewWhenCreated() {
        verify(loginView).setPresenter(loginPresenter);
    }
}
