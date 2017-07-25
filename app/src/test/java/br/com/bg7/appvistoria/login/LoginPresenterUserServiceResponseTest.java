package br.com.bg7.appvistoria.login;

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
public class LoginPresenterUserServiceResponseTest extends LoginPresenterBaseTest {

    @Captor
    private ArgumentCaptor<HttpCallback<Token>> tokenCallBackCaptor;

    @Captor
    private ArgumentCaptor<HttpCallback<User>> userCallBackCaptor;

    @Mock
    private HttpResponse<Token> tokenHttpResponse;


    @Test
    public void shouldShowCannotLoginWhenNoSuccessAndNoUser() {
        when(loginView.isConnected()).thenReturn(true, false);
        when(userRepository.findByUsername(anyString())).thenReturn(null);
        when(tokenHttpResponse.isSuccessful()).thenReturn(true);
        when(tokenHttpResponse.body()).thenReturn(new Token());
        callLogin();

        verify(tokenService).getToken(matches(USERNAME), matches(PASSWORD), tokenCallBackCaptor.capture());
        tokenCallBackCaptor.getValue().onResponse(tokenHttpResponse);

        verify(loginView).showCannotLoginError();
    }


    @Test
    public void shouldMainScreenLoginWhenNoSuccessAndBadPassword() {
        when(loginView.isConnected()).thenReturn(true, false);
        when(userRepository.findByUsername(anyString())).thenReturn(new User());
        loginPresenter.checkpw = false;
        when(tokenHttpResponse.isSuccessful()).thenReturn(true);
        Token token = new Token();
        token.setExpiresIn(0);
        when(tokenHttpResponse.body()).thenReturn(token);

        callLogin();

        verify(tokenService).getToken(matches(USERNAME), matches(PASSWORD), tokenCallBackCaptor.capture());
        tokenCallBackCaptor.getValue().onResponse(tokenHttpResponse);

        verify(loginView).showMainScreen();
    }

}
