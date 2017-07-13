package br.com.bg7.appvistoria.view.listeners;

import org.mindrot.jbcrypt.BCrypt;

import java.util.List;

import br.com.bg7.appvistoria.Applic;
import br.com.bg7.appvistoria.R;
import br.com.bg7.appvistoria.view.LoginView;
import br.com.bg7.appvistoria.vo.User;

/**
 * Created by: elison
 * Date: 2017-07-13
 */
public class OfflineLoginCommand {

    public void onClick(LoginView view) {
        List<User> user = User.find(User.class, "user_name = ?", view.getUser());
        if(user != null && user.size() > 0) {
            String passwordHash = user.get(0).getPassword();
            if (BCrypt.checkpw(view.getPassword(), passwordHash)) {
                view.showDialog(Applic.getInstance().getString(R.string.success),
                        Applic.getInstance().getString(R.string.login_offline_success));
            } else {
                view.showDialog(Applic.getInstance().getString(R.string.warning),
                        Applic.getInstance().getString(R.string.validation_password_dont_match));
            }
        } else {
            view.showDialog(Applic.getInstance().getString(R.string.warning),
                    Applic.getInstance().getString(R.string.validation_user_not_found));
        }
    }
}
