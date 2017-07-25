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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.matches;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by: elison
 * Date: 2017-07-25
 */
public class LoginPresenterUserRepositoryFailureTest extends LoginPresenterBaseTest {

    @Captor
    private ArgumentCaptor<HttpCallback<Token>> tokenCallBackCaptor;

    @Captor
    private ArgumentCaptor<HttpCallback<UserResponse>> userCallBackCaptor;

    @Mock
    private HttpResponse<Token> tokenHttpResponse;

    @Mock
    private HttpResponse<UserResponse> userHttpResponse;


    @Test
    public void shouldShowApplicationErrorWhenRepositoryFails() {
        when(loginView.isConnected()).thenReturn(true);
        when(userRepository.findByUsername(anyString())).thenReturn(null);
        when(tokenHttpResponse.isSuccessful()).thenReturn(true);
        Token token = new Token(TOKEN, USER_ID);
        token.setExpiresIn(0);
        when(tokenHttpResponse.body()).thenReturn(token);
        when(userHttpResponse.isSuccessful()).thenReturn(true);
        UserResponse userResponse = new UserResponse();
        when(userHttpResponse.body()).thenReturn(userResponse);

        doThrow(new RuntimeException("Error saving user"))
                .when(userRepository).save((User)any());

        callLogin();

        verify(tokenService).getToken(matches(USERNAME), matches(PASSWORD), tokenCallBackCaptor.capture());
        tokenCallBackCaptor.getValue().onResponse(tokenHttpResponse);
        verify(userService).getUser(matches(TOKEN), matches(USER_ID), userCallBackCaptor.capture());
        userCallBackCaptor.getValue().onResponse(userHttpResponse);
        verify(loginView).showApplicationError();
    }


    @Test
    public void shouldShowApplicationErrorWhenRepositoryFailsAndBadPassword() {
        when(loginView.isConnected()).thenReturn(true);
        when(userRepository.findByUsername(anyString())).thenReturn(new User());
        loginPresenter.checkpw = false;
        when(tokenHttpResponse.isSuccessful()).thenReturn(true);
        Token token = new Token(TOKEN, USER_ID);
        token.setExpiresIn(0);
        when(tokenHttpResponse.body()).thenReturn(token);
        when(userHttpResponse.isSuccessful()).thenReturn(false);
        UserResponse userResponse = new UserResponse();
        when(userHttpResponse.body()).thenReturn(userResponse);

        doThrow(new RuntimeException("Error saving user"))
                .when(userRepository).save((User)any());

        callLogin();

        verify(tokenService).getToken(matches(USERNAME), matches(PASSWORD), tokenCallBackCaptor.capture());
        tokenCallBackCaptor.getValue().onResponse(tokenHttpResponse);
        verify(userService).getUser(matches(TOKEN), matches(USER_ID), userCallBackCaptor.capture());
        userCallBackCaptor.getValue().onResponse(userHttpResponse);
        verify(loginView).showApplicationError();
    }

    @Test
    public void shouldShowApplicationErrorWhenRepositoryFailsAndUserAndPasswordOK() {
        when(loginView.isConnected()).thenReturn(true);
        when(userRepository.findByUsername(anyString())).thenReturn(new User());
        loginPresenter.checkpw = true;
        when(tokenHttpResponse.isSuccessful()).thenReturn(true);
        Token token = new Token(TOKEN, USER_ID);
        token.setExpiresIn(0);
        when(tokenHttpResponse.body()).thenReturn(token);
        when(userHttpResponse.isSuccessful()).thenReturn(false);
        UserResponse userResponse = new UserResponse();
        when(userHttpResponse.body()).thenReturn(userResponse);

        doThrow(new RuntimeException("Error saving user"))
                .when(userRepository).save((User)any());

        callLogin();

        verify(tokenService).getToken(matches(USERNAME), matches(PASSWORD), tokenCallBackCaptor.capture());
        tokenCallBackCaptor.getValue().onResponse(tokenHttpResponse);
        verify(userService).getUser(matches(TOKEN), matches(USER_ID), userCallBackCaptor.capture());
        userCallBackCaptor.getValue().onResponse(userHttpResponse);
        verify(loginView).showApplicationError();
    }


}
