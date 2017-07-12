package br.com.bg7.appvistoria;

/**
 * Created by elison on 11/07/17.
 */

public class Applic extends com.orm.SugarApp {

    public static final String TAG = "AppVistoria";

    private static Applic instance = null;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
    }

    public static Applic getInstance() {
        return instance;
    }


}
