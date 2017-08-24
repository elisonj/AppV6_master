package br.com.bg7.appvistoria;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.akexorcist.localizationactivity.LocalizationActivity;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import br.com.bg7.appvistoria.data.Config;
import br.com.bg7.appvistoria.data.Inspection;
import br.com.bg7.appvistoria.data.Picture;
import br.com.bg7.appvistoria.data.User;
import br.com.bg7.appvistoria.data.WorkOrder;
import br.com.bg7.appvistoria.data.source.local.ormlite.DatabaseHelper;

/**
 * Created by: elison
 * Date: 2017-07-17
 */
public abstract class BaseActivity extends LocalizationActivity {

    private DatabaseHelper databaseHelper = null;
    public  Dialog dialog;

    public RuntimeExceptionDao<User, Long> getUserDao() {
        return getHelper().getUserDao();
    }

    public RuntimeExceptionDao<Config, Long> getConfigDao() {
        return getHelper().getConfigDao();
    }


    public RuntimeExceptionDao<WorkOrder, Long> getWorkOrderDao() {
        return getHelper().getWorkOrderDao();
    }


    public RuntimeExceptionDao<Inspection, Long> getInspectionDao() {
        return getHelper().getInspectionDao();
    }

    public RuntimeExceptionDao<Picture, Long> getPictureDao() {
        return getHelper().getPictureDao();
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

    public void showConfirmDialog(String message, View.OnClickListener confirm, View.OnClickListener cancel) {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.confirm_dialog);

        TextView text = dialog.findViewById(R.id.message);
        text.setText(message);

        Button cancelButton = dialog.findViewById(R.id.button_cancel);
        cancelButton.setOnClickListener(cancel);

        Button confirmButton =dialog.findViewById(R.id.button_confirm);
        confirmButton.setOnClickListener(confirm);

        dialog.show();
    }

    public void showAlertDialog(String message, View.OnClickListener confirm) {
        dialog = new Dialog(this);

        dialog.setContentView(R.layout.alert_dialog);

        TextView text = dialog.findViewById(R.id.message);
        text.setText(message);

        Button confirmButton =dialog.findViewById(R.id.button_confirm);
        confirmButton.setOnClickListener(confirm);

        dialog.show();
    }

}
