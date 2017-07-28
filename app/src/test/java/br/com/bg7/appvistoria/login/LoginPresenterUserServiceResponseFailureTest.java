package br.com.bg7.appvistoria.login;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by: elison
 * Date: 2017-07-25
 */
public class LoginPresenterUserServiceResponseFailureTest extends LoginPresenterBaseTest {

    @Before
    public void setUp() {
        super.setUp();
        when(loginView.isConnected()).thenReturn(true);
        when(userHttpResponse.isSuccessful()).thenReturn(false);
    }

    @Test
    public void shouldShowCannotLoginWhenNoUser() {
        setUpNoUser();

        callLogin();

        invokeTokenService();
        invokeUserService();
        verify(loginView).showCannotLoginError();
    }

    @Test
    public void shouldShowMainScreenWhenBadPassword() {
        setUpBadPassword();

        callLogin();

        invokeTokenService();
        invokeUserService();
        verify(loginView).showMainScreen();
    }

    @Test
    public void shouldShowMainScreenWhenGoodPassword() {
        setUpGoodPassword();

        callLogin();

        invokeTokenService();
        invokeUserService();
        verify(loginView).showMainScreen();
    }

    @Test
    public void shouldShowCannotLoginWhenBodyNullAndNoUser() {
        setUpNoUser();
        setUpNullBody();

        callLogin();

        invokeTokenService();
        invokeUserService();
        verify(loginView).showCannotLoginError();
    }

    @Test
    public void shouldShowMainScreenWhenBodyNullAndBadPassword() {
        setUpBadPassword();
        setUpNullBody();

        callLogin();

        invokeTokenService();
        invokeUserService();
        verify(loginView).showMainScreen();
    }

    @Test
    public void shouldShowMainScreenWhenBodyNullAndGoodPassword() {
        setUpGoodPassword();
        setUpNullBody();

        callLogin();

        invokeTokenService();
        invokeUserService();
        verify(loginView).showMainScreen();
    }

    @Test
    public void shouldShowBadCredentialsWhenNoUserAndUserIsUnauthorized() {
        setUpNoUser();
        setUpUserUnauthorizedCode();

        callLogin();

        invokeTokenService();
        invokeUserService();
        verify(loginView).showBadCredentialsError();
    }

    @Test
    public void shouldShowWrongPasswordWhenUserIsUnauthorizedAndBadPassword() {
        setUpBadPassword();
        setUpUserUnauthorizedCode();

        callLogin();

        invokeTokenService();
        invokeUserService();
        verify(loginView).showBadCredentialsError();
    }

    @Test
    public void shouldShowWrongPasswordWhenUserIsUnauthorizedAndGoodPassword() {
        setUpGoodPassword();
        setUpUserUnauthorizedCode();

        callLogin();

        invokeTokenService();
        invokeUserService();
        verify(loginView).showBadCredentialsError();
    }

    private void setUpNullBody() {
        when(userHttpResponse.body()).thenReturn(null);
    }
}
