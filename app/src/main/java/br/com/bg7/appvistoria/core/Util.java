package br.com.bg7.appvistoria.core;

/**
 * Created by elison on 27/06/17.
 */


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

/**
 * Created by Elisonj on 21/06/2017.
 */

public class Util {



    public static final String TAG = "AppVistoria";
    public static boolean isInDebugMode = true; // habilita o log


    /*
     * Classe usada para efetuar o log na aplica��o
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
     *  Method to decode an URI image to a JPEG - 80%
     * @param activity
     * @param uri
     * @return
     */
    public static Bitmap getJpgCompressed(Activity activity, Uri uri) {
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), uri);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);
            Bitmap decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(out.toByteArray()));
            return decoded;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * Get current Date in format yyyy-MM-dd-HH:mm:ss
     * @param date
     * @return
     */
    public static String getDateTime(Date date) {
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
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
     * Save a bitmap in Internal Storage
     * @param context
     * @param finalBitmap
     */
    public static void saveToInternalStorage(Context context, Bitmap finalBitmap){


        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/saved_images");
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-"+ n +".jpg";
        File file = new File (myDir, fname);
        if (file.exists ()) file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }


//        ContextWrapper cw = new ContextWrapper(context);
//        // path to /data/data/yourapp/app_data/imageDir
//        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
//        // Create imageDir
//
//        Date today = null;
//        Calendar cal = Calendar.getInstance();
//        today = cal.getTime();
//
//        File mypath=new File(directory,getDateTime(today)+".jpg");
//
//        FileOutputStream fos = null;
//        try {
//            fos = new FileOutputStream(mypath);
//            // Use the compress method on the BitMap object to write image to the OutputStream
//            bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, fos);
//
//            if(fos != null) {
//                fos.close();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//        }
//        return directory.getAbsolutePath();
    }





}