package br.com.bg7.appvistoria.data.source.local.ormlite;

import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

import br.com.bg7.appvistoria.data.Config;
import br.com.bg7.appvistoria.data.User;
import br.com.bg7.appvistoria.data.source.local.ConfigRepository;

import static br.com.bg7.appvistoria.data.Config.USER_ID_FIELD;

/**
 * Created by: luciolucio
 * Date: 2017-08-16
 */

public class OrmLiteConfigRepository extends OrmLiteRepository<Config> implements ConfigRepository {
    private static final Logger LOG = LoggerFactory.getLogger(OrmLiteConfigRepository.class);

    public OrmLiteConfigRepository(RuntimeExceptionDao<Config, Long> dao) {
        super(dao);
    }

    @Override
    public Config findByUser(User user) {
        QueryBuilder<Config, Long> queryBuilder = dao.queryBuilder();

        try {
            PreparedQuery<Config> query = queryBuilder
                    .where()
                    .eq(USER_ID_FIELD, user.getId())
                    .prepare();

            return dao.queryForFirst(query);
        } catch (SQLException e) {
            LOG.error(SQL_EXCEPTION_MESSAGE, e);
            throw new android.database.SQLException(SQL_EXCEPTION_MESSAGE, e);
        }
    }
}
