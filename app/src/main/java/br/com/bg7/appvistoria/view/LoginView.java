package br.com.bg7.appvistoria.view;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.EditText;

import br.com.bg7.appvistoria.R;

/**
 * Created by elison on 10/07/17.
 */

public class LoginView extends ConstraintLayout {

    private EditText editTextUser;
    private EditText editTextPassword;
    private Button buttonLogin;


    public LoginView(Context context) {
        super(context);

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.activity_login, this, true);

        editTextUser = findViewById(R.id.editText_user);
        editTextPassword = findViewById(R.id.editText_password);
        buttonLogin = findViewById(R.id.button_login);
    }

    public String getEditTextPassword() {
        return editTextPassword.getText().toString();
    }

    public String getEditTextUser() {
        return editTextUser.getText().toString();
    }

    public void setButtonLoginListener(OnClickListener listener) {
        buttonLogin.setOnClickListener(listener);
    }
}
