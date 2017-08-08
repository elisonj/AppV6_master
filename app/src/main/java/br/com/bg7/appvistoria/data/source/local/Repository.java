package br.com.bg7.appvistoria.data.source.local;

import com.orm.SugarRecord;

/**
 * Created by: luciolucio
 * Date: 2017-07-18
 */

public interface Repository<T extends SugarRecord<T>> {
    void save(T entity);
    T findById(Long id);
    void delete(T entity);
}
