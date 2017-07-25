package br.com.bg7.appvistoria.login;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import br.com.bg7.appvistoria.data.source.remote.HttpCallback;
import br.com.bg7.appvistoria.data.source.remote.HttpResponse;
import br.com.bg7.appvistoria.data.source.remote.dto.Token;
import br.com.bg7.appvistoria.vo.User;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.matches;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by: elison
 * Date: 2017-07-21
 */
public class LoginPresenterTokenServiceResponseFailureTest extends LoginPresenterBaseTest {

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
    public void shouldShowCannotLoginWhenNoSuccessAndNoUser() {

        when(userRepository.findByUsername(anyString())).thenReturn(null);
        when(tokenHttpResponse.isSuccessful()).thenReturn(false);

        callLogin();

        verify(tokenService).getToken(matches(USERNAME), matches(PASSWORD), tokenCallBackCaptor.capture());
        tokenCallBackCaptor.getValue().onResponse(tokenHttpResponse);
        verify(loginView).showCannotLoginError();
    }

    @Test
    public void shouldShowCannotLoginWhenNoSuccessAndBadPassword() {
        setUpBadPassword();
        when(tokenHttpResponse.isSuccessful()).thenReturn(false);

        callLogin();

        verify(tokenService).getToken(matches(USERNAME), matches(PASSWORD), tokenCallBackCaptor.capture());
        tokenCallBackCaptor.getValue().onResponse(tokenHttpResponse);
        verify(loginView).showWrongPasswordError();
    }

    @Test
    public void shouldShowMainScreenWhenNoSuccessButUserAndPassOk() {
        setUpUserAndPasswordOk();
        when(tokenHttpResponse.isSuccessful()).thenReturn(false);

        callLogin();

        verify(tokenService).getToken(matches(USERNAME), matches(PASSWORD), tokenCallBackCaptor.capture());
        tokenCallBackCaptor.getValue().onResponse(tokenHttpResponse);
        verify(loginView).showMainScreen();
    }

    private void setUpUserAndPasswordOk() {
        when(userRepository.findByUsername(anyString())).thenReturn(new User());
        loginPresenter.checkpw = true;
    }

    @Test
    public void shouldShowCannotLoginWhenNoBodyAndNoUser() {
        when(userRepository.findByUsername(anyString())).thenReturn(null);
        when(tokenHttpResponse.isSuccessful()).thenReturn(true);
        when(tokenHttpResponse.body()).thenReturn(null);

        callLogin();

        verify(tokenService).getToken(matches(USERNAME), matches(PASSWORD), tokenCallBackCaptor.capture());
        tokenCallBackCaptor.getValue().onResponse(tokenHttpResponse);
        verify(loginView).showCannotLoginError();
    }

    @Test
    public void shouldShowCannotLoginWhenNoBodyAndBadPassword() {
        setUpBadPassword();
        when(tokenHttpResponse.isSuccessful()).thenReturn(true);
        when(tokenHttpResponse.body()).thenReturn(null);

        callLogin();

        verify(tokenService).getToken(matches(USERNAME), matches(PASSWORD), tokenCallBackCaptor.capture());
        tokenCallBackCaptor.getValue().onResponse(tokenHttpResponse);
        verify(loginView).showWrongPasswordError();
    }

    @Test
    public void shouldShowMainScreenWhenNoBodyButUserAndPasswordOk() {
        setUpUserAndPasswordOk();
        when(tokenHttpResponse.isSuccessful()).thenReturn(true);
        when(tokenHttpResponse.body()).thenReturn(null);

        callLogin();

        verify(tokenService).getToken(matches(USERNAME), matches(PASSWORD), tokenCallBackCaptor.capture());
        tokenCallBackCaptor.getValue().onResponse(tokenHttpResponse);
        verify(loginView).showMainScreen();
    }
    @Test
    public void shouldShowCannotLoginWhenUnauthorizedAndNoUser() {
        when(userRepository.findByUsername(anyString())).thenReturn(null);
        when(tokenHttpResponse.isSuccessful()).thenReturn(false);
        when(tokenHttpResponse.code()).thenReturn(loginPresenter.UNAUTHORIZED_CODE);

        callLogin();

        verify(tokenService).getToken(matches(USERNAME), matches(PASSWORD), tokenCallBackCaptor.capture());
        tokenCallBackCaptor.getValue().onResponse(tokenHttpResponse);
        verify(loginView).showWrongPasswordError();
    }
    @Test
    public void shouldShowCannotLoginWhenUnauthorizedAndBadPassword() {
        setUpBadPassword();
        when(tokenHttpResponse.isSuccessful()).thenReturn(false);
        when(tokenHttpResponse.code()).thenReturn(loginPresenter.UNAUTHORIZED_CODE);

        callLogin();

        verify(tokenService).getToken(matches(USERNAME), matches(PASSWORD), tokenCallBackCaptor.capture());
        tokenCallBackCaptor.getValue().onResponse(tokenHttpResponse);
        verify(loginView).showWrongPasswordError();
    }

    private void setUpBadPassword() {
        when(userRepository.findByUsername(anyString())).thenReturn(new User());
        loginPresenter.checkpw = false;
    }

    @Test
    public void shouldShowCannotLoginWhenUnauthorizedButUserAndPasswordOk() {
        setUpUserAndPasswordOk();
        when(tokenHttpResponse.isSuccessful()).thenReturn(false);
        when(tokenHttpResponse.code()).thenReturn(loginPresenter.UNAUTHORIZED_CODE);

        callLogin();

        verify(tokenService).getToken(matches(USERNAME), matches(PASSWORD), tokenCallBackCaptor.capture());
        tokenCallBackCaptor.getValue().onResponse(tokenHttpResponse);
        verify(loginView).showWrongPasswordError();
    }
}
