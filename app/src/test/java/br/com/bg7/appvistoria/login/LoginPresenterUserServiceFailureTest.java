package br.com.bg7.appvistoria.login;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import br.com.bg7.appvistoria.data.User;

import static org.mockito.ArgumentMatchers.any;
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
public class LoginPresenterUserServiceFailureTest extends LoginPresenterBaseTest {

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
    public void shouldMainScreenLoginWhenNoConnectionAndBadPassword() {
        setUpNoConnection();
        setUpBadPassword();

        callLogin();

        invokeTokenService();
        verifySaveUserAndShowMainScreen();
    }

    /**
     * 3.1 (c)
     */
    @Test
    public void shouldMainScreenLoginWhenNoConnectionAndGoodPassword() {
        setUpNoConnection();
        setUpGoodPassword();

        callLogin();

        invokeTokenService();
        verifySaveUserAndShowMainScreen();
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
    public void shouldShowMainScreenLoginWhenConnectivityErrorAndBadPassword() {
        setUpBadPassword();

        callLogin();

        invokeTokenService();
        invokeIOException();
        verifySaveUserAndShowMainScreen();
    }

    /**
     * 3.2 (c)
     */
    @Test
    public void shouldShowMainScreenLoginWhenConnectivityErrorAndGoodPassword() {
        setUpGoodPassword();

        callLogin();

        invokeTokenService();
        invokeIOException();
        verifySaveUserAndShowMainScreen();
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
    public void shouldShowMainScreenLoginWhenOtherErrorAndBadPassword() {
        setUpBadPassword();

        callLogin();

        invokeTokenService();
        invokeRuntimeException();
        verifySaveUserAndShowMainScreen();
    }

    /**
     * 3.3 (c)
     */
    @Test
    public void shouldShowMainScreenLoginWhenOtherErrorAndGoodPassword() {
        setUpGoodPassword();

        callLogin();

        invokeTokenService();
        invokeRuntimeException();
        verifySaveUserAndShowMainScreen();
    }

    /**
     * The TokenService call checks isConnected() before this one, so we need to return
     * true to that first, then return false so that UserService will think there is no
     * connection now
     */
    void setUpNoConnection() {
        when(loginView.isConnected()).thenReturn(true, false);
    }

    private void verifySaveUserAndShowMainScreen() {
        // TODO: Realmente verificar o usuário
        verify(userRepository).save((User)any());
        verify(loginView).showMainScreen();
    }

    private void invokeIOException() {
        invokeException(new IOException());
    }


    private void invokeRuntimeException() {
        invokeException(new RuntimeException());
    }

    private void invokeException(Throwable t) {
        verify(userService).getUser(matches(TOKEN), matches(USER_ID), userCallBackCaptor.capture());
        userCallBackCaptor.getValue().onFailure(t);
    }
}
