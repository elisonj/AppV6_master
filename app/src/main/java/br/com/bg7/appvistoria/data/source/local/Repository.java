package br.com.bg7.appvistoria.data.source.local;

/**
 * Created by: elison
 * Date: 2017-07-18
 */

public interface Repository<T> {
    void save(T entity);
}
