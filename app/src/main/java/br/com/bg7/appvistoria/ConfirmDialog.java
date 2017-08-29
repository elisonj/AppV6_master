package br.com.bg7.appvistoria;

import android.content.Context;
import android.view.View;

/**
 * Created by: luciolucio
 * Date: 2017-08-28
 */

public class ConfirmDialog extends DialogBase {

    public ConfirmDialog(Context context, String message) {
        super(context, message);
    }

    @Override
    public void show(View.OnClickListener confirmListener, View.OnClickListener cancelListener) {
        super.show(confirmListener, cancelListener);
    }

    @Override
    int getDialogId() {
        return R.layout.confirm_dialog;
    }
}
