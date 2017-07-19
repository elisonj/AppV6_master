package br.com.bg7.appvistoria.data.source.remote.retrofit;

import br.com.bg7.appvistoria.data.source.remote.HttpCall;
import br.com.bg7.appvistoria.data.source.remote.dto.Token;
import br.com.bg7.appvistoria.data.source.remote.dto.UserResponse;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by: luciolucio
 * Date: 2017-07-19
 */

public class RetrofitTokenService implements br.com.bg7.appvistoria.data.source.TokenService {
    private TokenService tokenService;
    private String grantType;
    private String clientId;

    public RetrofitTokenService(String baseUrl, String grantType, String clientId) {
        this.grantType = grantType;
        this.clientId = clientId;

        this.tokenService = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(TokenService.class);
    }

    @Override
    public HttpCall<Token> getToken(String username, String password) {
        Call<Token> call = tokenService.getToken(grantType, clientId, username, password);

        return new RetrofitHttpCall<>(call);
    }

    @Override
    public HttpCall<UserResponse> getUser(String token, String userId) {
        Call<UserResponse> call = tokenService.getUser(token, userId);

        return new RetrofitHttpCall<>(call);
    }
}
