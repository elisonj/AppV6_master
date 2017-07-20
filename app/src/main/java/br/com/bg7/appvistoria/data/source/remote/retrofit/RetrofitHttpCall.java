package br.com.bg7.appvistoria.data.source.remote.retrofit;

import javax.annotation.ParametersAreNonnullByDefault;

import br.com.bg7.appvistoria.data.source.remote.HttpCall;
import br.com.bg7.appvistoria.data.source.remote.HttpCallback;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by: luciolucio
 * Date: 2017-07-19
 */

public class RetrofitHttpCall<T> implements HttpCall<T> {

    private final Call<T> call;

    public RetrofitHttpCall(Call<T> call) {
        this.call = call;
    }

    @Override
    public void enqueue(final HttpCallback<T> httpCallback) {
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
