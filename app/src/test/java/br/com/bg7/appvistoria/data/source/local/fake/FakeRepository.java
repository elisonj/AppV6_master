package br.com.bg7.appvistoria.data.source.local.fake;

import android.annotation.SuppressLint;

import com.orm.SugarRecord;

import java.util.HashMap;
import java.util.Iterator;

import br.com.bg7.appvistoria.data.source.local.Repository;

/**
 * Created by: luciolucio
 * Date: 2017-07-31
 */

abstract class FakeRepository<K, T extends SugarRecord<T>> implements Repository<T> {
    @SuppressLint("UseSparseArrays")
    private HashMap<Long, T> ENTITIES_BY_ID = new HashMap<>();
    HashMap<K, T> ENTITIES_BY_KEY = new HashMap<>();

    @Override
    public T findById(Class<T> type, Long id) {
        return ENTITIES_BY_ID.get(id);
    }

    @Override
    public void save(T entity) {
        ENTITIES_BY_KEY.put(getKey(entity), entity);
        ENTITIES_BY_ID.put(entity.getId(), entity);
    }

    @Override
    public T first(Class<T> type) {
        Iterator<K> iterator = ENTITIES_BY_KEY.keySet().iterator();
        if (!iterator.hasNext()) {
            return null;
        }

        return ENTITIES_BY_KEY.get(iterator.next());
    }

    @Override
    public void delete(T entity) {
        ENTITIES_BY_KEY.remove(getKey(entity));
        ENTITIES_BY_ID.remove(entity.getId());
    }

    @Override
    public void deleteAll(Class<T> type) {
        ENTITIES_BY_KEY.clear();
    }

    abstract K getKey(T entity);
}
