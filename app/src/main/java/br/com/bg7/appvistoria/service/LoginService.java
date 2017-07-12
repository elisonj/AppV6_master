package br.com.bg7.appvistoria.service;

import br.com.bg7.appvistoria.util.Util;
import br.com.bg7.appvistoria.vo.Token;
import br.com.bg7.appvistoria.vo.UserResponse;
import br.com.bg7.appvistoria.ws.ServiceInterface;
import br.com.bg7.appvistoria.ws.ServiceUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by elison on 11/07/17.
 */

public class LoginService {

    private ServiceInterface mService;
    private Token token = null;

    /**
     * Method to execute request and get user Token
     */
    public void requestToken(String username, String password) {

        String grant_type = "password";
        String client_id = "61922-5b5d19794c749cfbc5d20becc3826e08-s4b-cda-app";

        mService = ServiceUtils.getService();
        mService.getToken(grant_type, client_id, username, password).enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {

                if(response.isSuccessful()) {
                    Util.Log.d(" **** posts loaded from API");
                    token = response.body();
                    if(token != null) {
                        Util.Log.i("Token: "+token.getAccessToken());
                        Util.Log.i("UserId: "+token.getUserId());
                        requestUser(token);
                    }
                } else {
                    int statusCode  = response.code();
                    Util.Log.d(" **** ERROR: "+ statusCode);
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                //showErrorMessage();
                Util.Log.d("error loading from API");
            }
        });
    }

    /**
     * Request UserAccount Logged
     * @param token
     */
    private void requestUser(Token token) {
        mService = ServiceUtils.getService();

        mService.getUser("Bearer "+token.getAccessToken(), token.getUserId()).enqueue(new Callback<UserResponse>() {
            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Util.Log.d(" **** error on load posts from API");
            }

            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {

                if(response.isSuccessful()) {
                    UserResponse userResponse = response.body();
                    if(userResponse != null) {
                        Util.Log.d(" **** user data loaded from API: "+userResponse.getUserAccounts().get(0).getId());
                    }
                } else {
                    int statusCode  = response.code();
                    Util.Log.d(" **** ERROR: "+ statusCode);
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
