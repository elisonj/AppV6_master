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

    @Before
    public void setUp() {
        super.setUp();
        when(loginView.isConnected()).thenReturn(true);
    }

    @Test
    public void shouldShowMainScreenWhenServicesWorkAndNoUser() {
        setUpNoUser();
        when(userHttpResponse.isSuccessful()).thenReturn(true);
        setUpUserResponse();

        callLogin();

        invokeTokenService();
        invokeUserService();
        verify(loginView).showMainScreen();
    }

    @Test
    public void shouldSaveUserAndShowMainScreenWhenServicesWorkAndBadPassword() {
        setUpBadPassword();
        when(userHttpResponse.isSuccessful()).thenReturn(false);
        setUpUserResponse();

        callLogin();

        invokeTokenService();
        invokeUserService();
        verify(loginView).showMainScreen();
    }

    @Test
    public void shouldSaveUserAndShowMainScreenWhenServicesWorkAndPasswordOK() {
        when(userRepository.findByUsername(anyString())).thenReturn(new User());
        loginPresenter.checkpw = true;
        when(userHttpResponse.isSuccessful()).thenReturn(false);
        setUpUserResponse();

        callLogin();

        invokeTokenService();
        invokeUserService();
        verify(loginView).showMainScreen();
    }
}