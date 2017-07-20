package br.com.bg7.appvistoria.data.source.remote;

/**
 * Created by: luciolucio
 * Date: 2017-07-19
 */

public interface HttpCallback<T> {
    void onResponse(HttpCall<T> httpCall, HttpResponse<T> httpResponse);
    void onFailure(HttpCall<T> httpCall, Throwable t);
}
