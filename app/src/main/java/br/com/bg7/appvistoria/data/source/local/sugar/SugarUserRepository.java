package br.com.bg7.appvistoria.data.source.local.sugar;

import java.util.List;

import br.com.bg7.appvistoria.data.User;
import br.com.bg7.appvistoria.data.source.local.UserRepository;

/**
 * Created by: luciolucio
 * Date: 2017-07-28
 */

public class SugarUserRepository extends SugarRepository<User> implements UserRepository {
    @Override
    public User findByUsername(String username) {
        List<User> userList = User.find(User.class, "user_name = ?", username);

        if(userList != null && userList.size() > 0) {
            return userList.get(0);
        }

        return null;
    }
}
