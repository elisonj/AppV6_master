package br.com.bg7.appvistoria.login;

import org.junit.Test;

import static org.mockito.Mockito.verify;

/**
 * Created by: luciolucio
 * Date: 2017-07-18
 */

public class LoginPresenterValidationTest extends LoginPresenterBaseTest {
    @Test
    public void shouldShowUserNameErrorWhenUserIsNull() {
        loginPresenter.login(null, "password");
        verify(loginView).showUsernameEmptyWarning();
    }

    @Test
    public void shouldShowUserNameErrorWhenUserIsEmpty() {
        loginPresenter.login("", "password");
        verify(loginView).showUsernameEmptyWarning();
    }

    @Test
    public void shouldShowUserNameErrorWhenUserIsBlank() {
        loginPresenter.login("   ", "password");
        verify(loginView).showUsernameEmptyWarning();
    }

    @Test
    public void shouldShowPasswordErrorWhenPasswordIsNull() {
        loginPresenter.login("user", null);
        verify(loginView).showPasswordEmptyErrorWarning();
    }

    @Test
    public void shouldShowPasswordErrorWhenPasswordIsEmpty() {
        loginPresenter.login("user", "");
        verify(loginView).showPasswordEmptyErrorWarning();
    }

    @Test
    public void shouldShowPasswordErrorWhenPasswordIsBlank() {
        loginPresenter.login("user", "  ");
        verify(loginView).showPasswordEmptyErrorWarning();
    }
}
