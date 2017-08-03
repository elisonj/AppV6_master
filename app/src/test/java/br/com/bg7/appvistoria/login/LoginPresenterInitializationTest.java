package br.com.bg7.appvistoria.login;

import org.junit.Test;

import static org.mockito.Mockito.verify;

/**
 * Created by: luciolucio
 * Date: 2017-07-18
 */

public class LoginPresenterInitializationTest extends LoginPresenterTestBase {
    @SuppressWarnings("ConstantConditions")
    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullTokenServiceWhenCreated() {
        new LoginPresenter(null, userService, userRepository, loginView);
    }

    @SuppressWarnings("ConstantConditions")
    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullUserServiceWhenCreated() {
        new LoginPresenter(tokenService, null, userRepository, loginView);
    }

    @SuppressWarnings("ConstantConditions")
    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullRepositoryWhenCreated() {
        new LoginPresenter(tokenService, userService, null, loginView);
    }

    @SuppressWarnings("ConstantConditions")
    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullViewWhenCreated() {
        new LoginPresenter(tokenService, userService, userRepository, null);
    }

    @Test
    public void shouldSetPresenterToViewWhenCreated() {
        verify(loginView).setPresenter(loginPresenter);
    }
}
