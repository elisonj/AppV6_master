package br.com.bg7.appvistoria.login;

import org.junit.Before;
import org.junit.Test;

import br.com.bg7.appvistoria.data.User;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by: elison
 * Date: 2017-07-25
 */
public class LoginPresenterLoginSuccessTest extends LoginPresenterBaseTest {

    @Test
    public void shouldShowMainScreenWhenServicesWorkAndNoUser() {
        setUpNoUser();

        callLogin();

        invokeUserService();
        verify(loginView).showMainScreen();
    }

    @Test
    public void shouldSaveUserAndShowMainScreenWhenServicesWorkAndBadPassword() {
        setUpBadPassword();

        callLogin();

        invokeUserService();
        verify(loginView).showMainScreen();
    }

    @Test
    public void shouldSaveUserAndShowMainScreenWhenServicesWorkAndPasswordOK() {
        setUpGoodPassword();

        callLogin();

        invokeUserService();
        verify(loginView).showMainScreen();
    }
}