package com.wslibrary.bg7.ws;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.view.WindowManager.LayoutParams;
import android.widget.RelativeLayout;

import com.wslibrary.bg7.R;

/**
 */
public abstract class WSAsyncTaskGeneric<W, U, T> extends AsyncTask<W, U, T> {

	public static boolean showError = true;
	protected int errorCode = 0;
	protected static ProgressDialog dialog;
	public boolean showActionbarProgress = false;
	public int theme = 0;

	@Override
	abstract protected T doInBackground(W... params);

	@Override
	abstract protected void onPostExecute(T result);

	protected void showServerErrorAlert(Activity activity) {
		LibraryUtil.showDialog(activity, activity.getString(R.string.generic_alert_warn), activity.getString(R.string.forget_result_notOk));
	}

	protected void createDefaultDialog(Activity activity) {
		if (activity == null || activity.isFinishing())
			return;
		
		if (dialog == null || !dialog.isShowing()) {
			dialog = new ProgressDialog(activity);
			dialog.getWindow().setFlags(LayoutParams.FLAG_FULLSCREEN, LayoutParams.FLAG_FULLSCREEN);

			dialog.show();
			dialog.setContentView(R.layout.loader);
			RelativeLayout bg = (RelativeLayout) dialog.findViewById(R.id.loader_background);
			
//			dialog.setMessage(activity.getString(R.string.progress_dialog_wait));
//			dialog.setTitle("");
//			dialog.setMessage("");
//			dialog.setTitle("");
		}
//		dialog.setCancelable(false);
	}

	protected void dismissDialogOrActionBarProgress(Activity activity) {
		if (!showActionbarProgress) {
			LibraryUtil.dismissDialog(dialog);
			dialog = null;
		}
	}

	protected void initActionBarProgress(Activity activity) {
	}
}