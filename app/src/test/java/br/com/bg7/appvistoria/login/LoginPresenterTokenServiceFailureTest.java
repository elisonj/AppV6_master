package br.com.bg7.appvistoria.login;

import org.junit.Test;

import br.com.bg7.appvistoria.vo.User;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by: luciolucio
 * Date: 2017-07-21
 */

public class LoginPresenterTokenServiceFailureTest extends LoginPresenterBaseTest {
    @Test
    public void shouldShowCannotLoginWhenNoConnectionAndNoUser() {
        when(loginView.isConnected()).thenReturn(false);
        when(userRepository.findByUsername(anyString())).thenReturn(null);

        callLogin();

        verify(loginView).showCannotLoginError();
    }

    @Test
    public void showBadCredentialsWhenNoConnectionAndBadPassword() {
        when(loginView.isConnected()).thenReturn(false);
        when(userRepository.findByUsername(anyString())).thenReturn(new User());
        loginPresenter.checkpw = false;

        callLogin();

        verify(loginView).showWrongPasswordError();
    }
}
