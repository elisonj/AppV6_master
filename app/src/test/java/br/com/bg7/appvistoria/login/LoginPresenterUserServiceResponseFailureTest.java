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
    public void shouldShowCannotLoginWhenUserDoNotExist() {
        setUpNullUser();
        setUpToken();
        setUpUserResponse();

        callLogin();

        invokeTokenServiceOnResponse();
        invokeUserServiceOnResponse();
        verify(loginView).showCannotLoginError();
    }

    @Test
    public void shouldShowMainScreenWhenNoSuccessAndBadPassword() {
        setUpBadPassword();
        setUpToken();
        setUpUserResponse();

        callLogin();

        invokeTokenServiceOnResponse();
        invokeUserServiceOnResponse();
        verify(loginView).showMainScreen();
    }

    @Test
    public void shouldShowMainScreenWhenNoSuccessButUserAndPasswordOk() {
        setUpUserAndPasswordOk();
        setUpToken();
        setUpUserResponse();

        callLogin();

        invokeTokenServiceOnResponse();
        invokeUserServiceOnResponse();
        verify(loginView).showMainScreen();
    }

    @Test
    public void shouldShowCannotLoginWhenUserDoNotExistAndBodyIsNull() {
        setUpNullUser();
        setUpToken();
        setUpUserResponseSuccessful();
        when(userHttpResponse.body()).thenReturn(null);

        callLogin();

        invokeTokenServiceOnResponse();
        invokeUserServiceOnResponse();
        verify(loginView).showCannotLoginError();
    }

    @Test
    public void shouldShowMainScreenWhenNoBodyAndBadPassword() {
        setUpBadPassword();
        setUpToken();
        when(userHttpResponse.body()).thenReturn(null);

        callLogin();

        invokeTokenServiceOnResponse();
        invokeUserServiceOnResponse();
        verify(loginView).showMainScreen();
    }

    @Test
    public void shouldShowMainScreenWhenNoBodyButUserAndPasswordOk() {
        setUpUserAndPasswordOk();
        setUpToken();
        when(userHttpResponse.body()).thenReturn(null);

        callLogin();

        invokeTokenServiceOnResponse();
        invokeUserServiceOnResponse();
        verify(loginView).showMainScreen();
    }

    @Test
    public void shouldShowCannotLoginWhenUserDoNotExistAndUserIsUnauthorized() {
        setUpNullUser();
        setUpToken();
        setUpUserUnauthorizedCode();

        callLogin();

        invokeTokenServiceOnResponse();
        invokeUserServiceOnResponse();
        verify(loginView).showBadCredentialsError();
    }

    @Test
    public void shouldShowWrongPasswordWhenUserIsUnauthorizedAndBadPassword() {
        setUpBadPassword();
        setUpToken();
        setUpUserUnauthorizedCode();

        callLogin();

        invokeTokenServiceOnResponse();
        invokeUserServiceOnResponse();
        verify(loginView).showBadCredentialsError();
    }

    @Test
    public void shouldShowWrongPasswordWhenUserIsUnauthorizedButUserAndPasswordOk() {
        setUpUserAndPasswordOk();
        setUpToken();
        setUpUserUnauthorizedCode();

        callLogin();

        invokeTokenServiceOnResponse();
        invokeUserServiceOnResponse();
        verify(loginView).showBadCredentialsError();
    }

    private void setUpUserResponseSuccessful() {
        when(userHttpResponse.isSuccessful()).thenReturn(true);
    }
}
