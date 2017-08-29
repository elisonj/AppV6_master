package br.com.bg7.appvistoria;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by: luciolucio
 * Date: 2017-08-28
 */

abstract class DialogBase {

    private Context context;
    private String message;
    private Dialog dialog;

    DialogBase(Context context, String message) {
        this.context = context;
        this.message = message;
    }

    void show(View.OnClickListener confirmListener, final View.OnClickListener cancelListener) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(getDialogId());

        TextView text = dialog.findViewById(R.id.message);
        text.setText(message);

        Button confirmButton = dialog.findViewById(R.id.button_confirm);
        confirmButton.setOnClickListener(confirmListener);

        Button cancelButton = dialog.findViewById(R.id.button_cancel);
        if (cancelButton != null) {
            cancelButton.setOnClickListener(cancelListener);
        }

        dialog.show();
        this.dialog = dialog;
    }

    void show(View.OnClickListener confirmListener) {
        show(confirmListener, null);
    }

    public void hide() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    abstract int getDialogId();
}
