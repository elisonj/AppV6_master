package br.com.bg7.appvistoria.login;

import org.junit.Test;

import br.com.bg7.appvistoria.data.User;

import static org.mockito.Mockito.verify;

/**
 * Created by: elison
 * Date: 2017-07-25
 *
 * Linha 5 da tabela
 * https://bg7.easyredmine.com/projects/185/wiki/Pode_falar_mais_sobre_a_tela_de_login
 */
public class LoginPresenterLoginSuccessTest extends LoginPresenterBaseTest {

    /**
     * 5.1 (a)
     */
    @Test
    public void shouldSaveAllUserDataAndShowMainScreenWhenNoUser() {
        setUpNoUser();

        callLogin();

        invokeUserService();
        verifySaveAllUserDataAndEnter();
    }

    /**
     * 5.1 (b)
     */
    @Test
    public void shouldSaveAllUserDataAndShowMainScreenWhenBadPassword() {
        setUpBadPassword();

        callLogin();

        invokeUserService();
        verifyClearUserData();
        verifySaveAllUserDataAndEnter();
    }

    /**
     * 5.1 (c)
     */
    @Test
    public void shouldSaveAllUserDataAndShowMainScreenWhenGoodPassword() {
        setUpGoodPassword();

        callLogin();

        invokeUserService();
        verifyClearUserData();
        verifySaveAllUserDataAndEnter();
    }

    private void verifyClearUserData() {
        verify(userRepository).deleteAll(User.class);
    }
}
