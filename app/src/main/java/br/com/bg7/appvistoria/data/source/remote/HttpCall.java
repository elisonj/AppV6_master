package br.com.bg7.appvistoria.data.source.remote;

/**
 * Created by: luciolucio
 * Date: 2017-07-19
 */

public interface HttpCall<T> {
    void enqueue(HttpCallback<T> httpCallback);
}