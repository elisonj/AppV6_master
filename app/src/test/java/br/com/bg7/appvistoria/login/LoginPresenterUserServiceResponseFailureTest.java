package br.com.bg7.appvistoria.login;

import org.junit.Test;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by: elison
 * Date: 2017-07-25
 */
public class LoginPresenterUserServiceResponseFailureTest extends LoginPresenterBaseTest {


    @Test
    public void shouldShowCannotLoginWhenUserDoNotExist() {
        when(loginView.isConnected()).thenReturn(true);
        when(userRepository.findByUsername(anyString())).thenReturn(null);
        setUpToken();
        when(userHttpResponse.isSuccessful()).thenReturn(false);
        setUpUserResponse();

        callLogin();

        verifyTokenService();
        verifyUserService();
        verify(loginView).showCannotLoginError();
    }

    @Test
    public void shouldShowMainScreenWhenNoSuccessAndBadPassword() {
        when(loginView.isConnected()).thenReturn(true);
        setUpBadPassword();
        setUpToken();
        when(userHttpResponse.isSuccessful()).thenReturn(false);
        setUpUserResponse();

        callLogin();

        verifyTokenService();
        verifyUserService();
        verify(loginView).showMainScreen();
    }

    @Test
    public void shouldShowMainScreenWhenNoSuccessButUserAndPasswordOk() {
        when(loginView.isConnected()).thenReturn(true);
        setUpUserAndPasswordOk();
        setUpToken();
        when(userHttpResponse.isSuccessful()).thenReturn(false);
        setUpUserResponse();

        callLogin();

        verifyTokenService();
        verifyUserService();
        verify(loginView).showMainScreen();
    }

    @Test
    public void shouldShowCannotLoginWhenUserDoNotExistAndBodyIsNull() {
        when(loginView.isConnected()).thenReturn(true);
        when(userRepository.findByUsername(anyString())).thenReturn(null);
        setUpToken();
        when(userHttpResponse.isSuccessful()).thenReturn(true);
        when(userHttpResponse.body()).thenReturn(null);

        callLogin();

        verifyTokenService();
        verifyUserService();
        verify(loginView).showCannotLoginError();
    }

    @Test
    public void shouldShowMainScreenWhenNoBodyAndBadPassword() {
        when(loginView.isConnected()).thenReturn(true);
        setUpBadPassword();
        setUpToken();
        when(userHttpResponse.isSuccessful()).thenReturn(false);
        when(userHttpResponse.body()).thenReturn(null);

        callLogin();

        verifyTokenService();
        verifyUserService();
        verify(loginView).showMainScreen();
    }

    @Test
    public void shouldShowMainScreenWhenNoBodyButUserAndPasswordOk() {
        when(loginView.isConnected()).thenReturn(true);
        setUpUserAndPasswordOk();
        setUpToken();
        when(userHttpResponse.isSuccessful()).thenReturn(false);
        when(userHttpResponse.body()).thenReturn(null);

        callLogin();

        verifyTokenService();
        verifyUserService();
        verify(loginView).showMainScreen();
    }

    @Test
    public void shouldShowCannotLoginWhenUserDoNotExistAndUserIsUnauthorized() {
        when(loginView.isConnected()).thenReturn(true);
        when(userRepository.findByUsername(anyString())).thenReturn(null);
        setUpToken();
        when(userHttpResponse.isSuccessful()).thenReturn(false);
        when(userHttpResponse.code()).thenReturn(loginPresenter.UNAUTHORIZED_CODE);

        callLogin();

        verifyTokenService();
        verifyUserService();
        verify(loginView).showWrongPasswordError();
    }

    @Test
    public void shouldShowWrongPasswordWhenUserIsUnauthorizedAndBadPassword() {
        when(loginView.isConnected()).thenReturn(true);
        setUpBadPassword();
        setUpToken();
        when(userHttpResponse.isSuccessful()).thenReturn(false);
        when(userHttpResponse.code()).thenReturn(loginPresenter.UNAUTHORIZED_CODE);

        callLogin();

        verifyTokenService();
        verifyUserService();
        verify(loginView).showWrongPasswordError();
    }

    @Test
    public void shouldShowWrongPasswordWhenUserIsUnauthorizedButUserAndPasswordOk() {
        when(loginView.isConnected()).thenReturn(true);
        setUpUserAndPasswordOk();
        setUpToken();
        when(userHttpResponse.isSuccessful()).thenReturn(false);
        when(userHttpResponse.code()).thenReturn(loginPresenter.UNAUTHORIZED_CODE);

        callLogin();

        verifyTokenService();
        verifyUserService();
        verify(loginView).showWrongPasswordError();
    }
}
