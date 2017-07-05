package com.wslibrary.bg7.ws;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.InputStream;

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
	ImageCallBack callBack;
	boolean scale = true;
	String urldisplay = "";
	private boolean isRunning = false;

	public DownloadImageTask(ImageCallBack callBack, boolean scale) {
		this.callBack = callBack;
		this.scale = scale;
	}

	public DownloadImageTask(ImageCallBack callBack) {
		this.callBack = callBack;
		this.scale = true;
	}

	protected Bitmap doInBackground(String... urls) {
		isRunning = true;

		String urldisplay = urls[0];
		// Log.i("TESTE",
		// Calendar.getInstance().getTime()+" - GET IMAGE: "+urldisplay);
		this.urldisplay = urldisplay;
		Bitmap mIcon11 = null;
		try {
			InputStream in = new java.net.URL(urldisplay).openStream();
			mIcon11 = BitmapFactory.decodeStream(in);
		} catch (Exception e) {
			LibraryUtil.PrintLog(LibraryUtil.TypeLog.E, LibraryUtil.TAG, e);
		}
		return mIcon11;
	}

	protected void onPostExecute(Bitmap result) {
		if (result != null) {
			Bitmap scaled;

			if (scale) {
				scaled = ImageUtil.createScaledBitmap(result, 400, 320, ImageUtil.ScalingLogic.CROP);
			} else {
				scaled = result;
			}

			callBack.onResult(scaled);
		} else {
			callBack.onResult(null);
		}
		isRunning = false;
	}

	@Override
	protected void onCancelled() {
		super.onCancelled();
		isRunning = false;
	}

	public boolean isRunning() {
		return isRunning;
	}
}