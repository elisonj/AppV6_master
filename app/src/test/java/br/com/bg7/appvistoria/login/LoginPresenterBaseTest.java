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
    LoginContract.View loginView;

    @Mock
    TokenService tokenService;

    @Mock
    UserService userService;

    @Mock
    UserRepository userRepository;

    @Mock
    HttpResponse<Token> tokenHttpResponse;

    @Mock
    HttpResponse<UserResponse> userHttpResponse;

    @Captor
    ArgumentCaptor<HttpCallback<Token>> tokenCallBackCaptor;

    @Captor
    ArgumentCaptor<HttpCallback<UserResponse>> userCallBackCaptor;

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


    void setUpBadPassword() {
        when(userRepository.findByUsername(anyString())).thenReturn(new User());
        loginPresenter.checkpw = false;
    }

    void setUpUserAndPasswordOk() {
        when(userRepository.findByUsername(anyString())).thenReturn(new User());
        loginPresenter.checkpw = true;
    }

    void setUpUserResponse() {
        UserResponse userResponse = new UserResponse();
        when(userHttpResponse.body()).thenReturn(userResponse);
    }

    void setUpToken() {
        when(tokenHttpResponse.isSuccessful()).thenReturn(true);
        Token token = new Token(TOKEN, USER_ID, 0);
        when(tokenHttpResponse.body()).thenReturn(token);
    }

    void verifyTokenService() {
        verify(tokenService).getToken(matches(USERNAME), matches(PASSWORD), tokenCallBackCaptor.capture());
        tokenCallBackCaptor.getValue().onResponse(tokenHttpResponse);
    }

    void verifyUserService() {
        verify(userService).getUser(matches(TOKEN), matches(USER_ID), userCallBackCaptor.capture());
        userCallBackCaptor.getValue().onResponse(userHttpResponse);
    }
    void setUpNoConnection() {
        when(loginView.isConnected()).thenReturn(false);
    }
}
