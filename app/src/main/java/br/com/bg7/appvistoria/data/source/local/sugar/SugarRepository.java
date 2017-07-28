package br.com.bg7.appvistoria.data.source.local.sugar;

import com.orm.SugarRecord;

import java.util.Iterator;

import br.com.bg7.appvistoria.data.source.local.Repository;

/**
 * Created by: luciolucio
 * Date: 2017-07-28
 */

public abstract class SugarRepository<T extends SugarRecord<T>> implements Repository<T> {
    public void save(T entity) {
        entity.save();
    }

    public T first(Class<T> type) {
        Iterator<T> iterator = T.findAll(type);

        if (!iterator.hasNext()) {
            return null;
        }

        return iterator.next();
    }

    public void delete(T entity) {
        entity.delete();
    }

    public void deleteAll(Class<T> type) {
        T.deleteAll(type);
    }
}
