package com.wslibrary.bg7.ws;

import android.app.Activity;
import android.os.AsyncTask;

import com.wslibrary.bg7.R;
import com.wslibrary.bg7.util.MyProgressDialog;

/**
 */
public abstract class WSAsyncTask extends AsyncTask<Object, Object, String> {

	public static boolean showError = true;
	protected int errorCode = 0;
	protected MyProgressDialog dialog;
	protected boolean showActionbarProgress = false;

	@Override
	abstract protected String doInBackground(Object... params);

	@Override
	abstract protected void onPostExecute(String result);

	protected void showServerErrorAlert(Activity activity) {
	}

	protected void createDefaultDialog(Activity activity) {
//		dialog = (MyProgressDialog) MyProgressDialog.show(activity, "", activity.getString(R.string.progress_dialog_wait), true, true);
		
		dialog = new MyProgressDialog(activity);
		dialog.setMessage(activity.getString(R.string.progress_dialog_wait));
		dialog.setTitle("");
		dialog.show();
		
	}

	protected void dismissDialogOrActionBarProgress(Activity activity) {
		if (!showActionbarProgress) {
			LibraryUtil.dismissDialog(dialog);
		}
	}
}
