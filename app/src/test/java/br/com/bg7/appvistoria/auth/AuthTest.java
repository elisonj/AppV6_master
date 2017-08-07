package br.com.bg7.appvistoria.auth;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.bg7.appvistoria.data.source.local.fake.FakeAuthRepository;
import br.com.bg7.appvistoria.data.source.local.fake.FakeUserRepository;
import br.com.bg7.appvistoria.data.source.remote.TokenService;
import br.com.bg7.appvistoria.data.source.remote.UserService;

/**
 * Created by: luciolucio
 * Date: 2017-08-07
 */

public class AuthTest {
    private Auth auth;

    @Mock
    private TokenService tokenService;

    @Mock
    private UserService userService;

    private FakeUserRepository userRepository = new FakeUserRepository();

    private FakeAuthRepository authRepository = new FakeAuthRepository();

    @Before
    public void setUp () {
        MockitoAnnotations.initMocks(this);

        auth = new Auth(userService, tokenService, userRepository, authRepository);
    }

    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullUserServiceWhenCreated() {
        new Auth(null, tokenService, userRepository, authRepository);
    }

    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullTokenServiceWhenCreated() {
        new Auth(userService, null, userRepository, authRepository);
    }

    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullUserRepositoryWhenCreated() {
        new Auth(userService, tokenService, null, authRepository);
    }

    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullAuthRepositoryWhenCreated() {
        new Auth(userService, tokenService, userRepository, null);
    }

    @Test
    public void shouldNotCheckIfNoUserInRepository() {
        Assert.assertFalse(auth.check());
    }

    @Test
    public void shouldCheckIfUserInRepository() {
        authRepository.save("Wesley");
        Assert.assertTrue(auth.check());
    }

    @Test
    public void shouldReturnNullIfNoUserInRepository() {
        Assert.assertNull(auth.user());
    }

    @Test
    public void shouldReturnUsernameIfUserInRepository() {
        authRepository.save("tiberio");
        Assert.assertEquals("tiberio", auth.user());
    }
}
