package com.wslibrary.bg7.ws;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.webkit.MimeTypeMap;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Elison
 */
public class LibraryUtil {

	public static String TAG = "BG7_LIB";


	public static boolean isInDebugMode = true; // habilita o log

	public static boolean SHOWLOG_I = true;
	public static boolean SHOWLOG_D = true;
	public static boolean SHOWLOG_E = true;
	public static boolean SHOWLOG_W = true;

	private static ArrayList<ProgressDialog> dialogs = new ArrayList<ProgressDialog>();

	public static enum TypeLog {
		I, D, E, W
	};

	public static void PrintLog(TypeLog typeLog, String tag, Exception e) {

		switch (typeLog) {
		case I:
			if (SHOWLOG_I)
				Log.i(tag, e instanceof NullPointerException ? e.toString() : e.getMessage());
			break;
		case D:
			if (SHOWLOG_D)
				Log.d(e instanceof NullPointerException ? e.toString() : e.getMessage());
			break;
		case E:
			if (SHOWLOG_E)
				Log.e(e instanceof NullPointerException ? e.toString() : e.getMessage());
			break;
		case W:
			if (SHOWLOG_W)
				Log.e(e instanceof NullPointerException ? e.toString() : e.getMessage());
			break;
		}
	}

	public static void PrintLog(TypeLog typeLog, String tag, String e) {

		switch (typeLog) {
		case I:
			if (SHOWLOG_I)
				Log.i(tag, e);
			break;
		case D:
			if (SHOWLOG_D)
				Log.i(tag, e);
			break;
		case E:
			if (SHOWLOG_E)
				Log.i(tag, e);
			break;
		case W:
			if (SHOWLOG_W)
				Log.i(tag, e);
			break;
		}
	}
	

	/*
	 * Classe usada para efetuar o log na aplica
	 */
	public static class Log {

		public static void e(String log) {
			if (isInDebugMode)
				splitAndLog_e(TAG, log);
		}

		public static void i(String log) {
			if (isInDebugMode)
				splitAndLog_i(TAG, log);
		}
		
		public static void i(String tag, String log) {
			if (isInDebugMode)
				splitAndLog_i(tag, log);
		}

		public static void d(String log) {
			if (isInDebugMode)
				splitAndLog_d(TAG, log);
		}



		public static BufferedWriter out;

		/**
		 * Save log in file system - create one file per day
		 * @param log
		 */
		public static void file(String log) {
			i(log);
		}
	}



	/**
	 * get ContentType of file
	 * @param url
	 * @return
	 */
	public static String getMimeType(String url) {
		String type = null;
		String extension = MimeTypeMap.getFileExtensionFromUrl(url);
		if (extension != null) {
			type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
		}
		return type;
	}
	

	
	/**
     * Divides a string into chunks of a given character size.
     * 
     * @param text                  String text to be sliced
     * @param sliceSize             int Number of characters
     * @return  ArrayList<String>   Chunks of strings
     */
    public static ArrayList<String> splitString(String text, int sliceSize) {
        ArrayList<String> textList = new ArrayList<String>();
        String aux;
        int left = -1, right = 0;
        int charsLeft = text.length();
        while (charsLeft != 0) {
            left = right;
            if (charsLeft >= sliceSize) {
                right += sliceSize;
                charsLeft -= sliceSize;
            }
            else {
                right = text.length();
                aux = text.substring(left, right);
                charsLeft = 0;
            }
            aux = text.substring(left, right);
            textList.add(aux);
        }
        return textList;
    }

    /**
     * Divides a string into chunks.
     * 
     * @param text                  String text to be sliced
     * @return  ArrayList<String>   
     */
    public static ArrayList<String> splitString(String text) {
        return splitString(text, 600);
    }

