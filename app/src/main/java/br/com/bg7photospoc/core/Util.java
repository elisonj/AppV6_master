package br.com.bg7photospoc.core;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

/**
 * Created by Elisonj on 21/06/2017.
 */

public class Util {


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
