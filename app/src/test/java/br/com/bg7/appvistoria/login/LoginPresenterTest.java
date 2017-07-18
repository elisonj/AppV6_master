package br.com.bg7.appvistoria.login;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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

    private LoginPresenter loginPresenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullServiceWhenCreated() {
        loginPresenter = new LoginPresenter(null, loginView);
    }

    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullViewWhenCreated() {
        loginPresenter = new LoginPresenter(loginService, null);
    }

    @Test
    public void shouldSetPresenterToViewWhenCreated() {
        loginPresenter = new LoginPresenter(loginService, loginView);
        verify(loginView).setPresenter(loginPresenter);
    }
}
