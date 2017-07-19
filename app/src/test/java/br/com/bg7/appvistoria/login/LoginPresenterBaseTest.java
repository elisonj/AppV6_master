package br.com.bg7.appvistoria.login;

import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.bg7.appvistoria.data.UserRepository;
import br.com.bg7.appvistoria.service.LoginService;

/**
 * Created by: luciolucio
 * Date: 2017-07-19
 */

public class LoginPresenterBaseTest {
    @Mock
    protected LoginContract.View loginView;

    @Mock
    protected LoginService loginService;

    @Mock
    protected UserRepository userRepository;

    TestableLoginPresenter loginPresenter;

    static final String USERNAME = "user";
    static final String PASSWORD = "password";

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        loginPresenter = new TestableLoginPresenter(loginService, userRepository, loginView);
    }
}
