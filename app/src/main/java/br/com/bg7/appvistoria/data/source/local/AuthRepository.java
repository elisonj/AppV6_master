package br.com.bg7.appvistoria.data.source.local;

import java.io.IOException;

import br.com.bg7.appvistoria.data.User;

/**
 * Created by: elison
 * Date: 2017-08-07
 */

public interface AuthRepository {
    /**
     * Get the current logged in user
     *
     * @return the logged in user, or null if not logged in
     */
    User currentUser();

    /**
     * Get the authentication token of the logged in user
     *
     * @return the authentication token of the logged in user, or null if not logged in
     */
    String currentToken();

    /**
     * Save the user data. This will qualify the given user
     * as the logged in user, and the given token is going to be used
     * for authentication with remote services.
     *
     * Any existing user and token will get overwritten.
     *
     * @param user the user to log in
     * @param token the authentication token
     * @throws IOException if the user cannot be saved
     */
    void save(User user, String token) throws IOException;

    /**
     * Removes the currently logged in user and token
     */
    void clear();
}
