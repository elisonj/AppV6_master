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
        verify(loginView).showUsernameEmptyError();
    }

    @Test
    public void shouldShowUserNameErrorWhenUserIsEmpty() {
        loginPresenter.login("", "password");
        verify(loginView).showUsernameEmptyError();
    }

    @Test
    public void shouldShowUserNameErrorWhenUserIsBlank() {
        loginPresenter.login("   ", "password");
        verify(loginView).showUsernameEmptyError();
    }

    @Test
    public void shouldShowPasswordErrorWhenPasswordIsNull() {
        loginPresenter.login("user", null);
        verify(loginView).showPasswordEmptyError();
    }

    @Test
    public void shouldShowPasswordErrorWhenPasswordIsEmpty() {
        loginPresenter.login("user", "");
        verify(loginView).showPasswordEmptyError();
    }

    @Test
    public void shouldShowPasswordErrorWhenPasswordIsBlank() {
        loginPresenter.login("user", "  ");
        verify(loginView).showPasswordEmptyError();
    }
}
