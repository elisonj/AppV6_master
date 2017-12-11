package br.com.bg7.appvistoria.login;

import org.junit.Test;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.matches;
import static org.mockito.Mockito.verify;

/**
 * Created by: elison
 * Date: 2017-07-21
 *
 * Linha 1 da tabela
 * https://bg7.easyredmine.com/projects/185/wiki/Pode_falar_mais_sobre_a_tela_de_login
 */
public class LoginPresenterTokenServiceFailureTest extends LoginPresenterTestBase {

    /**
     * 1.1 (a)
     */
    @Test
    public void shouldShowCannotLoginWhenNoConnectionAndNoUser() {
        setUpNoConnection();
        setUpNoUser();

        callLogin();

        verifyShowCannotLogin();
    }

    /**
     * 1.1 (b)
     */
    @Test
    public void shouldShowBadCredentialsWhenNoConnectionAndBadPassword() {
        setUpNoConnection();
        setUpBadPassword();

        callLogin();

        verifyShowBadCredentials();
    }

    /**
     * 1.1 (c)
     */
    @Test
    public void shouldShowMainScreenWhenNoConnectionAndGoodPassword() {
        setUpNoConnection();
        setUpGoodPassword();

        callLogin();

        verifyShowMainScreen();
    }

    /**
     * 1.2 (a)
     */
    @Test
    public void shouldShowCannotLoginWhenNoUserAndConnectivityError() {
        setUpNoUser();

        callLogin();

        invokeIOException();
        verifyShowCannotLogin();
    }

    /**
     * 1.2 (b)
     */
    @Test
    public void shouldShowBadCredentialsWhenBadPasswordAndConnectivityError() {
        setUpBadPassword();

        callLogin();

        invokeIOException();
        verifyShowBadCredentials();
    }

    /**
     * 1.2 (c)
     */
    @Test
    public void shouldShowMainScreenWhenGoodPasswordAndConnectivityError() {
        setUpGoodPassword();

        callLogin();

        invokeIOException();
        verifyShowMainScreen();
    }

    /**
     * 1.3 (a)
     */
    @Test
    public void shouldShowCannotLoginWhenNoUserAndOtherError() {
        setUpNoUser();

        callLogin();

        invokeRuntimeException();
        verifyShowCannotLogin();
    }

    /**
     * 1.3 (b)
     */
    @Test
    public void shouldShowBadCredentialsWhenBadPasswordAndOtherError() {
        setUpBadPassword();

        callLogin();

        invokeRuntimeException();
        verifyShowBadCredentials();
    }

    /**
     * 1.3 (c)
     */
    @Test
    public void shouldShowMainScreenWhenGoodPasswordAndOtherError() {
        setUpGoodPassword();

        callLogin();

        invokeRuntimeException();
        verifyShowMainScreen();
    }

    private void invokeIOException() {
        verify(tokenService).getToken(matches(USERNAME), matches(password), tokenCallBackCaptor.capture());
        tokenCallBackCaptor.getValue().onFailure(new IOException());
    }

    private void invokeRuntimeException() {
        verify(tokenService).getToken(matches(USERNAME), matches(password), tokenCallBackCaptor.capture());
        tokenCallBackCaptor.getValue().onFailure(new RuntimeException());
    }
}
