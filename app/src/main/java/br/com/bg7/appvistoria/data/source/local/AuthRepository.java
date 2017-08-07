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
}
