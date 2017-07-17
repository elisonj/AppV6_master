package br.com.bg7.appvistoria.service;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.List;

import br.com.bg7.appvistoria.vo.Config;

/**
 * Created by: elison
 * Date: 2017-07-17
 */
public class SyncronizeService {

    /**
     * Verify if the actual connection can sincronize data
     * @return
     */
    public boolean canSyncronize(Activity activity) {
        List<Config> config = Config.listAll(Config.class);
        if(config != null && config.size() > 0) {
            final ConnectivityManager conn = (ConnectivityManager)
                    activity.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetwork = conn.getActiveNetworkInfo();
            if (config.get(0).isUpdateOnlyWifi() && activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                return true;
            }
            if (!config.get(0).isUpdateOnlyWifi() && activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                return true;
            }
            return false;
        }
        return false;
    }
}
