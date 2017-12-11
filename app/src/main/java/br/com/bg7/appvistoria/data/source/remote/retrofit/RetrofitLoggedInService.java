package br.com.bg7.appvistoria.data.source.remote.retrofit;

import br.com.bg7.appvistoria.auth.Auth;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by: elison
 * Date: 2017-09-12
 */

abstract class RetrofitLoggedInService<T> {
    static final String TOKEN = "Bearer " + Auth.token();
    private String baseUrl;

    RetrofitLoggedInService(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    T buildService(Class<T> clazz) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(clazz);
    }
}
