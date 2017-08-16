package br.com.bg7.appvistoria.data.source.local.ormlite;

import com.j256.ormlite.dao.RuntimeExceptionDao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.bg7.appvistoria.data.source.local.Repository;

/**
 * Created by: luciolucio
 * Date: 2017-08-16
 */

abstract class OrmLiteRepository<T> implements Repository<T> {

    private static final Logger LOG = LoggerFactory.getLogger(OrmLiteRepository.class);

    static final String SQL_EXCEPTION_MESSAGE = "Erro ao acessar banco de dados interno";

    RuntimeExceptionDao<T, Long> dao;

    OrmLiteRepository(RuntimeExceptionDao<T, Long> dao) {
        this.dao = dao;
    }

    @Override
    public void save(T entity) {
        dao.create(entity);
    }
}
