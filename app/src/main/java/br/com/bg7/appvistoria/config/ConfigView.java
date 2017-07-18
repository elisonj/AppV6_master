package br.com.bg7.appvistoria.config;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import br.com.bg7.appvistoria.R;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by: luciolucio
 * Date: 2017-07-17
 */

public class ConfigView extends ConstraintLayout implements ConfigContract.View {
    ConfigContract.Presenter configPresenter;

    private LinearLayout topLanguages;
    private LinearLayout syncronize;
    private LinearLayout languages;
    private LinearLayout buttons;
    private CheckBox wifi;
    private Button cancel;
    private Button confirm;

    public ConfigView(Context context) {
        super(context);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.fragment_config, this);

        syncronize = findViewById(R.id.linear_wifi);
        languages = findViewById(R.id.linear_language);
        topLanguages = findViewById(R.id.linear_language_top);
        buttons = findViewById(R.id.linear_buttons);
        wifi = findViewById(R.id.checkBox_wifi);
        cancel = findViewById(R.id.button_cancel);
        confirm = findViewById(R.id.button_confirm);
    }

    @Override
    public void setPresenter(ConfigContract.Presenter presenter) {
        configPresenter = checkNotNull(presenter);
    }
}
