package br.com.bg7.appvistoria.data.source.remote;

/**
 * Created by: elison
 * Date: 2017-07-28
 */
public interface HttpProgressCallback<T> extends HttpCallback<T>{
    void onProgressUpdate(int percentage);
}
