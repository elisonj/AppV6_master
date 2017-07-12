package br.com.bg7.appvistoria.view.listeners;

import android.text.TextUtils;
import android.view.View;

import br.com.bg7.appvistoria.R;
import br.com.bg7.appvistoria.activity.LoginActivity;
import br.com.bg7.appvistoria.service.LoginService;
import br.com.bg7.appvistoria.view.LoginView;

/**
 * Created by: elison
 * Date: 2017-07-11
 */
public class ButtonLoginListener implements View.OnClickListener {

    private LoginView view;
    private LoginActivity activity;

    public ButtonLoginListener(LoginActivity activity, LoginView view) {
        this.activity = activity;
        this.view = view;
    }

    @Override
    public void onClick(View v) {
        if (fieldsAreValid()) {
            LoginService service = new LoginService();
            service.requestToken(view.getTextUser(), view.getTextPassword());
        }
    }

    /**
     * Validate Login fields
     * @return
     */
    private boolean fieldsAreValid() {
        if(TextUtils.isEmpty(view.getTextUser())) {
            view.showDialog(activity.getString(R.string.warning), activity.getString(R.string.fill_user));
            return false;
        }
        if(TextUtils.isEmpty(view.getTextPassword())) {
            view.showDialog(activity.getString(R.string.warning), activity.getString(R.string.fill_pass));
            return false;
        }
        return true;
    }
}
