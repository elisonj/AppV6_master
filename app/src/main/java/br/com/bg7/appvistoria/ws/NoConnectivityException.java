package br.com.bg7.appvistoria.ws;

import java.io.IOException;

/**
 * Created by: elison
 * Date: 2017-07-12
 */
public class NoConnectivityException extends IOException {

    @Override
    public String getMessage() {
        return "No connectivity exception";
    }
}