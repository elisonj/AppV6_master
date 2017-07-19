package br.com.bg7.appvistoria.login;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;

import br.com.bg7.appvistoria.view.listeners.LoginCallback;

import static org.mockito.ArgumentMatchers.matches;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by: luciolucio
 * Date: 2017-07-19
 */

public class LoginPresenterOnlineTest extends LoginPresenterBaseTest {

    @Captor
    private ArgumentCaptor<LoginCallback> loginCallbackCaptor;

    @Override
    @Before
    public void setUp() {
        super.setUp();
        when(loginView.isConnected()).thenReturn(true);
    }

    @Test
    public void shouldShowMainScreenWhenLoggedInSuccessfully() {
        callLogin();

        verify(loginService).requestToken(loginCallbackCaptor.capture(), matches(USERNAME), matches(PASSWORD));
        loginCallbackCaptor.getValue().onSucess();

        verify(loginView).showMainScreen();
    }

    @Test
    public void shouldShowOfflineLoginErrorWhenConnectionTimesOut() {
        callLogin();

        verify(loginService).requestToken(loginCallbackCaptor.capture(), matches(USERNAME), matches(PASSWORD));
        loginCallbackCaptor.getValue().onTimeout();

        verify(loginView).showCannotLoginOfflineError();
    }

    @Test
    public void shouldShowOfflineLoginErrorWhenConnectionFails() {
        callLogin();

        verify(loginService).requestToken(loginCallbackCaptor.capture(), matches(USERNAME), matches(PASSWORD));
        loginCallbackCaptor.getValue().onConnectionFailed();

        verify(loginView).showCannotLoginOfflineError();
    }

    @Test
    public void shouldShowLoginErrorWhenAnotherFailureHappens() {
        callLogin();

        verify(loginService).requestToken(loginCallbackCaptor.capture(), matches(USERNAME), matches(PASSWORD));
        loginCallbackCaptor.getValue().onError();

        verify(loginView).showCannotLoginError();
    }
}
