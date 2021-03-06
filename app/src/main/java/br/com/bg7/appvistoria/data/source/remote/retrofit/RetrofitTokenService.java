package br.com.bg7.appvistoria.data.source.remote.retrofit;

import br.com.bg7.appvistoria.data.source.remote.dto.Token;
import br.com.bg7.appvistoria.data.source.remote.http.HttpCallback;
import br.com.bg7.appvistoria.data.source.remote.retrofit.http.RetrofitHttpCall;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by: elison
 * Date: 2017-07-19
 */

public class RetrofitTokenService implements br.com.bg7.appvistoria.data.source.remote.TokenService {
    private final TokenService tokenService;
    private final String grantType;
    private final String clientId;

    public RetrofitTokenService(String baseUrl, String grantType, String clientId) {
        this.grantType = grantType;
        this.clientId = clientId;

        this.tokenService = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(TokenService.class);
    }

    @Override
    public void getToken(String username, String password, final HttpCallback<Token> callback) {
        Call<Token> call = tokenService.getToken(grantType, clientId, username, password);
        RetrofitHttpCall<Token> httpCall = new RetrofitHttpCall<>(call);

        httpCall.enqueue(callback);
    }
}
