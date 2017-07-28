package br.com.bg7.appvistoria.login;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import br.com.bg7.appvistoria.data.User;
import br.com.bg7.appvistoria.data.source.remote.dto.Token;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.matches;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by: elison
 * Date: 2017-07-21
 */
public class LoginPresenterUserServiceFailureTest extends LoginPresenterBaseTest {

    @Before
    public void setUp() {
        super.setUp();
        when(userHttpResponse.isSuccessful()).thenReturn(false);
    }

    @Test
    public void shouldShowCannotLoginWhenNoSuccessAndNoUser() {
        setUpNoConnection();
        setUpNoUser();

        callLogin();

        invokeTokenService();
        verify(loginView).showCannotLoginError();
    }

    @Test
    public void shouldMainScreenLoginWhenNoSuccessAndBadPassword() {
        setUpNoConnection();
        setUpBadPassword();

        callLogin();

        invokeTokenService();
        verify(userRepository).save((User)any());
        verify(loginView).showMainScreen();
    }

    @Test
    public void shouldMainScreenLoginWhenNoSuccessButUserAndPassOk() {
        setUpNoConnection();
        setUpGoodPassword();

        callLogin();

        invokeTokenService();
        verify(userRepository).save((User)any());
        verify(loginView).showMainScreen();
    }

    @Test
    public void shouldShowCannotLoginWhenConnectivityErrorAndNoUser() {
        setUpNoUser();

        callLogin();

        invokeTokenService();
        verify(userService).getUser(matches(TOKEN), matches(USER_ID), userCallBackCaptor.capture());
        userCallBackCaptor.getValue().onFailure(new IOException());
        verify(loginView).showCannotLoginError();
    }

    @Test
    public void shouldShowMainScreenLoginWhenConnectivityErrorAndBadPassword() {
        setUpBadPassword();

        callLogin();

        invokeTokenService();
        verify(userService).getUser(matches(TOKEN), matches(USER_ID), userCallBackCaptor.capture());
        userCallBackCaptor.getValue().onFailure(new IOException());
        verify(userRepository).save((User)any());
        verify(loginView).showMainScreen();
    }

    @Test
    public void shouldShowMainScreenLoginWhenConnectivityErrorButUserAndPassOk() {
        setUpGoodPassword();

        callLogin();

        invokeTokenService();
        verify(userService).getUser(matches(TOKEN), matches(USER_ID), userCallBackCaptor.capture());
        userCallBackCaptor.getValue().onFailure(new IOException());
        verify(userRepository).save((User)any());
        verify(loginView).showMainScreen();
    }

    @Test
    public void shouldShowCannotLoginWhenOtherErrorAndNoUser() {
        setUpNoUser();

        callLogin();

        invokeTokenService();
        verify(userService).getUser(matches(TOKEN), matches(USER_ID), userCallBackCaptor.capture());
        userCallBackCaptor.getValue().onFailure(new RuntimeException());
        verify(loginView).showCannotLoginError();
    }

    @Test
    public void shouldShowMainScreenLoginWhenOtherErrorAndBadPassword() {
        setUpBadPassword();

        callLogin();

        invokeTokenService();
        verify(userService).getUser(matches(TOKEN), matches(USER_ID), userCallBackCaptor.capture());
        userCallBackCaptor.getValue().onFailure(new RuntimeException());
        verify(userRepository).save((User)any());
        verify(loginView).showMainScreen();
    }

    @Test
    public void shouldShowMainScreenLoginWhenOtherErrorButUserAndPassOk() {
        setUpGoodPassword();

        callLogin();

        invokeTokenService();
        verify(userService).getUser(matches(TOKEN), matches(USER_ID), userCallBackCaptor.capture());
        userCallBackCaptor.getValue().onFailure(new RuntimeException());
        verify(userRepository).save((User)any());
        verify(loginView).showMainScreen();
    }

    /**
     * The TokenService call checks isConnected() before this one, so we need to return
     * true to that first, then return false so that UserService will think there is no
     * connection now
     */
    void setUpNoConnection() {
        when(loginView.isConnected()).thenReturn(true, false);
    }
}
