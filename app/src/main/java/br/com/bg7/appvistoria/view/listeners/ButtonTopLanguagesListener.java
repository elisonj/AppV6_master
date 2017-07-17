package br.com.bg7.appvistoria.view.listeners;

import android.view.View;

import br.com.bg7.appvistoria.view.ConfigView;

/**
 * Created by: elison
 * Date: 2017-07-14
 */
public class ButtonTopLanguagesListener implements View.OnClickListener{

    ConfigView view;

    public ButtonTopLanguagesListener(ConfigView view) {
        this.view = view;
    }

    @Override
    public void onClick(View v) {
        view.getButtons().setVisibility(View.VISIBLE);
        if(view.getLanguages().isShown()) {
            view.getLanguages().setVisibility(View.GONE);
        } else {
            view.getLanguages().setVisibility(View.VISIBLE);
        }
    }
}
