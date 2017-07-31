package br.com.bg7.appvistoria.data.source.local.fake;

import java.util.HashMap;
import java.util.Iterator;

import br.com.bg7.appvistoria.data.User;
import br.com.bg7.appvistoria.data.source.local.UserRepository;

/**
 * Created by: luciolucio
 * Date: 2017-07-31
 */

public class FakeUserRepository implements UserRepository {
    private static final HashMap<String, User> USERS_BY_USERNAME = new HashMap<>();

    @Override
    public void save(User entity) {
        USERS_BY_USERNAME.put(entity.getUsername(), entity);
    }

    @Override
    public User first(Class<User> type) {
        Iterator<String> iterator = USERS_BY_USERNAME.keySet().iterator();
        if (!iterator.hasNext()) {
            return null;
        }

        return USERS_BY_USERNAME.get(iterator.next());
    }

    @Override
    public void delete(User entity) {
        USERS_BY_USERNAME.remove(entity.getUsername());
    }

    @Override
    public void deleteAll(Class<User> type) {
        USERS_BY_USERNAME.clear();
    }

    @Override
    public User findByUsername(String username) {
        return USERS_BY_USERNAME.get(username);
    }
}
