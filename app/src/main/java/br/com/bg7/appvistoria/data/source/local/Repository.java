package br.com.bg7.appvistoria.data.source.local;

import com.orm.SugarRecord;

import br.com.bg7.appvistoria.data.ProductInspection;

/**
 * Created by: luciolucio
 * Date: 2017-07-18
 */

public interface Repository<T extends SugarRecord<T>> {
    void save(T entity);
    T findById(Class<T> type, Long id);
    T first(Class<T> type);
    void delete(T entity);
    void deleteAll(Class<T> type);
}
