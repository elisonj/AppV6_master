package br.com.bg7.appvistoria.data.source.local;

/**
 * Created by: luciolucio
 * Date: 2017-08-07
 */

public interface AuthRepository {
    /**
     * Get the username of the logged in user
     *
     * @return the logged in user's username, or null if not logged in
     */
    String currentUsername();

    /**
     * Get the authentication token of the logged in user
     *
     * @return the authentication token of the logged in user, or null if not logged in
     */
    String currentToken();

    /**
     * Save the user data. This will qualify the given username
     * as the logged in user, and the given token is going to be used
     * for authentication with remote services.
     *
     * Any existing username and token will get overwritten.
     *
     * @param username the username to log in
     * @param token the authentication token
     */
    void save(String username, String token);

    /**
     * Removes the currently logged in user and token
     */
    void clear();
}
