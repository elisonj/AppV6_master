package br.com.bg7.appvistoria.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import br.com.bg7.appvistoria.Applic;
import br.com.bg7.appvistoria.R;
import br.com.bg7.appvistoria.service.dto.Token;
import br.com.bg7.appvistoria.service.dto.UserResponse;
import br.com.bg7.appvistoria.view.listeners.LoginCallback;
import br.com.bg7.appvistoria.vo.User;
import br.com.bg7.appvistoria.ws.RetrofitClient;
import br.com.bg7.appvistoria.ws.TokenService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static br.com.bg7.appvistoria.R.string.base_url;

/**
 * Created by: elison
 * Date: 2017-07-11
 */

public class LoginService {

    private static final Logger LOG = LoggerFactory.getLogger(LoginService.class);

    private TokenService service;
    private Token token = null;
    private String userName, password;
    private LoginCallback callback;

    /**
     * Method to execute request and get user Token
     */
    public void requestToken(final LoginCallback callback, String userName, String password) {

        this.callback = callback;
        this.userName = userName;
        this.password = password;

        service = RetrofitClient.getClient(Applic.getInstance().getResources().getString(R.string.base_url)).
                create(TokenService.class);
        service.getToken(Applic.getInstance().getResources().getString(R.string.grant_type),
                Applic.getInstance().getResources().getString(R.string.client_id),
                userName, password).enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {

                if(response.isSuccessful()) {
                    LOG.debug(" **** posts loaded from API");
                    token = response.body();
                    if(token != null) {
                        LOG.debug("Token: "+token.getAccessToken());
                        LOG.debug("UserId: "+token.getUserId());
                        requestUser(token);
                    }
                } else {
                    int statusCode  = response.code();
                    LOG.error(" **** ERROR: "+ statusCode);
                    callback.onFailure(new Throwable());
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                LOG.error("error loading from API");
                callback.onFailure(t);
            }
        });
    }

    /**
     * Request UserAccount Logged
     */
    private void requestUser(final Token token) {
        service =  RetrofitClient.getClient(Applic.getInstance().getString(base_url)).
                create(TokenService.class);

        service.getUser("Bearer "+token.getAccessToken(), token.getUserId()).enqueue(new Callback<UserResponse>() {
            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                LOG.error(" **** error on load posts from API");
                callback.onFailure(t);
            }

            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {

                if(response.isSuccessful()) {
                    UserResponse userResponse = response.body();
                    if(userResponse != null) {
                        LOG.debug(" **** user data loaded from API: "+userResponse.getUserAccounts().get(0).getId());
                        List<User> listUserBD = User.find(User.class, "user_name = ?", userName);
                        if(listUserBD == null || listUserBD.size() == 0) {
                            User user = new User(userResponse, token, password);
                            user.save();
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

    /**
     * get Actual Token
     * @return
     */
    public Token getToken() {
        return token;
    }

}
