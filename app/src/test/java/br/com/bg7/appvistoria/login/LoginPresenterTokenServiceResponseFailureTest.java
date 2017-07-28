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
        when(tokenHttpResponse.isSuccessful()).thenReturn(true);
    }

    /**
     * 2.1 (a)
     */
    @Test
    public void shouldShowCannotLoginWhenNoSuccessAndNoUser() {

        setUpNoUser();
        setUpNoSuccessful();

        callLogin();

        invokeTokenServiceOnResponse();
        verify(loginView).showCannotLoginError();
    }

    /**
     * 2.1 (b)
     */
    @Test
    public void shouldShowBadCredentialsWhenNoSuccessAndBadPassword() {
        setUpBadPassword();
        setUpNoSuccessful();

        callLogin();

        invokeTokenServiceOnResponse();
        verify(loginView).showBadCredentialsError();
    }

    /**
     * 2.1 (c)
     */
    @Test
    public void shouldShowMainScreenWhenNoSuccessButUserAndPassOk() {
        setUpGoodPassword();
        setUpNoSuccessful();

        callLogin();

        invokeTokenServiceOnResponse();
        verify(loginView).showMainScreen();
    }

    /**
     * 2.2 (a)
     */
    @Test
    public void shouldShowCannotLoginWhenNoBodyAndNoUser() {
        setUpNoUser();

        when(tokenHttpResponse.body()).thenReturn(null);

        callLogin();

        invokeTokenServiceOnResponse();
        verify(loginView).showCannotLoginError();
    }

    /**
     * 2.2 (b)
     */
    @Test
    public void shouldShowBadCredentialsWhenNoBodyAndBadPassword() {
        setUpBadPassword();
        when(tokenHttpResponse.body()).thenReturn(null);

        callLogin();

        invokeTokenServiceOnResponse();
        verify(loginView).showBadCredentialsError();
    }

    /**
     * 2.2 (c)
     */
    @Test
    public void shouldShowMainScreenWhenNoBodyButUserAndPasswordOk() {
        setUpGoodPassword();
        when(tokenHttpResponse.body()).thenReturn(null);

        callLogin();

        invokeTokenServiceOnResponse();
        verify(loginView).showMainScreen();
    }

    /**
     * 2.3 (a)
     */
    @Test
    public void shouldShowBadCredentialsWhenUnauthorizedAndNoUser() {
        setUpNoUser();
        setUpNoSuccessful();
        setUpTokenUnauthorizedCode();

        callLogin();

        invokeTokenServiceOnResponse();
        verify(loginView).showBadCredentialsError();
    }

    /**
     * 2.3 (b)
     */
    @Test
    public void shouldShowBadCredentialsWhenUnauthorizedAndBadPassword() {
        setUpBadPassword();
        setUpNoSuccessful();
        setUpTokenUnauthorizedCode();

        callLogin();

        invokeTokenServiceOnResponse();
        verify(loginView).showBadCredentialsError();
    }

    /**
     * 2.3 (c)
     */
    @Test
    public void shouldShowBadCredentialsWhenUnauthorizedButUserAndPasswordOk() {
        setUpGoodPassword();
        setUpNoSuccessful();
        setUpTokenUnauthorizedCode();

        callLogin();

        invokeTokenServiceOnResponse();
        verify(loginView).showBadCredentialsError();
    }

    private void setUpNoSuccessful() {
        when(tokenHttpResponse.isSuccessful()).thenReturn(false);
    }

}
