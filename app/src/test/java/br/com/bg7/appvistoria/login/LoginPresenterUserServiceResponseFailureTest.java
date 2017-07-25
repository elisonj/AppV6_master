package br.com.bg7.appvistoria.login;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import br.com.bg7.appvistoria.data.source.remote.HttpCallback;
import br.com.bg7.appvistoria.data.source.remote.HttpResponse;
import br.com.bg7.appvistoria.data.source.remote.dto.Token;
import br.com.bg7.appvistoria.data.source.remote.dto.UserResponse;
import br.com.bg7.appvistoria.vo.User;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.matches;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by: elison
 * Date: 2017-07-25
 */
public class LoginPresenterUserServiceResponseFailureTest extends LoginPresenterBaseTest {

    @Captor
    private ArgumentCaptor<HttpCallback<Token>> tokenCallBackCaptor;

    @Captor
    private ArgumentCaptor<HttpCallback<UserResponse>> userCallBackCaptor;

    @Mock
    private HttpResponse<Token> tokenHttpResponse;

    @Mock
    private HttpResponse<UserResponse> userHttpResponse;

    @Test
    public void shouldShowCannotLoginWhenUserDoNotExist() {
        when(loginView.isConnected()).thenReturn(true);
        when(userRepository.findByUsername(anyString())).thenReturn(null);
        when(tokenHttpResponse.isSuccessful()).thenReturn(true);
        Token token = new Token(TOKEN, USER_ID);
        token.setExpiresIn(0);
        when(tokenHttpResponse.body()).thenReturn(token);
        when(userHttpResponse.isSuccessful()).thenReturn(true);
        UserResponse userResponse = new UserResponse();
        when(userHttpResponse.body()).thenReturn(userResponse);

        callLogin();

        verify(tokenService).getToken(matches(USERNAME), matches(PASSWORD), tokenCallBackCaptor.capture());
        tokenCallBackCaptor.getValue().onResponse(tokenHttpResponse);
        verify(userService).getUser(matches(TOKEN), matches(USER_ID), userCallBackCaptor.capture());
        userCallBackCaptor.getValue().onFailure(new Exception());
        verify(loginView).showCannotLoginError();
    }

    @Test
    public void shouldShowMainScreenWhenNoSuccessAndBadPassword() {
        when(loginView.isConnected()).thenReturn(true);
        when(userRepository.findByUsername(anyString())).thenReturn(new User());
        loginPresenter.checkpw = false;
        when(tokenHttpResponse.isSuccessful()).thenReturn(true);
        Token token = new Token(TOKEN, USER_ID);
        token.setExpiresIn(0);
        when(tokenHttpResponse.body()).thenReturn(token);
        when(userHttpResponse.isSuccessful()).thenReturn(true);
        UserResponse userResponse = new UserResponse();
        when(userHttpResponse.body()).thenReturn(userResponse);

        callLogin();

        verify(tokenService).getToken(matches(USERNAME), matches(PASSWORD), tokenCallBackCaptor.capture());
        tokenCallBackCaptor.getValue().onResponse(tokenHttpResponse);
        verify(userService).getUser(matches(TOKEN), matches(USER_ID), userCallBackCaptor.capture());
        userCallBackCaptor.getValue().onFailure(new Exception());
        verify(loginView).showMainScreen();
    }


}
