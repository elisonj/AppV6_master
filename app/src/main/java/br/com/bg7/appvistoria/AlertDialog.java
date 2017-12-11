package br.com.bg7.appvistoria;

import android.content.Context;
import android.view.View;

/**
 * Created by: elison
 * Date: 2017-08-28
 */

public class AlertDialog extends DialogBase {

    public AlertDialog(Context context, String message) {
        super(context, message);
    }

    public void show() {
        super.show(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.this.hide();
            }
        });
    }

    @Override
    int getDialogId() {
        return R.layout.alert_dialog;
    }
}
