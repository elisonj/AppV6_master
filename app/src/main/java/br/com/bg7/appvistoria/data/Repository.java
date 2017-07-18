package br.com.bg7.appvistoria.data;

import com.orm.SugarRecord;

/**
 * Created by: luciolucio
 * Date: 2017-07-18
 */

public abstract class Repository<T extends SugarRecord<T>> {
    public T save(T entity) {
        return null;
    }

    public T first() {
        return null;
    }

    void delete(T entity) {

    }

    void deleteAll() {

    }
}
