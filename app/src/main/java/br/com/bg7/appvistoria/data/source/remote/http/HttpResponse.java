package br.com.bg7.appvistoria.data.source.remote.http;

import javax.annotation.Nullable;

/**
 * Created by: elison
 * Date: 2017-07-19
 */

public interface HttpResponse<T> {
    boolean isSuccessful();
    @Nullable T body();
    int code();
}
