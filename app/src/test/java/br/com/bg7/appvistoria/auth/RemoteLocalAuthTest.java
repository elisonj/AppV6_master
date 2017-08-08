package br.com.bg7.appvistoria.auth;

import junit.framework.Assert;
import junit.framework.AssertionFailedError;

import org.junit.Before;
import org.junit.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import br.com.bg7.appvistoria.auth.callback.CheckCannotLoginCallback;
import br.com.bg7.appvistoria.auth.callback.EmptyAuthCallback;
import br.com.bg7.appvistoria.data.User;
import br.com.bg7.appvistoria.data.source.local.fake.FakeAuthRepository;
import br.com.bg7.appvistoria.data.source.local.fake.FakeUserRepository;
import br.com.bg7.appvistoria.data.source.remote.TokenService;
import br.com.bg7.appvistoria.data.source.remote.UserService;

/**
 * Created by: luciolucio
 * Date: 2017-08-07
 */

public class RemoteLocalAuthTest {
    private RemoteLocalAuth auth;

    @Mock
    private TokenService tokenService;

    @Mock
    private UserService userService;

    private String passwordHash = BCrypt.hashpw("arrumamalae", BCrypt.gensalt());

    private FakeUserRepository userRepository = new FakeUserRepository();

    private FakeAuthRepository authRepository = new FakeAuthRepository();

    @Before
    public void setUp () {
        MockitoAnnotations.initMocks(this);

        auth = new RemoteLocalAuth(userService, tokenService, userRepository, authRepository);
    }

    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullUserServiceWhenCreated() {
        new RemoteLocalAuth(null, tokenService, userRepository, authRepository);
    }

    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullTokenServiceWhenCreated() {
        new RemoteLocalAuth(userService, null, userRepository, authRepository);
    }

    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullUserRepositoryWhenCreated() {
        new RemoteLocalAuth(userService, tokenService, null, authRepository);
    }

    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullAuthRepositoryWhenCreated() {
        new RemoteLocalAuth(userService, tokenService, userRepository, null);
    }

    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullUsernameOnAttempt() {
        auth.attempt(null, "", false, new EmptyAuthCallback());
    }

    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullPasswordOnAttempt() {
        auth.attempt("", null, false, new EmptyAuthCallback());
    }

    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullCallbackOnAttempt() {
        auth.attempt("", "", false, null);
    }

    @Test
    public void shouldNotCheckIfNoUserInRepository() {
        Assert.assertFalse(auth.check());
    }

    @Test
    public void shouldReturnNullUserIfNoUserInRepository() {
        Assert.assertNull(auth.user());
    }

    @Test
    public void shouldReturnNullTokenIfNoUserInRepository() {
        Assert.assertNull(auth.token());
    }

    @Test
    public void shouldCheckIfUserInRepository() throws IOException {
        authRepository.save(makeUser("Wesley"), "Tolkien");
        Assert.assertTrue(auth.check());
    }

    @Test
    public void shouldReturnUsernameIfUserInRepository() throws IOException {
        authRepository.save(makeUser("tiberio"), "qqToken");
        Assert.assertEquals("tiberio", auth.user().getUsername());
    }

    @Test
    public void shouldReturnTokenIfUserInRepository() throws IOException {
        authRepository.save(makeUser("kirk"), "mmToken");
        Assert.assertEquals("mmToken", auth.token());
    }

    @Test
    public void shouldNotCheckOrHaveUserOrTokenIfAuthFails() {
        auth.attempt("", "", false, new EmptyAuthCallback());

        Assert.assertFalse(auth.check());
        Assert.assertNull(auth.user());
        Assert.assertNull(auth.token());
    }

    @Test
    public void shouldCheckAndHaveUserAndTokenIfAuthPasses() {
        userRepository.save(makeUser("unb", "token"));

        attemptAuth();

        Assert.assertTrue(auth.check());
        Assert.assertEquals("unb", auth.user().getUsername());
        Assert.assertEquals("token", auth.token());
    }

    @Test
    public void shouldLogout() {
        userRepository.save(makeUser("unb", "token"));

        attemptAuth();
        auth.logout();

        Assert.assertFalse(auth.check());
        Assert.assertNull(auth.user());
        Assert.assertNull(auth.token());
    }

    /**
     * This test is kind of peculiar in that is uses a real callback to assert that
     * the callback has been called. Under CheckCannotLoginCallback, once onCannotLogin
     * gets invoked, it throws an AssertionFailedError that makes this test pass
     * because of the declared expected exception
     */
    @Test(expected = AssertionFailedError.class)
    public void shouldNotLoginWhenAuthRepoFails() {
        userRepository.save(makeUser("unb", "123PIM 456PIM"));
        auth.attempt("unb", "arrumamalae", false, new CheckCannotLoginCallback());

        attemptAuth();
    }

    private void attemptAuth() {
        auth.attempt("unb", "arrumamalae", false, new EmptyAuthCallback());
    }

    private User makeUser(String username) {
        return makeUser(username, "token");
    }

    private User makeUser(String username, String token) {
        return new User(username, token, passwordHash);
    }
}
