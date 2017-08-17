package br.com.bg7.appvistoria.data.source.local.ormlite;

import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.PreparedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

import br.com.bg7.appvistoria.data.User;
import br.com.bg7.appvistoria.data.source.local.UserRepository;

import static br.com.bg7.appvistoria.data.User.USERNAME_FIELD;

/**
 * Created by: luciolucio
 * Date: 2017-08-16
 */

public class OrmLiteUserRepository extends OrmLiteRepository<User> implements UserRepository {

    private static final Logger LOG = LoggerFactory.getLogger(OrmLiteUserRepository.class);

    public OrmLiteUserRepository(RuntimeExceptionDao<User, Long> dao) {
        super(dao);
    }

    @Override
    public User findByUsername(String username) {
        try {
            PreparedQuery<User> query = dao.queryBuilder()
                    .where()
                    .eq(USERNAME_FIELD, username)
                    .prepare();

            return dao.queryForFirst(query);
        } catch (SQLException e) {
            LOG.error(SQL_EXCEPTION_MESSAGE, e);
            throw new android.database.SQLException(SQL_EXCEPTION_MESSAGE, e);
        }
    }
}
