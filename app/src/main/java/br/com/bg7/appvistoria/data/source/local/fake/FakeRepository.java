package br.com.bg7.appvistoria.data.source.local.fake;

import android.annotation.SuppressLint;

import java.util.HashMap;

import br.com.bg7.appvistoria.data.source.local.Repository;

/**
 * Created by: luciolucio
 * Date: 2017-07-31
 */

abstract class FakeRepository<K, T> implements Repository<T> {
    @SuppressLint("UseSparseArrays")
    HashMap<K, T> ENTITIES_BY_KEY = new HashMap<>();

    @Override
    public void save(T entity) {
        ENTITIES_BY_KEY.put(getKey(entity), entity);
    }

    public void clear() {
        ENTITIES_BY_KEY.clear();
    }

    abstract K getKey(T entity);
}
