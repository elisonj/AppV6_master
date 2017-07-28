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

        invokeUserService();
        verify(loginView).showCannotLoginError();
    }

    @Test
    public void shouldSaveUserAndEnterWhenBadPassword() {
        setUpBadPassword();

        callLogin();

        invokeUserService();
        verifySaveUserAndShowMainScreen();
    }

    @Test
    public void shouldSaveUserAndEnterWhenGoodPassword() {
        setUpGoodPassword();

        callLogin();

        invokeUserService();
        verifySaveUserAndShowMainScreen();
    }

    @Test
    public void shouldShowCannotLoginWhenBodyNullAndNoUser() {
        setUpNullBody();
        setUpNoUser();

        callLogin();

        invokeUserService();
        verify(loginView).showCannotLoginError();
    }

    @Test
    public void shouldSaveUserAndEnterWhenBodyNullAndBadPassword() {
        setUpNullBody();
        setUpBadPassword();

        callLogin();

        invokeUserService();
        verifySaveUserAndShowMainScreen();
    }

    @Test
    public void shouldSaveUserAndEnterWhenBodyNullAndGoodPassword() {
        setUpNullBody();
        setUpGoodPassword();

        callLogin();

        invokeUserService();
        verifySaveUserAndShowMainScreen();
    }

    @Test
    public void shouldShowBadCredentialsWhenNoUserAndUserIsUnauthorized() {
        setUpNoUser();
        setUpUserUnauthorized();

        callLogin();

        invokeUserService();
        verify(loginView).showBadCredentialsError();
    }

    @Test
    public void shouldShowBadCredentialsWhenUserIsUnauthorizedAndBadPassword() {
        setUpBadPassword();
        setUpUserUnauthorized();

        callLogin();

        invokeUserService();
        verify(loginView).showBadCredentialsError();
    }

    @Test
    public void shouldShowBadCredentialsWhenUserIsUnauthorizedAndGoodPassword() {
        setUpGoodPassword();
        setUpUserUnauthorized();

        callLogin();

        invokeUserService();
        verify(loginView).showBadCredentialsError();
    }

    private void setUpNullBody() {
        when(userHttpResponse.body()).thenReturn(null);
    }

    /**
     * TokenService always gets called first, so we call it before actually calling
     * the UserService itself
     */
    @Override
    void invokeUserService() {
        invokeTokenService();
        super.invokeUserService();
    }

    private void setUpUserUnauthorized() {
        when(userHttpResponse.code()).thenReturn(LoginPresenter.UNAUTHORIZED_CODE);
    }
}
