package br.com.bg7.appvistoria.login;

import org.junit.Test;

import br.com.bg7.appvistoria.data.source.remote.dto.Token;
import br.com.bg7.appvistoria.vo.User;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by: elison
 * Date: 2017-07-25
 */
public class LoginPresenterUserRepositoryTest extends LoginPresenterBaseTest {

    @Test
    public void shouldShowMainScreenWhenSaveUser() {
        when(loginView.isConnected()).thenReturn(true);
        when(userRepository.findByUsername(anyString())).thenReturn(null);
        setUpToken();
        when(userHttpResponse.isSuccessful()).thenReturn(true);
        setUpUserResponse();

        callLogin();

        verifyTokenService();
        verifyUserService();
        verify(loginView).showMainScreen();
    }

    @Test
    public void shouldShowMainScreenWhenBadPassword() {
        when(loginView.isConnected()).thenReturn(true);
        setUpBadPassword();
        setUpToken();
        when(userHttpResponse.isSuccessful()).thenReturn(false);
        setUpUserResponse();

        callLogin();

        verifyTokenService();
        verifyUserService();
        verify(loginView).showMainScreen();
    }

    @Test
    public void shouldShowMainScreenWhenUserAndPasswordOK() {
        when(loginView.isConnected()).thenReturn(true);
        when(userRepository.findByUsername(anyString())).thenReturn(new User());
        loginPresenter.checkpw = true;
        setUpToken();
        when(userHttpResponse.isSuccessful()).thenReturn(false);
        setUpUserResponse();

        callLogin();

        verifyTokenService();
        verifyUserService();
        verify(loginView).showMainScreen();
    }

    protected void setUpToken() {
        when(tokenHttpResponse.isSuccessful()).thenReturn(true);
        Token token = new Token(TOKEN, USER_ID, 0);
        when(tokenHttpResponse.body()).thenReturn(token);
    }
}