package br.com.bg7.appvistoria.service;


import android.support.annotation.NonNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.ConnectException;
import java.util.concurrent.TimeoutException;

import javax.annotation.ParametersAreNonnullByDefault;

import br.com.bg7.appvistoria.data.local.UserRepository;
import br.com.bg7.appvistoria.service.dto.Token;
import br.com.bg7.appvistoria.service.dto.UserResponse;
import br.com.bg7.appvistoria.view.listeners.LoginCallback;
import br.com.bg7.appvistoria.vo.User;
import br.com.bg7.appvistoria.data.source.remote.TokenService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by: elison
 * Date: 2017-07-11
 */

public class LoginService {

    private static final Logger LOG = LoggerFactory.getLogger(LoginService.class);

    private final TokenService service;
    private final UserRepository userRepository;
    private final String grantType;
    private final String clientId;
    private Token token = null;
    private String userName, password;

    public LoginService(@NonNull TokenService service, @NonNull UserRepository userRepository, @NonNull String grantType, @NonNull String clientId) {
        this.service = checkNotNull(service);
        this.userRepository = checkNotNull(userRepository);
        this.grantType = grantType;
        this.clientId = clientId;
    }

    /**
     * Method to execute request and get user Token
     */
    public void requestToken(String userName, String password, final LoginCallback callback) {

        this.userName = userName;
        this.password = password;

        service.getToken(grantType, clientId, userName, password).enqueue(new Callback<Token>() {
            @Override
            @ParametersAreNonnullByDefault
            public void onResponse(Call<Token> call, Response<Token> response) {

                if(response.isSuccessful()) {
                    LOG.debug(" **** posts loaded from API");
                    token = response.body();
                    if(token != null) {
                        LOG.debug("Token: "+token.getAccessToken());
                        LOG.debug("UserId: "+token.getUserId());
                        requestUser(token, callback);
                    }
                } else {
                    int statusCode  = response.code();
                    LOG.error(" **** ERROR: "+ statusCode);
                    callback.onError();
                }
            }

            @Override
            @ParametersAreNonnullByDefault
            public void onFailure(Call<Token> call, Throwable t) {
                LOG.error("error loading from API", t);

                if(t instanceof TimeoutException) {
                    callback.onTimeout();
                    return;
                }

                if(t instanceof ConnectException) {
                    callback.onConnectionFailed();
                    return;
                }

                callback.onError();
            }
        });
    }

    /**
     * Request UserAccount Logged
     */
    private void requestUser(final Token token, final LoginCallback callback) {

        service.getUser("Bearer "+token.getAccessToken(), token.getUserId()).enqueue(new Callback<UserResponse>() {
            @Override
            @ParametersAreNonnullByDefault
            public void onFailure(Call<UserResponse> call, Throwable t) {
                LOG.error(" **** error on load users from API", t);
                callback.onError();
            }

            @Override
            @ParametersAreNonnullByDefault
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {

                if(response.isSuccessful()) {
                    UserResponse userResponse = response.body();
                    if(userResponse != null) {
                        LOG.debug(" **** user data loaded from API: "+userResponse.getUserAccounts().get(0).getId());

                        User userFromRepository = userRepository.findByUsername(userName);
                        if(userFromRepository == null) {
                            User user = new User(userResponse, token, password);
                            userRepository.save(user);
                        }
                        callback.onSucess();
                    }
                } else {
                    int statusCode  = response.code();
                    LOG.error(" **** ERROR: "+ statusCode);
                }
            }
        });
    }
}
