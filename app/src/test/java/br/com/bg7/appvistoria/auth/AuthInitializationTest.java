package br.com.bg7.appvistoria.auth;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.bg7.appvistoria.data.source.local.fake.FakeUserRepository;
import br.com.bg7.appvistoria.data.source.remote.TokenService;
import br.com.bg7.appvistoria.data.source.remote.UserService;

/**
 * Created by: luciolucio
 * Date: 2017-08-07
 */

public class AuthInitializationTest {
    @Mock
    TokenService tokenService;

    @Mock
    UserService userService;

    FakeUserRepository userRepository;

    @Before
    public void setUp () {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullUserServiceWhenCreated() {
        new Auth(null, tokenService, userRepository);
    }

    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullTokenServiceWhenCreated() {
        new Auth(userService, null, userRepository);
    }

    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullUserRepositoryWhenCreated() {
        new Auth(userService, tokenService, null);
    }
}
