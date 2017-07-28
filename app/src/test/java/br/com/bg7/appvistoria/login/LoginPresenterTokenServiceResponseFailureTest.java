package br.com.bg7.appvistoria.login;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by: elison
 * Date: 2017-07-21
 *
 * Linha 2 da tabela
 * https://bg7.easyredmine.com/projects/185/wiki/Pode_falar_mais_sobre_a_tela_de_login
 */
public class LoginPresenterTokenServiceResponseFailureTest extends LoginPresenterBaseTest {

    @Before
    public void setUp() {
        super.setUp();
        when(loginView.isConnected()).thenReturn(true);
        when(tokenHttpResponse.isSuccessful()).thenReturn(false);
    }

    /**
     * 2.1 (a)
     */
    @Test
    public void shouldShowCannotLoginWhenNoUser() {
        setUpNoUser();

        callLogin();

        invokeTokenService();
        verify(loginView).showCannotLoginError();
    }

    /**
     * 2.1 (b)
     */
    @Test
    public void shouldShowBadCredentialsWhenBadPassword() {
        setUpBadPassword();

        callLogin();

        invokeTokenService();
        verify(loginView).showBadCredentialsError();
    }

    /**
     * 2.1 (c)
     */
    @Test
    public void shouldShowMainScreenWhenGoodPassword() {
        setUpGoodPassword();

        callLogin();

        invokeTokenService();
        verify(loginView).showMainScreen();
    }

    /**
     * 2.2 (a)
     */
    @Test
    public void shouldShowCannotLoginWhenBodyNullAndNoUser() {
        setUpNullBody();
        setUpNoUser();

        callLogin();

        invokeTokenService();
        verify(loginView).showCannotLoginError();
    }

    /**
     * 2.2 (b)
     */
    @Test
    public void shouldShowBadCredentialsWhenBodyNullAndBadPassword() {
        setUpNullBody();
        setUpBadPassword();

        callLogin();

        invokeTokenService();
        verify(loginView).showBadCredentialsError();
    }

    /**
     * 2.2 (c)
     */
    @Test
    public void shouldShowMainScreenWhenBodyNullAndGoodPassword() {
        setUpNullBody();
        setUpGoodPassword();

        callLogin();

        invokeTokenService();
        verify(loginView).showMainScreen();
    }

    /**
     * 2.3 (a)
     */
    @Test
    public void shouldShowBadCredentialsWhenNotAuthorizedAndNoUser() {
        setUpTokenUnauthorizedCode();
        setUpNoUser();

        callLogin();

        invokeTokenService();
        verify(loginView).showBadCredentialsError();
    }

    /**
     * 2.3 (b)
     */
    @Test
    public void shouldShowBadCredentialsWhenNotAuthorizedAndBadPassword() {
        setUpTokenUnauthorizedCode();
        setUpBadPassword();

        callLogin();

        invokeTokenService();
        verify(loginView).showBadCredentialsError();
    }

    /**
     * 2.3 (c)
     */
    @Test
    public void shouldShowBadCredentialsWhenNotAuthorizedAndGoodPassword() {
        setUpTokenUnauthorizedCode();
        setUpGoodPassword();

        callLogin();

        invokeTokenService();
        verify(loginView).showBadCredentialsError();
    }

    private void setUpNullBody() {
        when(tokenHttpResponse.isSuccessful()).thenReturn(true);
        when(tokenHttpResponse.body()).thenReturn(null);
    }
}
