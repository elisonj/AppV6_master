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
 *
 * Linha 5 da tabela
 * https://bg7.easyredmine.com/projects/185/wiki/Pode_falar_mais_sobre_a_tela_de_login
 */
public class LoginPresenterUserRepositoryFailureTest extends LoginPresenterBaseTest {

    @Before
    public void setUp() {
        super.setUp();

        when(userHttpResponse.isSuccessful()).thenReturn(false);
        doThrow(new RuntimeException("Error saving user"))
                .when(userRepository).save((User)any());
    }

    @Test
    public void shouldShowApplicationErrorWhenNoUser() {
        setUpNoUser();

        callLogin();

        invokeUserService();
        verify(loginView).showCriticalError();
    }

    @Test
    public void shouldShowApplicationErrorWhenBadPassword() {
        setUpBadPassword();

        callLogin();

        invokeUserService();
        verify(loginView).showCriticalError();
    }

    @Test
    public void shouldShowApplicationErrorWhenGoodPassword() {
        setUpGoodPassword();

        callLogin();

        invokeUserService();
        verify(loginView).showCriticalError();
    }
}
