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

import static org.mockito.ArgumentMatchers.anyString;
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
        when(userRepository.findByUsername(anyString())).thenReturn(null);

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
    public void shouldShowCannotLoginWhenNoTokenAndNoUser() {

        when(userRepository.findByUsername(anyString())).thenReturn(null);

        callLogin();

        setUpConnectivityError();
        verify(loginView).showCannotLoginError();
    }

    @Test
    public void shouldShowBadCredentialsWhenNoTokenAndBadPassword() {
        setUpBadPassword();

        callLogin();

        setUpConnectivityError();
        verify(loginView).showBadCredentialsError();
    }

    @Test
    public void shouldShowMainScreenWhenNoTokenButUserAndPassOk() {
        setUpUserAndPasswordOk();

        callLogin();

        setUpConnectivityError();
        verify(loginView).showMainScreen();
    }

    @Test
    public void shouldShowCannotLoginWhenNoTokenBodyAndNoUser() {
        when(userRepository.findByUsername(anyString())).thenReturn(null);

        callLogin();

        verifyTokenSuccessful();
        tokenCallBackCaptor.getValue().onResponse(tokenHttpResponse);
        verify(loginView).showCannotLoginError();
    }

    @Test
    public void shouldShowCannotLoginWhenNoTokenBodyAndBadPassword() {
        setUpBadPassword();

        callLogin();

        verifyTokenSuccessful();
        when(tokenHttpResponse.body()).thenReturn(null);
        tokenCallBackCaptor.getValue().onResponse(tokenHttpResponse);
        verify(loginView).showBadCredentialsError();
    }

    @Test
    public void shuldShowMainScreenWhenNoTokenBodyButUserAndPassOk() {
        setUpUserAndPasswordOk();

        callLogin();

        verifyTokenSuccessful();
        when(tokenHttpResponse.body()).thenReturn(null);
        tokenCallBackCaptor.getValue().onResponse(tokenHttpResponse);
        verify(loginView).showMainScreen();
    }

    @Test
    public void shouldShowCannotLoginWhenSomeErrorAndNoUser() {
        when(userRepository.findByUsername(anyString())).thenReturn(null);

        callLogin();

        verifyException();
        verify(loginView).showCannotLoginError();
    }

    @Test
    public void shouldShowCannotLoginWhenSomeErrorAndBadPassword() {
        setUpBadPassword();

        callLogin();

        verifyException();
        verify(loginView).showBadCredentialsError();
    }

    @Test
    public void shouldShowMainScreenWhenSomeErrorButUserAndPassOk() {
        setUpUserAndPasswordOk();

        callLogin();

        verifyException();
        verify(loginView).showMainScreen();
    }

    private void setUpConnectivityError() {
        verify(tokenService).getToken(matches(USERNAME), matches(PASSWORD), tokenCallBackCaptor.capture());
        tokenCallBackCaptor.getValue().onFailure(new IOException());
    }

    private void verifyException() {
        verify(tokenService).getToken(matches(USERNAME), matches(PASSWORD), tokenCallBackCaptor.capture());
        tokenCallBackCaptor.getValue().onFailure(new Exception());
    }

    private void verifyTokenSuccessful() {
        verify(tokenService).getToken(matches(USERNAME), matches(PASSWORD), tokenCallBackCaptor.capture());
        when(tokenHttpResponse.isSuccessful()).thenReturn(true);
    }
}