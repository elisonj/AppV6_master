package br.com.bg7.appvistoria.data.source.remote.retrofit;

import br.com.bg7.appvistoria.data.source.remote.http.HttpResponse;

/**
 * Created by: elison
 * Date: 2017-09-12
 *
 * Exception that indicates that the response that we received from
 * the server is 'bad', i.e., cannot be used
 */

public class BadResponseException extends RuntimeException {
    public BadResponseException(HttpResponse response) {
        super(getMessageNoBaseMessage(response));
    }

    public BadResponseException(HttpResponse response, String message) {
        super(getMessage(response, message));
    }

    public BadResponseException(HttpResponse response, String message, Throwable cause) {
        super(getMessage(response, message), cause);
    }

    public BadResponseException(HttpResponse response, Throwable cause) {
        super(getMessageNoBaseMessage(response), cause);
    }

    private static String getMessageNoBaseMessage(HttpResponse response) {
        return getMessage(response, "");
    }

    private static String getMessage(HttpResponse response, String message) {
        if (response == null) {
            return message;
        }

        return message + "\n" + response.toString();
    }
}
