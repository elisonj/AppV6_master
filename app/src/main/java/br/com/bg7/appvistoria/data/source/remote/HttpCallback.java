package br.com.bg7.appvistoria.data.source.remote;

/**
 * Created by: luciolucio
 * Date: 2017-07-19
 */

public interface HttpCallback<T> {
    void onResponse(HttpResponse<T> httpResponse);

    /**
     * Invoked when a network exception occurred talking to the server or when an unexpected
     * exception occurred creating the request or processing the response.
     *
     * @param t A throwable that can be whether:
     * {@link java.io.IOException} if a problem occurred talking to the server.
     * {@link RuntimeException} (and subclasses) if an unexpected error occurs creating the request
     */
    void onFailure(Throwable t);
}