    /**
     * Divides the string into chunks for displaying them
     * into the Eclipse's LogCat.
     * 
     * @param text      The text to be split and shown in LogCat
     * @param tag       The tag in which it will be shown.
     */
    public static void splitAndLog_i(String tag, String text ) {
        ArrayList<String> messageList = splitString(text);
        for (String message : messageList) {
        	android.util.Log.i(tag, message);
        }
    }
	
    
    /**
     * Divides the string into chunks for displaying them
     * into the Eclipse's LogCat.
     * 
     * @param text      The text to be split and shown in LogCat
     * @param tag       The tag in which it will be shown.
     */
    public static void splitAndLog_e(String tag, String text ) {
    	ArrayList<String> messageList = splitString(text);
    	for (String message : messageList) {
    		android.util.Log.e(tag, message);
    	}
    }
    
	
    
    /**
     * Divides the string into chunks for displaying them
     * into the Eclipse's LogCat.
     * 
     * @param text      The text to be split and shown in LogCat
     * @param tag       The tag in which it will be shown.
     */
    public static void splitAndLog_d(String tag, String text ) {
    	ArrayList<String> messageList = splitString(text);
    	for (String message : messageList) {
    		android.util.Log.d(tag, message);
    	}
    }
    
    
	
	

	/**
	 * Show the dialog with message
	 * 
	 * @param activity
	 */
	public static void showDialog(final Activity activity, String title, String message) {
		if(!activity.isFinishing()) {
			Builder alert = new Builder(activity);
			alert.setTitle(title);
			alert.setMessage(message);
			alert.setPositiveButton("OK",null);
			alert.show();   
		}
	}

	/**
	 * register dialog in ArrayList to avoid dismiss when pause activity
	 * 
	 * @param dialog
	 */
	public static void registerDialog(ProgressDialog dialog) {
		if (dialog != null)
			dialogs.add(dialog);
	}

	/**
	 * unRegister all dialogs and clear ArrayList
	 */
	public static void unRegisterDialogs() {

		if (dialogs != null && dialogs.size() > 0) {
			for (ProgressDialog dialog : dialogs) {

				if (dialog != null && dialog.isShowing())
					dialog.dismiss();
			}

			dialogs.clear();
		}
	}

	/**
	 * Fecha o dialog
	 * 
	 * @param dialog
	 */
	public static void dismissDialog(ProgressDialog dialog) {
		try {
			if (dialog != null && dialog.isShowing())
				dialog.dismiss();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	

	/**
	 * Para e libera as asyncTasks.
	 * 
	 * @param runnable
	 * @param handler
	 */
	public static void releaseWSTasks(Runnable runnable, Handler handler) {

		if (runnable != null && handler != null) {
			handler.removeCallbacks(runnable);
		}
		runnable = null;
		handler = null;
	}

	/**
	 * Convert the Stream format to String format
	 * 
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static String convertStreamToString(InputStream is) throws IOException {
		/*
		 * To convert the InputStream to String we use the Reader.read(char[]
		 * buffer) method. We iterate until the Reader return -1 which means
		 * there's no more data to read. We use the StringWriter class to
		 * produce the string.
		 */
		if (is != null) {
			Writer writer = new StringWriter();

			char[] buffer = new char[1024];
			try {
				Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				int n;
				while ((n = reader.read(buffer)) != -1) {
					writer.write(buffer, 0, n);
				}
			} finally {
				is.close();
			}
			return writer.toString();
		} else {
			return "";
		}
	}

	public static int canDisplayOtherLine(Activity activity) {

		int display = 0;

		DisplayMetrics metrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);

		float widthInInches = metrics.widthPixels / metrics.xdpi;
		float heightInInches = metrics.heightPixels / metrics.ydpi;

		double sizeInInches = Math.sqrt(Math.pow(widthInInches, 2) + Math.pow(heightInInches, 2));


		if (sizeInInches >= 3.5) {
			display = 1;
		} else if (sizeInInches >= 4.5) {
			display = 2;
		}

		return display;
	}

	/**
	 * Convert a view to bitmap
	 */
	public static Bitmap createDrawableFromView(Context context, View view) {
		DisplayMetrics displayMetrics = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		view.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
		view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
		view.buildDrawingCache();
		Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

		Canvas canvas = new Canvas(bitmap);
		view.draw(canvas);

		return bitmap;
	}

