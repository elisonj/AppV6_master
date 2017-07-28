package br.com.bg7.appvistoria.data.source.remote.retrofit;

import javax.annotation.ParametersAreNonnullByDefault;

import br.com.bg7.appvistoria.data.source.remote.HttpCallback;
import br.com.bg7.appvistoria.data.source.remote.HttpProgressCallback;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by: luciolucio
 * Date: 2017-07-19
 */

class RetrofitHttpCall<T> {

    private final Call<T> call;

    RetrofitHttpCall(Call<T> call) {
        this.call = call;
    }

    void enqueue(final HttpCallback<T> httpCallback) {
        call.enqueue(new Callback<T>() {
            @Override
            @ParametersAreNonnullByDefault
            public void onResponse(Call<T> call, Response<T> response) {
                httpCallback.onResponse(new RetrofitHttpResponse<>(response));
            }

            @Override
            @ParametersAreNonnullByDefault
            public void onFailure(Call<T> call, Throwable t) {
                httpCallback.onFailure(t);
            }
        });
    }

    void enqueue(final HttpProgressCallback<T> httpCallback) {
        call.enqueue(new Callback<T>() {
            @Override
            @ParametersAreNonnullByDefault
            public void onResponse(Call<T> call, Response<T> response) {
                httpCallback.onResponse(new RetrofitHttpResponse<>(response));
            }

            @Override
            @ParametersAreNonnullByDefault
            public void onFailure(Call<T> call, Throwable t) {
                httpCallback.onFailure(t);
            }
        });
    }
}
