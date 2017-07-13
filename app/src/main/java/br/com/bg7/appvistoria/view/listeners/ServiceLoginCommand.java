package br.com.bg7.appvistoria.view.listeners;

import android.text.TextUtils;

import br.com.bg7.appvistoria.Applic;
import br.com.bg7.appvistoria.R;
import br.com.bg7.appvistoria.service.LoginService;
import br.com.bg7.appvistoria.view.LoginView;

/**
 * Created by: elison
 * Date: 2017-07-11
 */
public class ServiceLoginCommand {

    public void onClick(LoginCallback callback, LoginView view) {
        if (fieldsAreValid(view)) {
            LoginService service = new LoginService();
            service.requestToken(callback, view.getUser(), view.getPassword());
        }
    }

    /**
     * Validate Login fields
     * @return return true only if both fields <b>User</b> and <b>Password</b> has values.
     * Case one of than are empty, will be returned false.
     */
    private boolean fieldsAreValid(LoginView view) {
        if(TextUtils.isEmpty(view.getUser())) {
            view.showDialog(Applic.getInstance().getString(R.string.warning), Applic.getInstance().getString(R.string.validation_user_field_not_empty));
            return false;
        }
        if(TextUtils.isEmpty(view.getPassword())) {
            view.showDialog(Applic.getInstance().getString(R.string.warning), Applic.getInstance().getString(R.string.validation_password_field_not_empty));
            return false;
        }
        return true;
    }
}
