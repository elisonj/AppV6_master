package br.com.bg7.appvistoria.data.source.local;

/**
 * Created by: luciolucio
 * Date: 2017-07-28
 */

public class RepositoryException extends Exception {
    public RepositoryException() {
    }

    public RepositoryException(String message) {
        super(message);
    }

    public RepositoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public RepositoryException(Throwable cause) {
        super(cause);
    }
}
