package br.com.bg7.appvistoria.login;

import org.junit.Before;
import org.junit.Test;

import br.com.bg7.appvistoria.data.User;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by: luciolucio
 * Date: 2017-07-19
 */

public class LoginPresenterOfflineTest extends LoginPresenterBaseTest {

    @Override
    @Before
    public void setUp() {
        super.setUp();
        when(loginView.isConnected()).thenReturn(false);
        when(userRepository.findByUsername(USERNAME)).thenReturn(new User());
    }

    @Test
    public void shouldShowWrongPasswordWhenPasswordCheckFails() {
        loginPresenter.checkpw = false;

        callLogin();

        verify(loginView).showWrongPasswordError();
    }

    @Test
    public void shouldShowMainScreenWhenPasswordCheckIsSuccessfull() {
        loginPresenter.checkpw = true;

        callLogin();

        verify(loginView).showMainScreen();
    }
}
