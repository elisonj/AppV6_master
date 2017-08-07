package br.com.bg7.appvistoria.data.source.remote;

import br.com.bg7.appvistoria.data.source.remote.http.HttpProgressCallback;
import br.com.bg7.appvistoria.data.source.remote.http.HttpResponse;

/**
 * Created by: elison
 * Date: 2017-07-31
 */
public abstract class TestHttpProgressCallback implements HttpProgressCallback {

    @Override
    public void onFailure(Throwable t) {
    }

    @Override
    public void onResponse(HttpResponse httpResponse) {
    }
}
