package com.wslibrary.bg7.ws;


import android.app.Activity;

import org.json.JSONObject;

import java.util.List;

/**
 * @author elison
 */
@SuppressWarnings("rawtypes")
public class GetRequestUITask extends WSAsyncTaskGeneric<Object, Void, JSONObject> {

	private Activity activity;
	private boolean isFirstRun = true;
	private WSCallBack callback;
	private List<Parameter> params;
	private String command;
	private HttpMethod httpMethod;
	private JSONObject jsonParams;

	public static enum HttpMethod {
		POST, GET, PUT
	};

	public GetRequestUITask(final Activity activity, int theme, final String command, WSCallBack callback, final List<Parameter> params, HttpMethod httpMethod, boolean isFirstRun, boolean showActionbarProgress) {

		this.command = command;
		this.params = params;
		this.activity = activity;
		this.httpMethod = httpMethod;
		this.isFirstRun = isFirstRun;
		this.callback = callback;
		this.theme = theme;
		this.showActionbarProgress = showActionbarProgress;

		if (this.isFirstRun) {
			if (!showActionbarProgress && activity != null && !activity.isFinishing()) {
				LibraryUtil.PrintLog(LibraryUtil.TypeLog.E, LibraryUtil.TAG, "Execute Command: " + command);
				createDefaultDialog(activity);
				LibraryUtil.registerDialog(dialog);
			}
			this.isFirstRun = false;
		}
	}

	
	public GetRequestUITask(final Activity activity, int theme, final String command, WSCallBack callback, final JSONObject jsonParams, HttpMethod httpMethod, boolean isFirstRun, boolean showActionbarProgress) {
		
		this.command = command;
		this.jsonParams = jsonParams;
		this.activity = activity;
		this.httpMethod = httpMethod;
		this.isFirstRun = isFirstRun;
		this.callback = callback;
		this.theme = theme;
		this.showActionbarProgress = showActionbarProgress;
		
		if (this.isFirstRun) {
			if (!showActionbarProgress && activity != null && !activity.isFinishing()) {
				LibraryUtil.PrintLog(LibraryUtil.TypeLog.E, LibraryUtil.TAG, "Execute Command: " + command);
				createDefaultDialog(activity);
				LibraryUtil.registerDialog(dialog);
			}
			this.isFirstRun = false;
		}
	}

	@Override
	protected JSONObject doInBackground(Object... arg0) {

		JSONObject result = null;
		errorCode = 0;
		try {
			if(params != null) {
				result = WSCommands.requestWs(activity, command, params, httpMethod);
			} else if(jsonParams != null) {
				result = WSCommands.requestWs(activity, command, jsonParams, httpMethod);
			}
		} catch (Exception e) {
			LibraryUtil.PrintLog(LibraryUtil.TypeLog.E, LibraryUtil.TAG, e instanceof NullPointerException ? e.toString() : e.getMessage());
			this.cancel(true);// need to update the status to finished.
		}

		return result;
	}

	@Override
	protected void onPostExecute(JSONObject result) {
		if (result != null) {
			try {
				
				LibraryUtil.PrintLog(LibraryUtil.TypeLog.E, LibraryUtil.TAG, "- activity.getComponentName() " + activity.getLocalClassName());
				LibraryUtil.PrintLog(LibraryUtil.TypeLog.E, LibraryUtil.TAG, "- Result " + result.toString());
				
					callback.onResult(result);
					LibraryUtil.PrintLog(LibraryUtil.TypeLog.E, LibraryUtil.TAG, "- Dismiss Command: " + command);
					dismissDialogOrActionBarProgress(activity);
			} catch (Exception ex) {
				LibraryUtil.PrintLog(LibraryUtil.TypeLog.E, LibraryUtil.TAG, ex);
				this.cancel(true);// need to update the status to finished.
			} finally {
				try {
					LibraryUtil.PrintLog(LibraryUtil.TypeLog.E, LibraryUtil.TAG, "- Dismiss Command: " + command);
					dismissDialogOrActionBarProgress(activity);
				} catch (Exception ex) {
					LibraryUtil.PrintLog(LibraryUtil.TypeLog.E, LibraryUtil.TAG, ex);
				}
			}
		} else {
			dismissDialogOrActionBarProgress(activity);
			if (showError) {
				showServerErrorAlert(activity);
				showError = false;
			}
		}
	}
}