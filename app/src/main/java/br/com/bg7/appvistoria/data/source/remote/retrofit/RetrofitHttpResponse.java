package br.com.bg7.appvistoria.data.source.remote.retrofit;

import javax.annotation.Nullable;

import br.com.bg7.appvistoria.data.source.remote.HttpResponse;
import retrofit2.Response;

/**
 * Created by: luciolucio
 * Date: 2017-07-19
 */

public class RetrofitHttpResponse<T> implements HttpResponse<T> {

    private Response<T> response;

    public RetrofitHttpResponse(Response<T> response) {
        this.response = response;
    }

    @Override
    public boolean isSuccessful() {
        return response.isSuccessful();
    }

    @Nullable
    @Override
    public T body() {
        return response.body();
    }

    @Override
    public int code() {
        return response.code();
    }
}
