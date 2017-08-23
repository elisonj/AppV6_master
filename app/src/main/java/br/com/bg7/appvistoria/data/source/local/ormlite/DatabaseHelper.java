package br.com.bg7.appvistoria.data.source.local.ormlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

import br.com.bg7.appvistoria.BuildConfig;
import br.com.bg7.appvistoria.R;
import br.com.bg7.appvistoria.data.Config;
import br.com.bg7.appvistoria.data.Inspection;
import br.com.bg7.appvistoria.data.Picture;
import br.com.bg7.appvistoria.data.User;
import br.com.bg7.appvistoria.data.WorkOrder;

/**
 * Created by: luciolucio
 * Date: 2017-08-17
 *
 * DatabaseHelper used for database initialization and updates.
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final Logger LOG = LoggerFactory.getLogger(DatabaseHelper.class);

    private static final String DATABASE_NAME = BuildConfig.DATABASE_NAME;
    private static final int DATABASE_VERSION = BuildConfig.DATABASE_VERSION;

    private RuntimeExceptionDao<Config, Long> configDao = null;
    private RuntimeExceptionDao<Inspection, Long> inspectionDao = null;
    private RuntimeExceptionDao<Picture, Long> pictureDao = null;
    private RuntimeExceptionDao<User, Long> userDao = null;
    private RuntimeExceptionDao<WorkOrder, Long> workOrderDao = null;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
    }

    /**
     * This is called when the database is first created. Usually you should call createTable statements here to create
     * the tables that will store your data.
     */
    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            LOG.info("onCreate");
            TableUtils.createTable(connectionSource, Config.class);
            TableUtils.createTable(connectionSource, Inspection.class);
            TableUtils.createTable(connectionSource, Picture.class);
            TableUtils.createTable(connectionSource, User.class);
            TableUtils.createTable(connectionSource, WorkOrder.class);
        } catch (SQLException exception) {
            LOG.error("Can't create database", exception);
            throw new RuntimeException(exception);
        }
    }

    /**
     * This is called when your application is upgraded and it has a higher version number. This allows you to adjust
     * the various data to match the new version number.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
    }

    /**
     * Gets a DAO for Config. The ones below are all similar to this one.
     *
     * We ignore the 'unchecked' inspection because we have full control over
     * this part of the code and we really wanted to reuse the {@link #getDao(RuntimeExceptionDao, Class)}
     *
     * @return a DAO for Config
     */
    public RuntimeExceptionDao<Config, Long> getConfigDao() {
        //noinspection unchecked
        return getDao(configDao, Config.class);
    }

    public RuntimeExceptionDao<Inspection, Long> getInspectionDao() {
        //noinspection unchecked
        return getDao(inspectionDao, Inspection.class);
    }

    public RuntimeExceptionDao<Picture, Long> getPictureDao() {
        //noinspection unchecked
        return getDao(pictureDao, Picture.class);
    }

    public RuntimeExceptionDao<User, Long> getUserDao() {
        //noinspection unchecked
        return getDao(userDao, User.class);
    }

    public RuntimeExceptionDao<WorkOrder, Long> getWorkOrderDao() {
        //noinspection unchecked
        return getDao(workOrderDao, WorkOrder.class);
    }

    /**
     * Close the database connections and clear any cached DAOs.
     */
    @Override
    public void close() {
        super.close();
        configDao = null;
        inspectionDao = null;
        pictureDao = null;
        userDao = null;
        workOrderDao = null;
    }

    private RuntimeExceptionDao getDao(RuntimeExceptionDao dao, Class clazz) {
        if (dao == null) {
            dao = getRuntimeExceptionDao(clazz);
        }

        return dao;
    }
}
