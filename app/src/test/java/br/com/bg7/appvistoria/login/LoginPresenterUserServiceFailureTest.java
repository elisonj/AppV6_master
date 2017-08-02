package br.com.bg7.appvistoria.login;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.matches;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by: elison
 * Date: 2017-07-21
 *
 * Linha 3 da tabela
 * https://bg7.easyredmine.com/projects/185/wiki/Pode_falar_mais_sobre_a_tela_de_login
 */
public class LoginPresenterUserServiceFailureTest extends LoginPresenterTestBase {

    @Before
    public void setUp() {
        super.setUp();
        when(userHttpResponse.isSuccessful()).thenReturn(false);
    }

    /**
     * 3.1 (a)
     */
    @Test
    public void shouldShowCannotLoginWhenNoConnectionAndNoUser() {
        setUpNoConnection();
        setUpNoUser();

        callLogin();

        invokeTokenService();
        verify(loginView).showCannotLoginError();
    }

    /**
     * 3.1 (b)
     */
    @Test
    public void shouldSaveTokenAndPasswordAndEnterWhenNoConnectionAndBadPassword() {
        setUpNoConnection();
        setUpBadPassword();

        callLogin();

        invokeTokenService();
        verifySaveTokenAndPasswordAndShowMainScreen();
    }

    /**
     * 3.1 (c)
     */
    @Test
    public void shouldSaveTokenAndEnterWhenNoConnectionAndGoodPassword() {
        setUpNoConnection();
        setUpGoodPassword();

        callLogin();

        invokeTokenService();
        verifySaveTokenAndShowMainScreen();
    }

    /**
     * 3.2 (a)
     */
    @Test
    public void shouldShowCannotLoginWhenConnectivityErrorAndNoUser() {
        setUpNoUser();

        callLogin();

        invokeTokenService();
        invokeIOException();
        verify(loginView).showCannotLoginError();
    }

    /**
     * 3.2 (b)
     */
    @Test
    public void shouldSaveTokenAndPasswordAndEnterWhenConnectivityErrorAndBadPassword() {
        setUpBadPassword();

        callLogin();

        invokeTokenService();
        invokeIOException();
        verifySaveTokenAndPasswordAndShowMainScreen();
    }

    /**
     * 3.2 (c)
     */
    @Test
    public void shouldSaveTokenAndEnterWhenConnectivityErrorAndGoodPassword() {
        setUpGoodPassword();

        callLogin();

        invokeTokenService();
        invokeIOException();
        verifySaveTokenAndShowMainScreen();
    }

    /**
     * 3.3 (a)
     */
    @Test
    public void shouldShowCannotLoginWhenOtherErrorAndNoUser() {
        setUpNoUser();

        callLogin();

        invokeTokenService();
        invokeRuntimeException();
        verify(loginView).showCannotLoginError();
    }

    /**
     * 3.3 (b)
     */
    @Test
    public void shouldSaveTokenAndPasswordAndEnterWhenOtherErrorAndBadPassword() {
        setUpBadPassword();

        callLogin();

        invokeTokenService();
        invokeRuntimeException();
        verifySaveTokenAndPasswordAndShowMainScreen();
    }

    /**
     * 3.3 (c)
     */
    @Test
    public void shouldSaveTokenAndEnterWhenOtherErrorAndGoodPassword() {
        setUpGoodPassword();

        callLogin();

        invokeTokenService();
        invokeRuntimeException();
        verifySaveTokenAndShowMainScreen();
    }

    /**
     * The TokenService call checks isConnected() before this one, so we need to return
     * true to that first, then return false so that UserService will think there is no
     * connection now
     */
    void setUpNoConnection() {
        when(loginView.isConnected()).thenReturn(true, false);
    }

    private void invokeIOException() {
        invokeException(new IOException());
    }


    private void invokeRuntimeException() {
        invokeException(new RuntimeException());
    }

    private void invokeException(Throwable t) {
        verify(userService).getUser(matches(TOKEN_FROM_SERVICE), matches(USER_ID), userCallBackCaptor.capture());
        userCallBackCaptor.getValue().onFailure(t);
    }
}
