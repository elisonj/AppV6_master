package br.com.bg7.appvistoria.login;

import org.junit.Test;

/**
 * Created by: elison
 * Date: 2017-07-25
 *
 * Linha 5 da tabela
 * https://bg7.easyredmine.com/projects/185/wiki/Pode_falar_mais_sobre_a_tela_de_login
 */
public class LoginPresenterLoginSuccessTest extends LoginPresenterTestBase {

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
        verifySaveAllUserDataAndEnter();
    }

    @Test
    public void shouldCreateConfigWhenFirstLogin() {
        clearConfigRepository();
        setUpGoodPassword();

        callLogin();

        invokeUserService();
        verifySaveDefaultConfig();
    }

    @Test
    public void shouldLoginWithDefaultConfig() {
        setUpGoodPassword();

        callLogin();

        invokeUserService();
        verifySaveDefaultConfig();
    }

    @Test
    public void shouldLoginWithNotDefaultConfig() {
        clearConfigRepository();
        setUpConfig("en_US");

        setUpGoodPassword();

        callLogin();

        invokeUserService();
        verifyNotDefaultConfig();
    }

}
