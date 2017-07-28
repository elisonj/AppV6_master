package br.com.bg7.appvistoria.data.source.local;

import br.com.bg7.appvistoria.data.User;

/**
 * Created by: luciolucio
 * Date: 2017-07-18
 */

public interface UserRepository extends Repository<User> {
    User findByUsername(String username);
}
