package br.com.bg7.appvistoria.data.source.remote.http;

/**
 * Created by: elison
 * Date: 2017-07-28
 */
public interface HttpProgressCallback<T> extends HttpCallback<T>{
    void onProgressUpdated(Integer percentage);
}
