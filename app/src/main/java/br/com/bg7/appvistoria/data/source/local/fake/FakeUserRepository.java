package br.com.bg7.appvistoria.data.source.local.fake;

import br.com.bg7.appvistoria.data.User;
import br.com.bg7.appvistoria.data.source.local.UserRepository;

/**
 * Created by: luciolucio
 * Date: 2017-07-31
 */

public class FakeUserRepository extends FakeRepository<String, User> implements UserRepository {
    @Override
    public User findByUsername(String username) {
        return ENTITIES_BY_KEY.get(username);
    }

    @Override
    String getKey(User user) {
        return user.getUsername();
    }
}
