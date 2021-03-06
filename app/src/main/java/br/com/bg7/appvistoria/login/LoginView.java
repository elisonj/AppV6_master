package br.com.bg7.appvistoria.login;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.com.bg7.appvistoria.AlertDialog;
import br.com.bg7.appvistoria.ProgressDialog;
import br.com.bg7.appvistoria.R;
import br.com.bg7.appvistoria.config.ConfigActivity;

import static br.com.bg7.appvistoria.Constants.FONT_NAME_ROBOTO_MEDIUM;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by: elison
 * Date: 2017-07-14
 */

public class LoginView extends ConstraintLayout implements LoginContract.View {
    private LoginContract.Presenter loginPresenter;

    private Typeface robotoMedium = Typeface.createFromAsset(getContext().getAssets(), FONT_NAME_ROBOTO_MEDIUM);

    private ProgressDialog progress;


    public LoginView(Context context) {
        super(context);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.activity_login, this);

        progress = new ProgressDialog(getContext());

        final EditText username = findViewById(R.id.editText_user);
        final EditText password = findViewById(R.id.editText_password);
        Button buttonLogin = findViewById(R.id.button_login);
        username.setTypeface(robotoMedium);
        password.setTypeface(robotoMedium);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginPresenter.login(username.getText().toString(), password.getText().toString());
            }
        });

        username.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    username.setTextColor(getResources().getColor(R.color.edit_highlight, null));
                    return;
                }

                username.setTextColor(getResources().getColor(R.color.edit_default, null));
            }
        });

        password.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {

                if (hasFocus) {
                    password.setTextColor(getResources().getColor(R.color.edit_highlight, null));
                    return;
                }
                password.setTextColor(getResources().getColor(R.color.edit_default, null));
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
        showError(getContext().getString(R.string.validation_user_field_not_empty));
    }

    @Override
    public void showPasswordEmptyError() {
        showError(getContext().getString(R.string.validation_password_field_not_empty));
    }

    @Override
    public void showCannotLoginError() {
        showError(getContext().getString(R.string.cannot_login_error));
    }

    @Override
    public void showMainScreen() {
        Intent intent = new Intent(getContext(), ConfigActivity.class);
        getContext().startActivity(intent);
    }

    @Override
    public void showBadCredentialsError() {
        showError(getContext().getString(R.string.bad_credentials_error));
    }

    @Override
    public void showLoading() {
        progress.show();
    }

    @Override
    public void hideLoading() {
        progress.hide();
    }

    private void showError(String message) {
        AlertDialog dialog = new AlertDialog(getContext(), message);
        dialog.show();
    }
}
