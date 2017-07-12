package br.com.bg7.appvistoria.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import br.com.bg7.appvistoria.Applic;
import br.com.bg7.appvistoria.R;
import br.com.bg7.appvistoria.service.dto.Token;
import br.com.bg7.appvistoria.service.dto.UserResponse;
import br.com.bg7.appvistoria.vo.User;
import br.com.bg7.appvistoria.ws.NoConnectivityException;
import br.com.bg7.appvistoria.ws.RetrofitClient;
import br.com.bg7.appvistoria.ws.TokenService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by: elison
 * Date: 2017-07-11
 */

public class LoginService {

    private static final Logger LOG = LoggerFactory.getLogger(LoginService.class);

    private TokenService service;
    private Token token = null;
    private String userName, password;


    /**
     * Method to execute request and get user Token
     */
    public void requestToken(String username, String password) {

        this.userName = username;
        this.password = password;

        service = RetrofitClient.getClient(Applic.getInstance().getString(R.string.base_url)).
                create(TokenService.class);
        service.getToken(Applic.getInstance().getResources().getString(R.string.grant_type),
                Applic.getInstance().getResources().getString(R.string.client_id),
                username, password).enqueue(new Callback<Token>() {
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
                    LOG.debug(" **** ERROR: "+ statusCode);
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                LOG.info("error loading from API");
                if (t instanceof NoConnectivityException) {
                    LOG.info("No Internet");
                    loginOffline();
                }
            }
        });
    }

    /**
     * Request UserAccount Logged
     */
    private void requestUser(final Token token) {
        service =  RetrofitClient.getClient(Applic.getInstance().getString(R.string.base_url)).
                create(TokenService.class);

        service.getUser("Bearer "+token.getAccessToken(), token.getUserId()).enqueue(new Callback<UserResponse>() {
            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                LOG.debug(" **** error on load posts from API");
            }

            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {

                if(response.isSuccessful()) {
                    UserResponse userResponse = response.body();
                    if(userResponse != null) {
                        LOG.debug(" **** user data loaded from API: "+userResponse.getUserAccounts().get(0).getId());
                        User user = new User(userResponse, token, password);
                        user.save();
                    }
                } else {
                    int statusCode  = response.code();
                    LOG.debug(" **** ERROR: "+ statusCode);
                }
            }
        });
    }

    private void loginOffline() {
        List<User> user = User.find(User.class, "user_name = ?", this.userName);
        if(user != null && user.size() > 0) {
            String password = user.get(0).getPassword();
            if(password.equals(User.getHashPassword(this.password))) {
                LOG.info("Got data from user offline. UserId: "+user.get(0).getId());
            }
        }
    }

    /**
     * get Actual Token
     * @return
     */
    public Token getToken() {
        return token;
    }

}
