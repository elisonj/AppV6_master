package br.com.bg7.appvistoria.login;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import java.net.ConnectException;
import java.util.concurrent.TimeoutException;

import br.com.bg7.appvistoria.data.source.remote.HttpCallback;
import br.com.bg7.appvistoria.data.source.remote.HttpResponse;
import br.com.bg7.appvistoria.data.source.remote.dto.Token;

import static org.mockito.ArgumentMatchers.matches;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by: luciolucio
 * Date: 2017-07-19
 */

public class LoginPresenterOnlineTest extends LoginPresenterBaseTest {

    @Captor
    private ArgumentCaptor<HttpCallback<Token>> loginCallbackCaptor;

    @Mock
    HttpResponse<Token> tokenHttpResponse;

    @Override
    @Before
    public void setUp() {
        super.setUp();
        when(loginView.isConnected()).thenReturn(true);
    }

    @Test
    public void shouldShowOfflineLoginErrorWhenConnectionTimesOut() {
        callLogin();

        verify(tokenService).getToken(matches(USERNAME), matches(PASSWORD), loginCallbackCaptor.capture());
        loginCallbackCaptor.getValue().onFailure(null, new TimeoutException());

        verify(loginView).showCannotLoginOfflineError();
    }

    @Test
    public void shouldShowOfflineLoginErrorWhenConnectionFails() {
        callLogin();

        verify(tokenService).getToken(matches(USERNAME), matches(PASSWORD), loginCallbackCaptor.capture());
        loginCallbackCaptor.getValue().onFailure(null, new ConnectException());

        verify(loginView).showCannotLoginOfflineError();
    }

    @Test
    public void shouldShowLoginErrorWhenAnotherFailureHappens() {
        callLogin();

        verify(tokenService).getToken(matches(USERNAME), matches(PASSWORD), loginCallbackCaptor.capture());
        loginCallbackCaptor.getValue().onFailure(null, new Exception());

        verify(loginView).showCannotLoginError();
    }
}
