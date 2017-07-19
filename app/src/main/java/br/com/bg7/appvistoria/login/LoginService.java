package br.com.bg7.appvistoria.login;


import android.support.annotation.NonNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.ConnectException;
import java.util.concurrent.TimeoutException;

import javax.annotation.ParametersAreNonnullByDefault;

import br.com.bg7.appvistoria.data.source.RequestTokenCallback;
import br.com.bg7.appvistoria.data.source.TokenService;
import br.com.bg7.appvistoria.data.source.local.UserRepository;
import br.com.bg7.appvistoria.data.source.remote.HttpCall;
import br.com.bg7.appvistoria.data.source.remote.HttpCallback;
import br.com.bg7.appvistoria.data.source.remote.HttpResponse;
import br.com.bg7.appvistoria.data.source.remote.dto.Token;
import br.com.bg7.appvistoria.data.source.remote.dto.UserResponse;
import br.com.bg7.appvistoria.vo.User;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by: elison
 * Date: 2017-07-11
 */

public class LoginService {

    private static final Logger LOG = LoggerFactory.getLogger(LoginService.class);

    private final TokenService service;
    private final UserRepository userRepository;
    private Token token = null;

    public LoginService(@NonNull TokenService service, @NonNull UserRepository userRepository) {
        this.service = checkNotNull(service);
        this.userRepository = checkNotNull(userRepository);
    }

    /**
     * Method to execute request and get user Token
     */
    public void requestToken(@NonNull final String username, @NonNull final String password, @NonNull final RequestTokenCallback callback) {

        service.getToken(username, password).enqueue(new HttpCallback<Token>() {
            @Override
            @ParametersAreNonnullByDefault
            public void onResponse(HttpCall<Token> call, HttpResponse<Token> response) {

                if(response.isSuccessful()) {
                    LOG.debug(" **** posts loaded from API");
                    token = response.body();
                    if(token != null) {
                        LOG.debug("Token: "+token.getAccessToken());
                        LOG.debug("UserId: "+token.getUserId());
                        requestUser(token, username, password, callback);
                    }
                } else {
                    int statusCode  = response.code();
                    LOG.error(" **** ERROR: "+ statusCode);
                    callback.onError();
                }
            }

            @Override
            @ParametersAreNonnullByDefault
            public void onFailure(HttpCall<Token> call, Throwable t) {
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
    private void requestUser(final Token token, @NonNull final String username, final @NonNull String password, final RequestTokenCallback callback) {

        service.getUser("Bearer "+token.getAccessToken(), token.getUserId()).enqueue(new HttpCallback<UserResponse>() {
            @Override
            @ParametersAreNonnullByDefault
            public void onFailure(HttpCall<UserResponse> call, Throwable t) {
                LOG.error(" **** error on load users from API", t);
                callback.onError();
            }

            @Override
            @ParametersAreNonnullByDefault
            public void onResponse(HttpCall<UserResponse> call, HttpResponse<UserResponse> response) {

                if(response.isSuccessful()) {
                    UserResponse userResponse = response.body();
                    if(userResponse != null) {
                        LOG.debug(" **** user data loaded from API: "+userResponse.getUserAccounts().get(0).getId());

                        User userFromRepository = userRepository.findByUsername(username);
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
