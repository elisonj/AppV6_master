package br.com.bg7.appvistoria.login;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.com.bg7.appvistoria.R;
import br.com.bg7.appvistoria.config.ConfigActivity;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by: luciolucio
 * Date: 2017-07-14
 */

public class LoginView extends ConstraintLayout implements LoginContract.View {
    private LoginContract.Presenter loginPresenter;

    public LoginView(Context context) {
        super(context);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.activity_login, this);

        final EditText username = findViewById(R.id.editText_user);
        final EditText password = findViewById(R.id.editText_password);
        Button buttonLogin = findViewById(R.id.button_login);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginPresenter.login(username.getText().toString(), password.getText().toString());
            }
        });
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        loginPresenter = checkNotNull(presenter);
    }

    @Override
    public boolean isConnected() {
        ConnectivityManager connectivtyManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        return connectivtyManager.getActiveNetworkInfo() != null
                && connectivtyManager.getActiveNetworkInfo().isAvailable()
                && connectivtyManager.getActiveNetworkInfo().isConnected();
    }

    @Override
    public void showUsernameEmptyError() {
        showWarning(getContext().getString(R.string.validation_user_field_not_empty));
    }

    @Override
    public void showPasswordEmptyError() {
        showWarning(getContext().getString(R.string.validation_password_field_not_empty));
    }

    @Override
    public void showApplicationError() {
        showWarning(getContext().getString(R.string.application_error));
    }

    @Override
    public void showCannotLoginOfflineError() {
        showWarning(getContext().getString(R.string.validation_user_not_found));
    }

    @Override
    public void showCannotLoginError() {
        showWarning(getContext().getString(R.string.login_error));
    }

    @Override
    public void showMainScreen() {
        Intent intent = new Intent(getContext(), ConfigActivity.class);
        getContext().startActivity(intent);
    }

    @Override
    public void showOfflineLoginSuccess() {
        showSuccess(getContext().getString(R.string.login_offline_success));
    }

    @Override
    public void showWrongPasswordError() {
        showWarning(getContext().getString(R.string.validation_password_dont_match));
    }

    @Override
    public void showUserNotFoundError() {
        showWarning(getContext().getString(R.string.validation_user_not_found));
    }

    private void showWarning(String message) {
        showMessage(getContext().getString(R.string.warning), message);
    }

    private void showSuccess(String message) {
        showMessage(getContext().getString(R.string.success), message);
    }

    private void showMessage(String title, String message) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle(title);
        alert.setMessage(message);
        alert.setPositiveButton("OK",null);
        alert.show();
    }
}
