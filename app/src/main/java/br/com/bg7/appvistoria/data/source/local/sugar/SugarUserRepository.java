package br.com.bg7.appvistoria.data.source.local.sugar;

import android.database.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import br.com.bg7.appvistoria.ErrorConstants;
import br.com.bg7.appvistoria.data.User;
import br.com.bg7.appvistoria.data.source.local.UserRepository;

/**
 * Created by: luciolucio
 * Date: 2017-07-28
 */

public class SugarUserRepository extends SugarRepository<User> implements UserRepository {

    private static final Logger LOG = LoggerFactory.getLogger(SugarUserRepository.class);

    public SugarUserRepository() {
        super(User.class);
    }

    @Override
    public User findByUsername(String username) {
        try {
            List<User> userList = User.find(User.class, "username = ?", username);

            if (userList != null && userList.size() > 0) {
                return userList.get(0);
            }

            return null;
        }
        catch (SQLException sqlException) {
            LOG.error(ErrorConstants.SUGAR_SQL_EXCEPTION, sqlException);
            throw sqlException;
        }
    }
}
