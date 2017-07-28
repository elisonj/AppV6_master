package br.com.bg7.appvistoria.login;

import org.junit.Before;
import org.junit.Test;

import br.com.bg7.appvistoria.data.User;
import br.com.bg7.appvistoria.data.source.remote.dto.Token;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by: elison
 * Date: 2017-07-25
 */
public class LoginPresenterUserRepositoryFailureTest extends LoginPresenterBaseTest {

    @Before
    public void setUp() {
        super.setUp();
        when(loginView.isConnected()).thenReturn(true);
        when(tokenHttpResponse.isSuccessful()).thenReturn(true);
        Token token = new Token(TOKEN, USER_ID);
        when(tokenHttpResponse.body()).thenReturn(token);
    }

    @Test
    public void shouldShowApplicationErrorWhenRepositoryFails() {
        setUpNoUser();
        when(userHttpResponse.isSuccessful()).thenReturn(true);
        doThrow(new RuntimeException("Error saving user"))
                .when(userRepository).save((User)any());

        callLogin();

        invokeTokenService();
        invokeUserService();
        verify(loginView).showCriticalError();
    }

    @Test
    public void shouldShowApplicationErrorWhenRepositoryFailsAndBadPassword() {
        setUpBadPassword();
        when(userHttpResponse.isSuccessful()).thenReturn(false);
        doThrow(new RuntimeException("Error saving user"))
                .when(userRepository).save((User)any());

        callLogin();

        invokeTokenService();
        invokeUserService();
        verify(loginView).showCriticalError();
    }

    @Test
    public void shouldShowApplicationErrorWhenRepositoryFailsAndUserAndPasswordOK() {
        when(userRepository.findByUsername(anyString())).thenReturn(new User());
        loginPresenter.checkpw = true;
        when(userHttpResponse.isSuccessful()).thenReturn(false);
        doThrow(new RuntimeException("Error saving user"))
                .when(userRepository).save((User)any());

        callLogin();

        invokeTokenService();
        invokeUserService();
        verify(loginView).showCriticalError();
    }
}
