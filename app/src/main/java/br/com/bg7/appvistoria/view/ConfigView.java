package br.com.bg7.appvistoria.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;

import br.com.bg7.appvistoria.R;
import br.com.bg7.appvistoria.view.listeners.ButtonTopLanguagesListener;
import br.com.bg7.appvistoria.view.listeners.CheckBoxWifiListener;

/**
 * Created by: elison
 * Date: 2017-07-14
 */
public class ConfigView extends BaseView {

    private LinearLayout topLanguages;
    private LinearLayout syncronize;
    private LinearLayout languages;
    private Spinner languageSelected;
    private LinearLayout buttons;
    private CheckBox wifi;
    private Button cancel;
    private Button confirm;


    public ConfigView(Context context) {
        super(context);

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.fragment_config, this, true);

        syncronize = findViewById(R.id.linear_wifi);
        languages = findViewById(R.id.linear_language);
        topLanguages = findViewById(R.id.linear_language_top);
        languageSelected = findViewById(R.id.spinner_language);
        buttons = findViewById(R.id.linear_buttons);
        wifi = findViewById(R.id.checkBox_wifi);
        cancel = findViewById(R.id.button_cancel);
        confirm = findViewById(R.id.button_confirm);
        topLanguages.setOnClickListener(new ButtonTopLanguagesListener(this));
        syncronize.setOnClickListener(new CheckBoxWifiListener(this));
    }

    public LinearLayout getButtons() {
        return buttons;
    }

    public LinearLayout getLanguages() {
        return languages;
    }

    public CheckBox getWifi() {
        return wifi;
    }

    public String getLanguageSelected() {
        return languageSelected.getSelectedItem().toString();
    }

    public void setConfirmListenner(OnClickListener listenner) {
        confirm.setOnClickListener(listenner);
    }

    public void setCancelListenner(OnClickListener listenner) {
        cancel.setOnClickListener(listenner);
    }
}
