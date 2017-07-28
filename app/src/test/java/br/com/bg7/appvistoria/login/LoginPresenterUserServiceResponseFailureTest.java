package br.com.bg7.appvistoria.login;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by: elison
 * Date: 2017-07-25
 *
 * Linha 4 da tabela
 * https://bg7.easyredmine.com/projects/185/wiki/Pode_falar_mais_sobre_a_tela_de_login
 */
public class LoginPresenterUserServiceResponseFailureTest extends LoginPresenterBaseTest {

    @Before
    public void setUp() {
        super.setUp();
        when(loginView.isConnected()).thenReturn(true);
        when(userHttpResponse.isSuccessful()).thenReturn(false);
    }

    /**
     * 4.1 (a)
     */
    @Test
    public void shouldShowCannotLoginWhenNoUser() {
        setUpNoUser();

        callLogin();

        invokeUserService();
        verify(loginView).showCannotLoginError();
    }

    /**
     * 4.1 (b)
     */
    @Test
    public void shouldSaveTokenAndPasswordAndEnterWhenBadPassword() {
        setUpBadPassword();

        callLogin();

        invokeUserService();
        verifySaveTokenAndPasswordAndShowMainScreen();
    }

    /**
     * 4.1 (c)
     */
    @Test
    public void shouldSaveTokenAndEnterWhenGoodPassword() {
        setUpGoodPassword();

        callLogin();

        invokeUserService();
        verifySaveTokenAndShowMainScreen();
    }

    /**
     * 4.2 (a)
     */
    @Test
    public void shouldShowCannotLoginWhenBodyNullAndNoUser() {
        setUpNullBody();
        setUpNoUser();

        callLogin();

        invokeUserService();
        verify(loginView).showCannotLoginError();
    }

    /**
     * 4.2 (b)
     */
    @Test
    public void shouldSaveTokenAndPasswordAndEnterWhenBodyNullAndBadPassword() {
        setUpNullBody();
        setUpBadPassword();

        callLogin();

        invokeUserService();
        verifySaveTokenAndPasswordAndShowMainScreen();
    }

    /**
     * 4.2 (c)
     */
    @Test
    public void shouldSaveTokenAndEnterWhenBodyNullAndGoodPassword() {
        setUpNullBody();
        setUpGoodPassword();

        callLogin();

        invokeUserService();
        verifySaveTokenAndShowMainScreen();
    }

    /**
     * 4.3 (a)
     */
    @Test
    public void shouldShowBadCredentialsWhenNoUserAndUserIsUnauthorized() {
        setUpNoUser();
        setUpUserUnauthorized();

        callLogin();

        invokeUserService();
        verify(loginView).showBadCredentialsError();
    }

    /**
     * 4.3 (b)
     */
    @Test
    public void shouldShowBadCredentialsWhenUserIsUnauthorizedAndBadPassword() {
        setUpBadPassword();
        setUpUserUnauthorized();

        callLogin();

        invokeUserService();
        verify(loginView).showBadCredentialsError();
    }

    /**
     * 4.3 (c)
     */
    @Test
    public void shouldShowBadCredentialsWhenUserIsUnauthorizedAndGoodPassword() {
        setUpGoodPassword();
        setUpUserUnauthorized();

        callLogin();

        invokeUserService();
        verify(loginView).showBadCredentialsError();
    }

    private void setUpNullBody() {
        when(userHttpResponse.isSuccessful()).thenReturn(true);
        when(userHttpResponse.body()).thenReturn(null);
    }

    private void setUpUserUnauthorized() {
        when(userHttpResponse.isSuccessful()).thenReturn(false);
        when(userHttpResponse.code()).thenReturn(LoginPresenter.UNAUTHORIZED_CODE);
    }
}
