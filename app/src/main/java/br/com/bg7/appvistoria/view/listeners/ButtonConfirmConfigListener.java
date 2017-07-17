package br.com.bg7.appvistoria.view.listeners;

import android.view.View;

import br.com.bg7.appvistoria.Applic;
import br.com.bg7.appvistoria.R;
import br.com.bg7.appvistoria.view.ConfigView;

/**
 * Created by: elison
 * Date: 2017-07-14
 */
public class ButtonConfirmConfigListener implements View.OnClickListener{

    ConfigView view;

    public ButtonConfirmConfigListener(ConfigView view) {
        this.view = view;
    }

    @Override
    public void onClick(View v) {
        view.getButtons().setVisibility(View.GONE);
        view.getLanguages().setVisibility(View.GONE);
        view.showDialog(Applic.getInstance().getString(R.string.success),
                Applic.getInstance().getString(R.string.config_saved));
    }
}
