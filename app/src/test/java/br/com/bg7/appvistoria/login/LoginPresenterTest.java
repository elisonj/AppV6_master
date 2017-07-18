package br.com.bg7.appvistoria.login;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.bg7.appvistoria.data.UserRepository;
import br.com.bg7.appvistoria.service.LoginService;

import static org.mockito.Mockito.verify;

/**
 * Created by: luciolucio
 * Date: 2017-07-18
 */

public class LoginPresenterTest {
    @Mock
    private LoginContract.View loginView;

    @Mock
    private LoginService loginService;

    @Mock
    private UserRepository userRepository;

    private LoginPresenter loginPresenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        loginPresenter = new LoginPresenter(loginService, userRepository, loginView);
    }

    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullServiceWhenCreated() {
        new LoginPresenter(null, userRepository, loginView);
    }

    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullRepositoryWhenCreated() {
        new LoginPresenter(loginService, null, loginView);
    }

    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullViewWhenCreated() {
        new LoginPresenter(loginService, userRepository, null);
    }

    @Test
    public void shouldSetPresenterToViewWhenCreated() {
        verify(loginView).setPresenter(loginPresenter);
    }

    @Test
    public void shouldShowUserNameErrorWhenUserIsNull() {
        loginPresenter.login(null, "password");
        verify(loginView).showUsernameEmptyError();
    }

    @Test
    public void shouldShowUserNameErrorWhenUserIsEmpty() {
        loginPresenter.login("", "password");
        verify(loginView).showUsernameEmptyError();
    }

    @Test
    public void shouldShowUserNameErrorWhenUserIsBlank() {
        loginPresenter.login("   ", "password");
        verify(loginView).showUsernameEmptyError();
    }

    @Test
    public void shouldShowPasswordErrorWhenPasswordIsNull() {
        loginPresenter.login("user", null);
        verify(loginView).showPasswordEmptyError();
    }

    @Test
    public void shouldShowPasswordErrorWhenPasswordIsEmpty() {
        loginPresenter.login("user", "");
        verify(loginView).showPasswordEmptyError();
    }

    @Test
    public void shouldShowPasswordErrorWhenPasswordIsBlank() {
        loginPresenter.login("user", "  ");
        verify(loginView).showPasswordEmptyError();
    }
}
