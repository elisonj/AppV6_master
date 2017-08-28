package br.com.bg7.appvistoria.login;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.com.bg7.appvistoria.BaseActivity;
import br.com.bg7.appvistoria.R;
import br.com.bg7.appvistoria.config.ConfigActivity;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by: luciolucio
 * Date: 2017-07-14
 */

public class LoginView extends ConstraintLayout implements LoginContract.View {
    private LoginContract.Presenter loginPresenter;

    private View.OnClickListener confirmButton;
    private Typeface robotoMedium = Typeface.createFromAsset(getContext().getAssets(),"robotomedium.ttf");

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

        confirmButton = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((BaseActivity)getContext()).dialog.dismiss();
            }
        };

        username.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                username.setTypeface(robotoMedium);
                if(!b) {
                    username.setTextColor(Color.parseColor("#474d51"));
                    return;
                }
                username.setTextColor(Color.parseColor("#00539b"));
            }
        });

        password.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                password.setTypeface(robotoMedium);
                if(!b) {
                    password.setTextColor(Color.parseColor("#474d51"));
                    return;
                }
                password.setTextColor(Color.parseColor("#00539b"));
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

    private void showError(String message) {
        showMessage(getContext().getString(R.string.error), message);
    }

    private void showMessage(String title, String message) {
        ((BaseActivity)getContext()).showAlertDialog(message, confirmButton);
    }
}
