package br.com.bg7.appvistoria.login;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by: elison
 * Date: 2017-07-21
 */
public class LoginPresenterTokenServiceResponseFailureTest extends LoginPresenterBaseTest {

    @Before
    public void setUp() {
        super.setUp();
        when(loginView.isConnected()).thenReturn(true);
    }

    @Test
    public void shouldShowCannotLoginWhenNoSuccessAndNoUser() {

        setUpNullUser();
        when(tokenHttpResponse.isSuccessful()).thenReturn(false);

        callLogin();

        verifyTokenService();
        verify(loginView).showCannotLoginError();
    }

    @Test
    public void shouldShowCannotLoginWhenNoSuccessAndBadPassword() {
        setUpBadPassword();
        when(tokenHttpResponse.isSuccessful()).thenReturn(false);

        callLogin();

        verifyTokenService();
        verify(loginView).showBadCredentialsError();
    }

    @Test
    public void shouldShowMainScreenWhenNoSuccessButUserAndPassOk() {
        setUpUserAndPasswordOk();
        when(tokenHttpResponse.isSuccessful()).thenReturn(false);

        callLogin();

        verifyTokenService();
        verify(loginView).showMainScreen();
    }


    @Test
    public void shouldShowCannotLoginWhenNoBodyAndNoUser() {
        setUpNullUser();
        when(tokenHttpResponse.isSuccessful()).thenReturn(true);
        when(tokenHttpResponse.body()).thenReturn(null);

        callLogin();

        verifyTokenService();
        verify(loginView).showCannotLoginError();
    }

    @Test
    public void shouldShowCannotLoginWhenNoBodyAndBadPassword() {
        setUpBadPassword();
        when(tokenHttpResponse.isSuccessful()).thenReturn(true);
        when(tokenHttpResponse.body()).thenReturn(null);

        callLogin();

        verifyTokenService();
        verify(loginView).showBadCredentialsError();
    }

    @Test
    public void shouldShowMainScreenWhenNoBodyButUserAndPasswordOk() {
        setUpUserAndPasswordOk();
        when(tokenHttpResponse.isSuccessful()).thenReturn(true);
        when(tokenHttpResponse.body()).thenReturn(null);

        callLogin();

        verifyTokenService();
        verify(loginView).showMainScreen();
    }
    @Test
    public void shouldShowCannotLoginWhenUnauthorizedAndNoUser() {
        setUpNullUser();
        when(tokenHttpResponse.isSuccessful()).thenReturn(false);
        when(tokenHttpResponse.code()).thenReturn(loginPresenter.UNAUTHORIZED_CODE);

        callLogin();

        verifyTokenService();
        verify(loginView).showBadCredentialsError();
    }
    @Test
    public void shouldShowCannotLoginWhenUnauthorizedAndBadPassword() {
        setUpBadPassword();
        when(tokenHttpResponse.isSuccessful()).thenReturn(false);
        when(tokenHttpResponse.code()).thenReturn(loginPresenter.UNAUTHORIZED_CODE);

        callLogin();

        verifyTokenService();
        verify(loginView).showBadCredentialsError();
    }


    @Test
    public void shouldShowCannotLoginWhenUnauthorizedButUserAndPasswordOk() {
        setUpUserAndPasswordOk();
        when(tokenHttpResponse.isSuccessful()).thenReturn(false);
        when(tokenHttpResponse.code()).thenReturn(loginPresenter.UNAUTHORIZED_CODE);

        callLogin();

        verifyTokenService();
        verify(loginView).showBadCredentialsError();
    }
}
