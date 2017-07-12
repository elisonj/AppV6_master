package br.com.bg7.appvistoria.view;

import android.app.AlertDialog;
import android.content.Context;
import android.support.constraint.ConstraintLayout;

/**
 * Created by: elison
 * Date: 2017-07-12
 */
public class BaseView  extends ConstraintLayout {

    private Context context;
    public BaseView(Context context) {
        super(context);
        this.context = context;
    }

    /**
     * Show the dialog with message
     *
     * @param title
     * @param message
     */
    public void showDialog(String title, String message) {
            AlertDialog.Builder alert = new AlertDialog.Builder(context);
            alert.setTitle(title);
            alert.setMessage(message);
            alert.setPositiveButton("OK",null);
            alert.show();
    }
}
