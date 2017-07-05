package com.wslibrary.bg7.util;

import android.app.ProgressDialog;
import android.content.Context;

import com.wslibrary.bg7.R;

public class MyProgressDialog extends ProgressDialog {

    public MyProgressDialog(Context context) {
        super(context, R.style.NewDialog);
    }

}