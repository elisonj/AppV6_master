package br.com.bg7.appvistoria.login;

import org.junit.Before;
import org.junit.Test;

import br.com.bg7.appvistoria.data.User;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by: elison
 * Date: 2017-07-25
 *
 * Linha 6 da tabela
 * https://bg7.easyredmine.com/projects/185/wiki/Pode_falar_mais_sobre_a_tela_de_login
 */
public class LoginPresenterLoginSuccessTest extends LoginPresenterBaseTest {

    /**
     * 6.1 (a)
     */
    @Test
    public void shouldShowMainScreenWhenNoUser() {
        setUpNoUser();

        callLogin();

        invokeUserService();
        verify(loginView).showMainScreen();
    }

    /**
     * 6.1 (b)
     */
    @Test
    public void shouldSaveUserAndShowMainScreenWhenBadPassword() {
        setUpBadPassword();

        callLogin();

        invokeUserService();
        verify(loginView).showMainScreen();
    }

    /**
     * 6.1 (c)
     */
    @Test
    public void shouldSaveUserAndShowMainScreenWhenGoodPassword() {
        setUpGoodPassword();

        callLogin();

        invokeUserService();
        verify(loginView).showMainScreen();
    }
}