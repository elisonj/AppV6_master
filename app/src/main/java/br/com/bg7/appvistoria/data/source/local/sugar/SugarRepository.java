package br.com.bg7.appvistoria.data.source.local.sugar;

import android.database.SQLException;

import com.orm.SugarRecord;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;

import br.com.bg7.appvistoria.ErrorConstants;
import br.com.bg7.appvistoria.data.source.local.Repository;

/**
 * Created by: luciolucio
 * Date: 2017-07-28
 */

public abstract class SugarRepository<T extends SugarRecord<T>> implements Repository<T> {
    private static final Logger LOG = LoggerFactory.getLogger(SugarRepository.class);

    public void save(T entity) {
        try {
            entity.save();
        }
        catch (SQLException sqlException) {
            LOG.error(ErrorConstants.SUGAR_SQL_EXCEPTION, sqlException);
            throw sqlException;
        }
    }

    public T first(Class<T> type) {
        try {
            Iterator<T> iterator = T.findAll(type);

            if (!iterator.hasNext()) {
                return null;
            }

            return iterator.next();
        }
        catch (SQLException sqlException) {
            LOG.error(ErrorConstants.SUGAR_SQL_EXCEPTION, sqlException);
            throw sqlException;
        }
    }

    public void delete(T entity) {
        try {
            entity.delete();
        }
        catch (SQLException sqlException) {
            LOG.error(ErrorConstants.SUGAR_SQL_EXCEPTION, sqlException);
            throw sqlException;
        }
    }

    public void deleteAll(Class<T> type) {
        try {
            T.deleteAll(type);
        }
        catch (SQLException sqlException) {
            LOG.error(ErrorConstants.SUGAR_SQL_EXCEPTION, sqlException);
            throw sqlException;
        }
    }
}
