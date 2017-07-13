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
            service.requestToken(view);
        }
    }

    /**
     * Validate Login fields
     * @return return true only if both fields <b>User</b> and <b>Password</b> has values.
     * Case one of than are empty, will be returned false.
     */
    private boolean fieldsAreValid() {
        if(TextUtils.isEmpty(view.getUser())) {
            view.showDialog(activity.getString(R.string.warning), activity.getString(R.string.validation_user_field_not_empty));
            return false;
        }
        if(TextUtils.isEmpty(view.getPassword())) {
            view.showDialog(activity.getString(R.string.warning), activity.getString(R.string.validation_password_field_not_empty));
            return false;
        }
        return true;
    }
}
