package br.com.bg7.appvistoria.login;

import org.junit.Test;

import br.com.bg7.appvistoria.data.source.local.UserRepository;

/**
 * Created by: luciolucio
 * Date: 2017-07-19
 */

public class LoginServiceInitializationTest extends LoginServiceBaseTest {
    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullTokenServiceWhenCreated() {
        new LoginService(null, userRepository, "", "");
    }

    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullUserRepositoryWhenCreated() {
        new LoginService(tokenService, null, "", "");
    }

    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullGrantTypeWhenCreated() {
        new LoginService(tokenService, userRepository, null, "");
    }

    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullClientIdWhenCreated() {
        new LoginService(tokenService, userRepository, "", null);
    }
}
