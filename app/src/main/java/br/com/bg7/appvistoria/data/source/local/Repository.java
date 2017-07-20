package br.com.bg7.appvistoria.data.source.local;

import com.orm.SugarRecord;

import java.util.Iterator;

/**
 * Created by: luciolucio
 * Date: 2017-07-18
 */

public abstract class Repository<T extends SugarRecord<T>> {
    public T save(T entity) {
        entity.save();
        return entity;
    }

    public T first(Class<T> type) {
        Iterator<T> iterator = T.findAll(type);

        if (!iterator.hasNext()) {
            return null;
        }

        return iterator.next();
    }

    void delete(T entity) {
        entity.delete();
    }

    void deleteAll(Class<T> type) {
        T.deleteAll(type);
    }
}
