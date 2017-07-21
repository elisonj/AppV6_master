package br.com.bg7.appvistoria.login;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import br.com.bg7.appvistoria.data.source.remote.HttpCallback;
import br.com.bg7.appvistoria.data.source.remote.HttpResponse;
import br.com.bg7.appvistoria.data.source.remote.dto.Token;

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
    }

    @Test
    public void shouldShowCannotLoginWhenNoConnectionAndNoUser() {
        when(loginView.isConnected()).thenReturn(true);

        when(userRepository.findByUsername(anyString())).thenReturn(null);

        callLogin();
        verify(tokenService).getToken(matches(USERNAME), matches(PASSWORD), tokenCallBackCaptor.capture());
        when(tokenHttpResponse.isSuccessful()).thenReturn(false);
        tokenCallBackCaptor.getValue().onResponse(tokenHttpResponse);

        verify(loginView).showCannotLoginError();
    }
}
