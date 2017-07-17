package br.com.bg7.appvistoria.view.listeners;

import android.view.View;

import br.com.bg7.appvistoria.view.ConfigView;

/**
 * Created by: elison
 * Date: 2017-07-14
 */
public class CheckBoxWifiListener implements View.OnClickListener{

    boolean checked = true;
    ConfigView view;

    public CheckBoxWifiListener(ConfigView view) {
        this.view = view;
    }

    @Override
    public void onClick(View v) {
        view.getButtons().setVisibility(View.VISIBLE);
        if(checked) {
            checked = false;
        } else {
            checked = true;
        }
        view.getWifi().setChecked(checked);
    }
}
