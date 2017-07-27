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
        when(tokenHttpResponse.isSuccessful()).thenReturn(true);
    }

    @Test
    public void shouldShowCannotLoginWhenNoSuccessAndNoUser() {

        setUpNoUser();
        setUpNoSuccessful();

        callLogin();

        invokeTokenServiceOnResponse();
        verify(loginView).showCannotLoginError();
    }

    @Test
    public void shouldShowBadCredentialsWhenNoSuccessAndBadPassword() {
        setUpBadPassword();
        setUpNoSuccessful();

        callLogin();

        invokeTokenServiceOnResponse();
        verify(loginView).showBadCredentialsError();
    }

    @Test
    public void shouldShowMainScreenWhenNoSuccessButUserAndPassOk() {
        setUpUserAndPasswordOk();
        setUpNoSuccessful();

        callLogin();

        invokeTokenServiceOnResponse();
        verify(loginView).showMainScreen();
    }

    @Test
    public void shouldShowCannotLoginWhenNoBodyAndNoUser() {
        setUpNoUser();

        when(tokenHttpResponse.body()).thenReturn(null);

        callLogin();

        invokeTokenServiceOnResponse();
        verify(loginView).showCannotLoginError();
    }

    @Test
    public void shouldShowBadCredentialsWhenNoBodyAndBadPassword() {
        setUpBadPassword();
        when(tokenHttpResponse.body()).thenReturn(null);

        callLogin();

        invokeTokenServiceOnResponse();
        verify(loginView).showBadCredentialsError();
    }

    @Test
    public void shouldShowMainScreenWhenNoBodyButUserAndPasswordOk() {
        setUpUserAndPasswordOk();
        when(tokenHttpResponse.body()).thenReturn(null);

        callLogin();

        invokeTokenServiceOnResponse();
        verify(loginView).showMainScreen();
    }

    @Test
    public void shouldShowBadCredentialsWhenUnauthorizedAndNoUser() {
        setUpNoUser();
        setUpNoSuccessful();
        setUpTokenUnauthorizedCode();

        callLogin();

        invokeTokenServiceOnResponse();
        verify(loginView).showBadCredentialsError();
    }

    @Test
    public void shouldShowBadCredentialsWhenUnauthorizedAndBadPassword() {
        setUpBadPassword();
        setUpNoSuccessful();
        setUpTokenUnauthorizedCode();

        callLogin();

        invokeTokenServiceOnResponse();
        verify(loginView).showBadCredentialsError();
    }

    @Test
    public void shouldShowBadCredentialsWhenUnauthorizedButUserAndPasswordOk() {
        setUpUserAndPasswordOk();
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