	public static void showServerErrorAlert(Activity activity, String wait) {
		LibraryUtil.showDialog(activity, wait, "");
	}

	public static void createDefaultDialog(Activity activity, ProgressDialog dialog, String wait) {
		dialog = ProgressDialog.show(activity, "", wait, true, true);
	}

	public static void dismissDialogOrActionBarProgress(Activity activity, ProgressDialog dialog, boolean showActionbarProgress) {
		LibraryUtil.dismissDialog(dialog);
	}

	public static void CopyStream(InputStream is, OutputStream os) {
		final int buffer_size = 1024;
		try {
			byte[] bytes = new byte[buffer_size];
			for (;;) {
				int count = is.read(bytes, 0, buffer_size);
				if (count == -1)
					break;
				os.write(bytes, 0, count);
			}
		} catch (Exception ex) {
		}
	}

	public final static boolean isValidEmail(CharSequence target) {
		if (target == null) {
			return false;
		} else {
			return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
		}
	}

	public static Drawable mirrorImage(Activity activity, Drawable d) {
		Matrix matrix = new Matrix();
		matrix.preScale(-1.0f, 1.0f);

		Bitmap bmp = Bitmap.createBitmap(((BitmapDrawable) d).getBitmap(), 0, 0, ((BitmapDrawable) d).getBitmap().getWidth(), ((BitmapDrawable) d).getBitmap().getHeight(), matrix, false);
		return new BitmapDrawable(activity.getResources(), bmp);
	}

	public static ProgressDialog dialog;

	public static void createDefaultDialog(Activity activity, String wait) {
		if (activity == null)
			return;

		dialog = ProgressDialog.show(activity, "", wait, true, true);
		dialog.setCancelable(false);
	}

	public static void dismissDialogOrActionBarProgress(Activity activity) {
		LibraryUtil.dismissDialog(dialog);
	}

	public static int getDrawable(Activity activity, String name) {
		return activity.getResources().getIdentifier(name, "drawable", activity.getPackageName());
	}

	public static int getString(Activity activity, String name) {
		return activity.getResources().getIdentifier(name, "values", activity.getPackageName());
	}

	public static String version() {

		switch (android.os.Build.VERSION.SDK_INT) {
		case android.os.Build.VERSION_CODES.CUPCAKE:
			return "Android 1.5 - CupCake.";
		case android.os.Build.VERSION_CODES.DONUT:
			return "Android 1.6 - Donut.";
		case android.os.Build.VERSION_CODES.ECLAIR:
			return "Android 2.0 - Eclair.";
		case android.os.Build.VERSION_CODES.FROYO:
			return "Android 2.2 - Froyo.";
		case android.os.Build.VERSION_CODES.GINGERBREAD:
			return "Android 2.3 - Gingerbread.";
		case android.os.Build.VERSION_CODES.GINGERBREAD_MR1:
			return "Android 2.3.3 - Gingerbread.";
		case android.os.Build.VERSION_CODES.HONEYCOMB:
			return "Android 3.0 - HoneyComb.";
		case android.os.Build.VERSION_CODES.HONEYCOMB_MR1:
			return "Android 3.1 - HoneyComb Mr1.";
		case android.os.Build.VERSION_CODES.HONEYCOMB_MR2:
			return "Android 3.2 - HoneyComb Mr2.";
		case android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH:
			return "Android 4.0 - Ice Cream Sandwich.";
		case android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1:
			return "Android 4.0.3 - Ice Cream Sandwich Mr1.";
		case android.os.Build.VERSION_CODES.JELLY_BEAN:
			return "Android 4.1 - Jelly Bean.";
		case android.os.Build.VERSION_CODES.JELLY_BEAN_MR1:
			return "Android 4.2: Moar Jelly Beans!";
//		case android.os.Build.VERSION_CODES.JELLY_BEAN_MR2:
//			return "Android 4.3: Jelly Bean MR2, the revenge of the beans.";
		}
		return "N/A";
	}
}