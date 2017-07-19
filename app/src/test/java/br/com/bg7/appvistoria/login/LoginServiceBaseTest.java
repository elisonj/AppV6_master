package br.com.bg7.appvistoria.login;

import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.bg7.appvistoria.data.source.TokenService;
import br.com.bg7.appvistoria.data.source.local.UserRepository;

/**
 * Created by: luciolucio
 * Date: 2017-07-19
 */

public class LoginServiceBaseTest {
    @Mock
    UserRepository userRepository;

    @Mock
    TokenService tokenService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
}
