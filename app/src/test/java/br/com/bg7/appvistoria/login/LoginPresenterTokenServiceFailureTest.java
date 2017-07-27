package br.com.bg7.appvistoria.login;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import java.io.IOException;

import br.com.bg7.appvistoria.data.source.remote.HttpCallback;
import br.com.bg7.appvistoria.data.source.remote.HttpResponse;
import br.com.bg7.appvistoria.data.source.remote.dto.Token;

import static org.mockito.ArgumentMatchers.matches;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by: luciolucio
 * Date: 2017-07-21
 */

public class LoginPresenterTokenServiceFailureTest extends LoginPresenterBaseTest {

    @Captor
    private ArgumentCaptor<HttpCallback<Token>> tokenCallBackCaptor;

    @Mock
    HttpResponse<Token> tokenHttpResponse;

    @Before
    public void setUp() {
        super.setUp();
        when(loginView.isConnected()).thenReturn(true);
    }

    @Test
    public void shouldShowCannotLoginWhenNoConnectionAndNoUser() {
        setUpNoConnection();
        setUpNoUser();

        callLogin();

        verify(loginView).showCannotLoginError();
    }

    @Test
    public void shouldShowBadCredentialsWhenNoConnectionAndBadPassword() {
        setUpNoConnection();
        setUpBadPassword();

        callLogin();

        verify(loginView).showBadCredentialsError();
    }

    @Test
    public void shouldShowMainScreenWhenNoConnection() {
        setUpNoConnection();
        setUpUserAndPasswordOk();

        callLogin();

        verify(loginView).showMainScreen();
    }

    @Test
    public void shouldShowCannotLoginWhenConnectivityErrorAndNoUser() {
        setUpNoUser();

        callLogin();

        invokeIOException();
        verify(loginView).showCannotLoginError();
    }

    @Test
    public void shouldShowBadCredentialsWhenConnectivityErrorAndBadPassword() {
        setUpBadPassword();

        callLogin();

        invokeIOException();
        verify(loginView).showBadCredentialsError();
    }

    @Test
    public void shouldShowMainScreenWhenNoTokenButUserAndPassOk() {
        setUpUserAndPasswordOk();

        callLogin();

        invokeIOException();
        verify(loginView).showMainScreen();
    }

    @Test
    public void shouldShowCannotLoginWhenOtherErrorAndNoUser() {
        setUpNoUser();

        callLogin();

        invokeRuntimeException();
        verify(loginView).showCannotLoginError();
    }

    @Test
    public void shouldShowCannotLoginWhenOtherErrorAndBadPassword() {
        setUpBadPassword();

        callLogin();

        invokeRuntimeException();
        verify(loginView).showBadCredentialsError();
    }

    @Test
    public void shouldShowMainScreenWhenOtherErrorButUserAndPassOk() {
        setUpUserAndPasswordOk();

        callLogin();

        invokeRuntimeException();
        verify(loginView).showMainScreen();
    }

    private void invokeIOException() {
        verify(tokenService).getToken(matches(USERNAME), matches(PASSWORD), tokenCallBackCaptor.capture());
        tokenCallBackCaptor.getValue().onFailure(new IOException());
    }

    private void invokeRuntimeException() {
        verify(tokenService).getToken(matches(USERNAME), matches(PASSWORD), tokenCallBackCaptor.capture());
        tokenCallBackCaptor.getValue().onFailure(new RuntimeException());
    }
}
