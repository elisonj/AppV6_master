package com.wslibrary.bg7.ws;


import android.app.Activity;
import android.os.AsyncTask;
import android.os.Handler;

import com.wslibrary.bg7.ws.GetRequestUITask.HttpMethod;

import org.json.JSONObject;

import java.util.List;

/**
 * @author elison
 */
@SuppressWarnings("rawtypes")
public class WSRequests {

	private Handler handler = null;
	private Runnable runnable = null;
	private GetRequestUITask task = null;
	private int theme = 0;
	
	public WSRequests() {
	}
	

	/**
	 * executa o request
	 * 
	 * @param activity
	 * @param callback
	 */
	public void requestGetWS(final Activity activity, final String command, final WSCallBack callback, final List<Parameter> params, final HttpMethod executeBy) {
		try {

			if (handler != null && handler != null) {
				handler.removeCallbacks(runnable);
			}

			GetRequestUITask.showError = true; 

			runnable = new Runnable() {
				public void run() {
					if (task != null) {
						if (task.getStatus() == AsyncTask.Status.FINISHED) {
							task = null;
						}
					}

					if (task == null) {
						task = new GetRequestUITask(activity, theme, command, callback, params, executeBy, true, false);
						task.execute("");
					}
				}
			};

			if (handler == null) {
				handler = new Handler();
			} else {
				handler.removeCallbacks(runnable);
			}
			handler.postDelayed(runnable, 10);
		} catch (Exception e) {
			LibraryUtil.PrintLog(LibraryUtil.TypeLog.E, LibraryUtil.TAG, "Erro na requisicao ao servidor: " + (e instanceof NullPointerException ? e.toString() : e.getMessage()));
		}
	}
	/**
	 * executa o request
	 * 
	 * @param activity
	 * @param callback
	 */
	public void requestGetWS(final Activity activity, final String command, final WSCallBack callback, final JSONObject jsonParams, final HttpMethod executeBy) {
		try {
			
			if (handler != null && handler != null) {
				handler.removeCallbacks(runnable);
			}
			
			GetRequestUITask.showError = true; 
			
			runnable = new Runnable() {
				public void run() {
					if (task != null) {
						if (task.getStatus() == AsyncTask.Status.FINISHED) {
							task = null;
						}
					}
					
					if (task == null) {
						task = new GetRequestUITask(activity, theme, command, callback, jsonParams, executeBy, true, false);
						task.execute("");
					}
				}
			};
			
			if (handler == null) {
				handler = new Handler();
			} else {
				handler.removeCallbacks(runnable);
			}
			handler.postDelayed(runnable, 10);
		} catch (Exception e) {
			LibraryUtil.PrintLog(LibraryUtil.TypeLog.E, LibraryUtil.TAG, "Erro na requisi��o ao servidor: " + (e instanceof NullPointerException ? e.toString() : e.getMessage()));
		}
	}
	
	/**
	 * executa o request
	 * 
	 * @param activity
	 * @param callback
	 */
	public void requestGetWS(final Activity activity, final String command, final WSCallBack callback, final List<Parameter> params, final HttpMethod executeBy, final boolean hideLoader) {
		try {
			
			if (handler != null && handler != null) {
				handler.removeCallbacks(runnable);
			}
			
			GetRequestUITask.showError = true; 
			
			runnable = new Runnable() {
				public void run() {
					if (task != null) {
						if (task.getStatus() == AsyncTask.Status.FINISHED) {
							task = null;
						}
					}
					
					if (task == null) {
						task = new GetRequestUITask(activity, theme, command, callback, params, executeBy, true, hideLoader);
						task.execute("");
					}
				}
			};
			
			if (handler == null) {
				handler = new Handler();
			} else {
				handler.removeCallbacks(runnable);
			}
			handler.postDelayed(runnable, 10);
		} catch (Exception e) {
			LibraryUtil.PrintLog(LibraryUtil.TypeLog.E, LibraryUtil.TAG, "Erro na requisi��o ao servidor: " + (e instanceof NullPointerException ? e.toString() : e.getMessage()));
		}
	}
}