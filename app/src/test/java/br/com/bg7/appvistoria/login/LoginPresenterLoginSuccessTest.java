package br.com.bg7.appvistoria.login;

import junit.framework.Assert;

import org.junit.Test;

import java.io.IOException;

import br.com.bg7.appvistoria.auth.Auth;
import br.com.bg7.appvistoria.data.Config;

import static org.mockito.Mockito.reset;

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
    public void shouldCreateConfigOnFirstLogin() {
        setUpGoodPassword();

        callLogin();

        invokeUserService();

        Config config = configRepository.findByUser(Auth.user());
        Assert.assertEquals("pt", config.getLanguageName());
    }

    @Test
    public void shouldNotChangeExistingConfigWhenLoggingIn() throws IOException {
        setUpGoodPassword();
        callLogin();
        invokeUserService();

        configRepository.save(new Config("en", Auth.user()));

        reset(tokenService);
        reset(userService);
        callLogin();
        invokeUserService();

        Config config = configRepository.findByUser(Auth.user());
        Assert.assertEquals("en", config.getLanguageName());
    }
}
