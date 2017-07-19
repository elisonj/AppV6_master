package br.com.bg7.appvistoria.login;

import org.junit.Test;

/**
 * Created by: luciolucio
 * Date: 2017-07-19
 */

public class LoginServiceTest extends LoginServiceBaseTest {

    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullTokenServiceWhenCreated() {
        new LoginService(null, userRepository);
    }

    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullUserRepositoryWhenCreated() {
        new LoginService(tokenService, null);
    }
}
