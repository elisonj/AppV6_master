package br.com.bg7.appvistoria.data.source.local;

import java.util.List;

import br.com.bg7.appvistoria.data.User;

/**
 * Created by: luciolucio
 * Date: 2017-07-18
 */

public class UserRepository extends Repository<User> {
    public User findByUsername(String username) {
        List<User> userList = User.find(User.class, "user_name = ?", username);

        if(userList != null && userList.size() > 0) {
            return userList.get(0);
        }

        return null;
    }
}
