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
    public void shouldShowCannotLoginWhenNoConnectionAndNoUser() {
        setUpNoConnection();
        setUpNoUser();

        callLogin();

        invokeTokenService(); // TokenService only because there is no connection
        verify(loginView).showCannotLoginError();
    }

    @Test
    public void shouldMainScreenLoginWhenNoConnectionAndBadPassword() {
        setUpNoConnection();
        setUpBadPassword();

        callLogin();

        invokeTokenService(); // TokenService only because there is no connection
        verifyThatUserGetsSavedAndShowsMainScreen();
    }

    @Test
    public void shouldMainScreenLoginWhenNoConnectionAndGoodPassword() {
        setUpNoConnection();
        setUpGoodPassword();

        callLogin();

        invokeTokenService(); // TokenService only because there is no connection
        verifyThatUserGetsSavedAndShowsMainScreen();
    }

    @Test
    public void shouldShowCannotLoginWhenConnectivityErrorAndNoUser() {
        setUpNoUser();

        callLogin();

        invokeTokenService();
        invokeIOException();
        verify(loginView).showCannotLoginError();
    }

    @Test
    public void shouldShowMainScreenLoginWhenConnectivityErrorAndBadPassword() {
        setUpBadPassword();

        callLogin();

        invokeTokenService();
        invokeIOException();
        verifyThatUserGetsSavedAndShowsMainScreen();
    }

    @Test
    public void shouldShowMainScreenLoginWhenConnectivityErrorAndGoodPassword() {
        setUpGoodPassword();

        callLogin();

        invokeTokenService();
        invokeIOException();
        verifyThatUserGetsSavedAndShowsMainScreen();
    }

    @Test
    public void shouldShowCannotLoginWhenOtherErrorAndNoUser() {
        setUpNoUser();

        callLogin();

        invokeTokenService();
        invokeRuntimeException();
        verify(loginView).showCannotLoginError();
    }

    @Test
    public void shouldShowMainScreenLoginWhenOtherErrorAndBadPassword() {
        setUpBadPassword();

        callLogin();

        invokeTokenService();
        invokeRuntimeException();
        verifyThatUserGetsSavedAndShowsMainScreen();
    }

    @Test
    public void shouldShowMainScreenLoginWhenOtherErrorAndGoodPassword() {
        setUpGoodPassword();

        callLogin();

        invokeTokenService();
        invokeRuntimeException();
        verifyThatUserGetsSavedAndShowsMainScreen();
    }

    /**
     * The TokenService call checks isConnected() before this one, so we need to return
     * true to that first, then return false so that UserService will think there is no
     * connection now
     */
    void setUpNoConnection() {
        when(loginView.isConnected()).thenReturn(true, false);
    }

    private void verifyThatUserGetsSavedAndShowsMainScreen() {
        verify(userRepository).save((User)any());
        verify(loginView).showMainScreen();
    }

    private void invokeIOException() {
        invokeException(new IOException());
    }


    private void invokeRuntimeException() {
        invokeException(new RuntimeException());
    }

    private void invokeException(Throwable t) {
        verify(userService).getUser(matches(TOKEN), matches(USER_ID), userCallBackCaptor.capture());
        userCallBackCaptor.getValue().onFailure(t);
    }
}
