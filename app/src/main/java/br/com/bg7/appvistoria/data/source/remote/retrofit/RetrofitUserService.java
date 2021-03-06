package br.com.bg7.appvistoria.data.source.remote.retrofit;

import br.com.bg7.appvistoria.data.source.remote.dto.UserResponse;
import br.com.bg7.appvistoria.data.source.remote.http.HttpCallback;
import br.com.bg7.appvistoria.data.source.remote.retrofit.http.RetrofitHttpCall;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by: elison
 * Date: 2017-07-19
 */

public class RetrofitUserService implements br.com.bg7.appvistoria.data.source.remote.UserService {
    private final UserService userService;

    public RetrofitUserService(String baseUrl) {
        this.userService = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(UserService.class);
    }

    @Override
    public void getUser(String token, String userId, final HttpCallback<UserResponse> callback) {
        Call<UserResponse> call = userService.getUser("Bearer " + token, userId);
        RetrofitHttpCall<UserResponse> httpCall = new RetrofitHttpCall<>(call);

        httpCall.enqueue(callback);
    }
}
