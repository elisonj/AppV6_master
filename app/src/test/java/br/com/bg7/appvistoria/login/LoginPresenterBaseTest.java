package br.com.bg7.appvistoria.login;

import org.junit.Before;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.bg7.appvistoria.data.source.TokenService;
import br.com.bg7.appvistoria.data.source.UserService;
import br.com.bg7.appvistoria.data.source.local.UserRepository;
import br.com.bg7.appvistoria.data.source.remote.HttpCallback;
import br.com.bg7.appvistoria.data.source.remote.HttpResponse;
import br.com.bg7.appvistoria.data.source.remote.dto.Token;
import br.com.bg7.appvistoria.data.source.remote.dto.UserResponse;
import br.com.bg7.appvistoria.vo.User;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.matches;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

    @Mock
    protected HttpResponse<Token> tokenHttpResponse;

    @Mock
    protected HttpResponse<UserResponse> userHttpResponse;

    @Captor
    protected ArgumentCaptor<HttpCallback<Token>> tokenCallBackCaptor;

    @Captor
    protected ArgumentCaptor<HttpCallback<UserResponse>> userCallBackCaptor;

    TestableLoginPresenter loginPresenter;

    static final String USERNAME = "user";
    static final String PASSWORD = "password";
    static final String TOKEN = "token";
    static final String USER_ID = "user_id";

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        loginPresenter = new TestableLoginPresenter(tokenService, userService, userRepository, loginView);
    }

    void callLogin() {
        loginPresenter.login(USERNAME, PASSWORD);
    }


    protected void setUpBadPassword() {
        when(userRepository.findByUsername(anyString())).thenReturn(new User());
        loginPresenter.checkpw = false;
    }

    protected void setUpUserAndPasswordOk() {
        when(userRepository.findByUsername(anyString())).thenReturn(new User());
        loginPresenter.checkpw = true;
    }

    protected void setUpUserResponse() {
        UserResponse userResponse = new UserResponse();
        when(userHttpResponse.body()).thenReturn(userResponse);
    }

    protected void setUpToken() {
        when(tokenHttpResponse.isSuccessful()).thenReturn(true);
        Token token = new Token(TOKEN, USER_ID, 0);
        when(tokenHttpResponse.body()).thenReturn(token);
    }

    protected void verifyTokenService() {
        verify(tokenService).getToken(matches(USERNAME), matches(PASSWORD), tokenCallBackCaptor.capture());
        tokenCallBackCaptor.getValue().onResponse(tokenHttpResponse);
    }

    protected void verifyUserService() {
        verify(userService).getUser(matches(TOKEN), matches(USER_ID), userCallBackCaptor.capture());
        userCallBackCaptor.getValue().onResponse(userHttpResponse);
    }
}
