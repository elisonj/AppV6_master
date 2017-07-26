package br.com.bg7.appvistoria.login;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import br.com.bg7.appvistoria.data.source.remote.HttpCallback;
import br.com.bg7.appvistoria.data.source.remote.HttpResponse;
import br.com.bg7.appvistoria.data.source.remote.dto.Token;
import br.com.bg7.appvistoria.data.source.remote.dto.UserResponse;

import static org.mockito.ArgumentMatchers.matches;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by: elison
 * Date: 2017-07-21
 */
public class LoginPresenterUserServiceResponseTest extends LoginPresenterBaseTest {

    @Captor
    private ArgumentCaptor<HttpCallback<UserResponse>> userCallBackCaptor;

    @Mock
    private HttpResponse<UserResponse> userHttpResponse;

    @Before
    public void setUp() {
        super.setUp();
        when(loginView.isConnected()).thenReturn(true);
    }

    @Test
    public void shouldShowCannotLoginWhenNoSuccessAndNoUser() {
        when(loginView.isConnected()).thenReturn(true, false);
        setUpNullUser();
        when(tokenHttpResponse.isSuccessful()).thenReturn(true);
        when(tokenHttpResponse.body()).thenReturn(new Token());

        callLogin();

        verifyTokenService();
        verify(loginView).showCannotLoginError();
    }

    @Test
    public void shouldMainScreenLoginWhenNoSuccessAndBadPassword() {
        when(loginView.isConnected()).thenReturn(true, false);
        setUpBadPassword();
        when(tokenHttpResponse.isSuccessful()).thenReturn(true);
        setUpToken();

        callLogin();

        verifyTokenService();
        verify(loginView).showMainScreen();
    }

    @Test
    public void shouldMainScreenLoginWhenNoSuccessButUserAndPassOk() {
        when(loginView.isConnected()).thenReturn(true, false);
        setUpUserAndPasswordOk();
        when(tokenHttpResponse.isSuccessful()).thenReturn(true);
        setUpToken();

        callLogin();

        verifyTokenService();
        verify(loginView).showMainScreen();
    }

    @Test
    public void shouldShowCannotLoginWhenAnyErrorAndNoUser() {
        setUpNullUser();
        when(tokenHttpResponse.isSuccessful()).thenReturn(true);
        Token token = new Token(TOKEN, USER_ID, 0);
        when(tokenHttpResponse.body()).thenReturn(token);
        when(userHttpResponse.isSuccessful()).thenReturn(false);
        when(userHttpResponse.body()).thenReturn(null);

        callLogin();

        verifyTokenService();
        verify(userService).getUser(matches(TOKEN), matches(USER_ID), userCallBackCaptor.capture());
        userCallBackCaptor.getValue().onFailure(new Exception());
        verify(loginView).showCannotLoginError();
    }

    @Test
    public void shouldShowMainScreenLoginWhenAnyErrorAndBadPassword() {
        setUpBadPassword();
        when(tokenHttpResponse.isSuccessful()).thenReturn(true);
        Token token = new Token(TOKEN, USER_ID, 0);
        when(tokenHttpResponse.body()).thenReturn(token);
        when(userHttpResponse.isSuccessful()).thenReturn(false);
        when(userHttpResponse.body()).thenReturn(null);

        callLogin();

        verifyTokenService();
        verify(userService).getUser(matches(TOKEN), matches(USER_ID), userCallBackCaptor.capture());
        userCallBackCaptor.getValue().onFailure(new Exception());
        verify(loginView).showMainScreen();
    }


    @Test
    public void shouldShowMainScreenLoginWhenAnyErrorButUserAndPassOk() {
        setUpUserAndPasswordOk();
        when(tokenHttpResponse.isSuccessful()).thenReturn(true);
        Token token = new Token(TOKEN, USER_ID, 0);
        when(tokenHttpResponse.body()).thenReturn(token);
        when(userHttpResponse.isSuccessful()).thenReturn(false);
        when(userHttpResponse.body()).thenReturn(null);

        callLogin();

        verifyTokenService();
        verify(userService).getUser(matches(TOKEN), matches(USER_ID), userCallBackCaptor.capture());
        userCallBackCaptor.getValue().onFailure(new Exception());
        verify(loginView).showMainScreen();
    }
}
