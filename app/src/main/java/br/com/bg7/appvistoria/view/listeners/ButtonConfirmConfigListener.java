package br.com.bg7.appvistoria.view.listeners;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;

import java.util.List;

import br.com.bg7.appvistoria.MainActivity;
import br.com.bg7.appvistoria.config.ConfigFragment;
import br.com.bg7.appvistoria.view.ConfigView;
import br.com.bg7.appvistoria.vo.Config;
import br.com.bg7.appvistoria.vo.Country;

/**
 * Created by: elison
 * Date: 2017-07-14
 */
public class ButtonConfirmConfigListener implements View.OnClickListener{

    ConfigView view;
    ConfigFragment frag;

    public ButtonConfirmConfigListener(ConfigFragment frag, ConfigView view) {
        this.frag = frag;
        this.view = view;
    }

    @Override
    public void onClick(View v) {
        view.getButtons().setVisibility(View.GONE);
        view.getLanguages().setVisibility(View.GONE);

        List<Config> list = Config.listAll(Config.class);
        if(list != null && list.size() > 0) {
            Config.deleteAll(Config.class);
        }
        Config config = new Config((view.getLanguageSelected()).getId(), view.getWifi().isChecked());
        config.save();

        changeLanguage();
    }


    private void changeLanguage() {
        Country country = view.getLanguageSelected();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(frag.getContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Language", country.getLanguage());
        editor.apply();

        frag.getActivity().finish();
        frag.getActivity().startActivity(new Intent(frag.getActivity(), MainActivity.class));
    }
}
