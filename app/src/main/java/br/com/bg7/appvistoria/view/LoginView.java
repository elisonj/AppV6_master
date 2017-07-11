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

public class LoginView  extends ConstraintLayout {

    private EditText editTextUser;
    private EditText editTextPassword;
    private Button buttonLogin;


    public LoginView(Context context) {
        super(context);

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.activity_login, this, true);

        editTextUser = (EditText) findViewById(R.id.editText_user);
        editTextPassword = (EditText) findViewById(R.id.editText_password);

        buttonLogin = (Button) findViewById(R.id.button_login);
    }

    public EditText getEditTextPassword() {
        return editTextPassword;
    }

    public EditText getEditTextUser() {
        return editTextUser;
    }

    public Button getButtonLogin() {
        return buttonLogin;
    }
}
