package br.com.bg7.appvistoria.login;

import org.junit.Before;
import org.junit.BeforeClass;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.bg7.appvistoria.data.User;
import br.com.bg7.appvistoria.data.source.TokenService;
import br.com.bg7.appvistoria.data.source.UserService;
import br.com.bg7.appvistoria.data.source.local.fake.FakeUserRepository;
import br.com.bg7.appvistoria.data.source.remote.HttpCallback;
import br.com.bg7.appvistoria.data.source.remote.HttpResponse;
import br.com.bg7.appvistoria.data.source.remote.dto.Token;
import br.com.bg7.appvistoria.data.source.remote.dto.UserResponse;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.matches;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by: luciolucio
 * Date: 2017-07-19
 */

public class LoginPresenterBaseTest {
    @Mock
    LoginContract.View loginView;

    @Mock
    TokenService tokenService;

    @Mock
    UserService userService;

    FakeUserRepository userRepository;

    @Mock
    HttpResponse<Token> tokenHttpResponse;

    @Mock
    HttpResponse<UserResponse> userHttpResponse;

    @Captor
    ArgumentCaptor<HttpCallback<Token>> tokenCallBackCaptor;

    @Captor
    ArgumentCaptor<HttpCallback<UserResponse>> userCallBackCaptor;

    LoginPresenter loginPresenter;

    static final String USERNAME = "user";
    static final String PASSWORD = "password";
    static final String HASHED_PASSWORD;
    static final String WRONG_PASSWORD = "not-the-password";
    static final String TOKEN = "token";
    static final String USER_ID = "user_id";

    String password = PASSWORD;

    static {
        HASHED_PASSWORD = BCrypt.hashpw(PASSWORD, BCrypt.gensalt());
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        when(loginView.isConnected()).thenReturn(true);

        when(tokenHttpResponse.isSuccessful()).thenReturn(true);
        when(tokenHttpResponse.body()).thenReturn(new Token(TOKEN, USER_ID));

        when(userHttpResponse.isSuccessful()).thenReturn(true);
        when(userHttpResponse.body()).thenReturn(new UserResponse());

        userRepository = new FakeUserRepository();
        userRepository.deleteAll(User.class);

        loginPresenter = new LoginPresenter(tokenService, userService, userRepository, loginView);

        userRepository.save(new User(USERNAME, TOKEN, HASHED_PASSWORD));
    }

    void callLogin() {
        loginPresenter.login(USERNAME, password);
    }

    void setUpBadPassword() {
        password = WRONG_PASSWORD;
    }

    void setUpGoodPassword() {
        password = PASSWORD;
    }

    void setUpNoConnection() {
        when(loginView.isConnected()).thenReturn(false);
    }

    void setUpNoUser() {
        userRepository.deleteAll(User.class);
    }

    void verifySaveTokenAndPasswordAndShowMainScreen() {
        // TODO: Realmente verificar o salvamento de token e password
        verifySaveUserAndShowMainScreen();
    }

    void verifySaveTokenAndShowMainScreen() {
        // TODO: Realmente verificar o salvamento do token
        verifySaveUserAndShowMainScreen();
    }

    void verifySaveAllUserDataAndEnter() {
        // TODO: Realmente verificar o salvamento de todos os dados
        verifySaveUserAndShowMainScreen();
    }

    private void verifySaveUserAndShowMainScreen() {
        // TODO: Realmente verificar o usuário
        verify(loginView).showMainScreen();
    }

    void invokeTokenService() {
        verify(tokenService).getToken(matches(USERNAME), matches(password), tokenCallBackCaptor.capture());
        tokenCallBackCaptor.getValue().onResponse(tokenHttpResponse);
    }

    /**
     * TokenService always gets called first, so we call it before actually calling
     * the UserService itself
     */
    void invokeUserService() {
        invokeTokenService();

        verify(userService).getUser(matches(TOKEN), matches(USER_ID), userCallBackCaptor.capture());
        userCallBackCaptor.getValue().onResponse(userHttpResponse);
    }
}
