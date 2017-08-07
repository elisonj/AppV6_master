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
     * Save the username. This will qualify the given username
     * as the logged in user.
     *
     * Any existing username will get overwritten.
     *
     * @param username the username to log in
     */
    void save(String username);

    /**
     * Removes the currently logged in user
     */
    void clear();
}
