package br.com.bg7.appvistoria.data.source.local;

import br.com.bg7.appvistoria.data.User;

/**
 * Created by: luciolucio
 * Date: 2017-07-18
 */

public interface UserRepository extends Repository<User> {
    /**
     * Find a user by username
     *
     * @param username The username of the user to search for
     * @return The user, or null if there is no user with this username
     */
    User findByUsername(String username);
}
