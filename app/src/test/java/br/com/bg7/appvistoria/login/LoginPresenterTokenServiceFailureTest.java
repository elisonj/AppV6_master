package br.com.bg7.appvistoria.login;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import java.util.concurrent.TimeoutException;

import br.com.bg7.appvistoria.data.source.remote.HttpCallback;
import br.com.bg7.appvistoria.data.source.remote.HttpResponse;
import br.com.bg7.appvistoria.data.source.remote.dto.Token;
import br.com.bg7.appvistoria.vo.User;

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
    }

    @Test
    public void shouldShowCannotLoginWhenNoConnectionAndNoUser() {
        when(loginView.isConnected()).thenReturn(false);
        when(userRepository.findByUsername(anyString())).thenReturn(null);

        callLogin();

        verify(loginView).showCannotLoginError();
    }

    @Test
    public void showBadCredentialsWhenNoConnectionAndBadPassword() {
        when(loginView.isConnected()).thenReturn(false);
        setUpBadPassword();

        callLogin();

        verify(loginView).showWrongPasswordError();
    }

    @Test
    public void showMainScreenWhenNoConnection() {
        when(loginView.isConnected()).thenReturn(false);
        setUpUserAndPasswordOk();

        callLogin();

        verify(loginView).showMainScreen();
    }

    private void setUpUserAndPasswordOk() {
        when(userRepository.findByUsername(anyString())).thenReturn(new User());
        loginPresenter.checkpw = true;
    }

    @Test
    public void shouldShowCannotLoginWhenNoTokenAndNoUser() {
        when(loginView.isConnected()).thenReturn(true);

        when(userRepository.findByUsername(anyString())).thenReturn(null);

        callLogin();
        verify(tokenService).getToken(matches(USERNAME), matches(PASSWORD), tokenCallBackCaptor.capture());
        tokenCallBackCaptor.getValue().onFailure(new TimeoutException());

        verify(loginView).showCannotLoginOfflineError();
    }


    @Test
    public void showBadCredentialsWhenNoTokenAndBadPassword() {
        when(loginView.isConnected()).thenReturn(true);
        setUpBadPassword();

        callLogin();
        verify(tokenService).getToken(matches(USERNAME), matches(PASSWORD), tokenCallBackCaptor.capture());
        tokenCallBackCaptor.getValue().onFailure(new TimeoutException());

        verify(loginView).showWrongPasswordError();
    }

    @Test
    public void showMainScreenWhenNoTokenButUserAndPassOk() {
        when(loginView.isConnected()).thenReturn(true);
        setUpUserAndPasswordOk();

        callLogin();
        verify(tokenService).getToken(matches(USERNAME), matches(PASSWORD), tokenCallBackCaptor.capture());
        tokenCallBackCaptor.getValue().onFailure(new TimeoutException());

        verify(loginView).showMainScreen();
    }


    @Test
    public void shouldShowCannotLoginWhenNoTokenBodyAndNoUser() {
        when(loginView.isConnected()).thenReturn(true);

        when(userRepository.findByUsername(anyString())).thenReturn(null);

        callLogin();
        verify(tokenService).getToken(matches(USERNAME), matches(PASSWORD), tokenCallBackCaptor.capture());
        when(tokenHttpResponse.isSuccessful()).thenReturn(true);
        tokenCallBackCaptor.getValue().onResponse(tokenHttpResponse);

        verify(loginView).showCannotLoginError();
    }

    @Test
    public void shouldShowCannotLoginWhenNoTokenBodyAndBadPassword() {
        when(loginView.isConnected()).thenReturn(true);

        setUpBadPassword();

        callLogin();
        verify(tokenService).getToken(matches(USERNAME), matches(PASSWORD), tokenCallBackCaptor.capture());
        when(tokenHttpResponse.isSuccessful()).thenReturn(true);
        when(tokenHttpResponse.body()).thenReturn(null);

        tokenCallBackCaptor.getValue().onResponse(tokenHttpResponse);

        verify(loginView).showWrongPasswordError();
    }

    @Test
    public void showMainScreenWhenNoTokenBodyButUserAndPassOk() {
        when(loginView.isConnected()).thenReturn(true);
        setUpUserAndPasswordOk();

        callLogin();
        verify(tokenService).getToken(matches(USERNAME), matches(PASSWORD), tokenCallBackCaptor.capture());
        when(tokenHttpResponse.isSuccessful()).thenReturn(true);
        when(tokenHttpResponse.body()).thenReturn(null);

        tokenCallBackCaptor.getValue().onResponse(tokenHttpResponse);

        verify(loginView).showMainScreen();
    }

    @Test
    public void shouldShowCannotLoginWhenSomeErrorAndNoUser() {
        when(loginView.isConnected()).thenReturn(true);
        when(userRepository.findByUsername(anyString())).thenReturn(null);

        callLogin();
        verify(tokenService).getToken(matches(USERNAME), matches(PASSWORD), tokenCallBackCaptor.capture());
        tokenCallBackCaptor.getValue().onFailure(new Exception());

        verify(loginView).showCannotLoginError();
    }

    @Test
    public void shouldShowCannotLoginWhenSomeErrorAndBadPassword() {
        when(loginView.isConnected()).thenReturn(true);

        setUpBadPassword();

        callLogin();
        verify(tokenService).getToken(matches(USERNAME), matches(PASSWORD), tokenCallBackCaptor.capture());
        tokenCallBackCaptor.getValue().onFailure(new Exception());

        verify(loginView).showWrongPasswordError();
    }

    private void setUpBadPassword() {
        when(userRepository.findByUsername(anyString())).thenReturn(new User());
        loginPresenter.checkpw = false;
    }

    @Test
    public void shouldShowMainScreenWhenSomeErrorButUserAndPassOk() {
        when(loginView.isConnected()).thenReturn(true);

        setUpUserAndPasswordOk();

        callLogin();
        verify(tokenService).getToken(matches(USERNAME), matches(PASSWORD), tokenCallBackCaptor.capture());
        tokenCallBackCaptor.getValue().onFailure(new Exception());

        verify(loginView).showMainScreen();
    }
}