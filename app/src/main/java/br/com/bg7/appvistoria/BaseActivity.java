package br.com.bg7.appvistoria;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.akexorcist.localizationactivity.LocalizationActivity;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import br.com.bg7.appvistoria.data.Config;
import br.com.bg7.appvistoria.data.Inspection;
import br.com.bg7.appvistoria.data.Picture;
import br.com.bg7.appvistoria.data.User;
import br.com.bg7.appvistoria.data.WorkOrder;
import br.com.bg7.appvistoria.data.WorkOrderCategory;
import br.com.bg7.appvistoria.data.WorkOrderProduct;
import br.com.bg7.appvistoria.data.WorkOrderProductType;
import br.com.bg7.appvistoria.data.source.local.ormlite.DatabaseHelper;

/**
 * Created by: elison
 * Date: 2017-07-17
 */
public abstract class BaseActivity extends LocalizationActivity {

    private DatabaseHelper databaseHelper = null;

    public RuntimeExceptionDao<Config, Long> getConfigDao() {
        return getHelper().getConfigDao();
    }

    public RuntimeExceptionDao<Inspection, Long> getInspectionDao() {
        return getHelper().getInspectionDao();
    }

    public RuntimeExceptionDao<Picture, Long> getPictureDao() {
        return getHelper().getPictureDao();
    }

    public RuntimeExceptionDao<User, Long> getUserDao() {
        return getHelper().getUserDao();
    }

    public RuntimeExceptionDao<WorkOrder, Long> getWorkOrderDao() {
        return getHelper().getWorkOrderDao();
    }

    public RuntimeExceptionDao<WorkOrderCategory, Long> getWorkOrderCategoryDao() {
        return getHelper().getWorkOrderCategoryDao();
    }

    public RuntimeExceptionDao<WorkOrderProduct, Long> getWorkOrderProductDao() {
        return getHelper().getWorkOrderProductDao();
    }

    public RuntimeExceptionDao<WorkOrderProductType, Long> getWorkOrderProductTypeDao() {
        return getHelper().getWorkOrderProductTypeDao();
    }

    private DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
        }
        return databaseHelper;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        String defaultLanguage = preferences.getString(
                Constants.PREFERENCE_LANGUAGE_KEY,
                Constants.DEFAULT_LANGUAGE);
        setLanguage(defaultLanguage);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }
}
