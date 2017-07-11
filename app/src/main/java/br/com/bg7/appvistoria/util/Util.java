package br.com.bg7.appvistoria.util;

/**
 * Created by elison on 27/06/17.
 */


import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Environment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Elisonj on 21/06/2017.
 */

public class Util {

    public static final String TAG = "AppVistoria";
    public static BufferedWriter out;

    public static boolean isInDebugMode = true; // habilita o log
    public static boolean SHOWLOG_I = true;
    public static boolean SHOWLOG_D = true;
    public static boolean SHOWLOG_E = true;
    public static boolean SHOWLOG_W = true;

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
     * Classe usada para efetuar o log na aplicacao
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

        /**
         * Save log in file system - create one file per day
         * @param log
         */
        public static void file(String log) {
            File Root = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES), "AppVistoria");
            if(Root.canWrite()){
                Calendar cal = Calendar.getInstance();
                String filename = "log_app_vistoria_"+cal.get(Calendar.DAY_OF_MONTH)+cal.get(Calendar.MONTH)+cal.get(Calendar.YEAR)+".txt";
                File  LogFile = new File(Root, filename);
                FileWriter LogWriter = null;
                try {
                    LogWriter = new FileWriter(LogFile, true);
                    out = new BufferedWriter(LogWriter);
                    Date date = new Date();
                    out.write("* Logged at" + String.valueOf(date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds() + " --- " + log + "\n"));
                    out.close();
                    i(log);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
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
            Util.Log.i("Error open GenericAlert with msg: " + msg);
            Util.Log.i(ex.getMessage());
        }
    }
}