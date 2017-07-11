package br.com.bg7.appvistoria.core;

/**
 * Created by elison on 27/06/17.
 */


import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.wslibrary.bg7.ws.LibraryUtil;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

/**
 * Created by Elisonj on 21/06/2017.
 */

public class Util {

    public static final String TAG = "AppVistoria";
    public static boolean isInDebugMode = true; // habilita o log
    public static BufferedWriter out;


    /**
     * Get current Date in format yyyy-MM-dd-HH:mm:ss
     * @param date
     * @return
     */
    public static String getDateTime(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss", new Locale("pt", "BR"));
        return dateFormat.format(date);
    }


    /**
     * Load the model test json from assets folder
     * @return
     */
    public static String loadJSONFromAsset(Activity activity) {
        String json = null;
        try {
            InputStream is = activity.getAssets().open("model.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }


    /**
     * Get all Images stored
     * @param context
     * @param finalBitmap
     */
    public static File[] getPhotos(Context context) {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "AppVistoria");

        File[] files = mediaStorageDir.listFiles();
        return files;
    }



    /**
     * Save a bitmap in Internal Storage
     * @param context
     * @param finalBitmap
     */
    public static void saveToInternalStorage(Context context, Bitmap finalBitmap){
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "AppVistoria");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                LibraryUtil.Log.d( "Util.class - failed to create directory");
            }
        }

        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-"+ n +".jpg";
        File file = new File (mediaStorageDir, fname);
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Show alert with ok button
     */
    public static void showGenericAlertOK(final Activity activity, final String title, final String msg) {

        try{
            if (activity != null && title != null && msg != null && !msg.equals("null")) {
                Builder dialog= new Builder(activity);

                dialog.setTitle(title);
                dialog.setMessage(msg);

                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) { }
                });
                dialog.show();
            }
        } catch (Exception ex) {
            LibraryUtil.Log.i("Error open GenericAlert with msg: " + msg);
            LibraryUtil.Log.i(ex.getMessage());
        }
    }
}