package br.com.bg7.appvistoria;

/**
 * Created by: elison
 * Date: 2017-07-11
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
