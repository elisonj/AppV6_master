package br.com.bg7.appvistoria.login;

import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.bg7.appvistoria.data.source.TokenService;
import br.com.bg7.appvistoria.data.source.UserService;
import br.com.bg7.appvistoria.data.source.local.UserRepository;

/**
 * Created by: luciolucio
 * Date: 2017-07-19
 */

public class LoginPresenterBaseTest {
    @Mock
    protected LoginContract.View loginView;

    @Mock
    protected TokenService tokenService;

    @Mock
    protected UserService userService;

    @Mock
    protected UserRepository userRepository;

    TestableLoginPresenter loginPresenter;

    static final String USERNAME = "user";
    static final String PASSWORD = "password";

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        loginPresenter = new TestableLoginPresenter(tokenService, userService, userRepository, loginView);
    }

    void callLogin() {
        loginPresenter.login(USERNAME, PASSWORD);
    }
}
