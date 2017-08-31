package br.com.bg7.appvistoria;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;

/**
 * Created by: elison
 * Date: 2017-08-31
 */
public class ProgressDialog {

    private Context context;
    private Dialog dialog;

    public ProgressDialog(Context context) {
        this.context = context;
    }

    public void show() {

            Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.loading);
            if(dialog.getWindow() != null)
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            dialog.show();
            this.dialog = dialog;
    }

    public void hide() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

}
