package br.com.bg7.appvistoria.data.source.remote;

/**
 * Created by: elison
 * Date: 2017-07-28
 */
public interface HttpProgressCallback<T> {
    void onProgressUpdate(int percentage);
    void onError(Throwable t);
    void onFinish(HttpResponse<T> httpResponse);
}
