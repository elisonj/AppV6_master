package br.com.bg7.appvistoria.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.EditText;

import br.com.bg7.appvistoria.R;

/**
 * Created by: elison
 * Date: 2017-07-10
 */

public class LoginView extends BaseView {

    private EditText textUser;
    private EditText textPassword;
    private Button buttonLogin;


    public LoginView(Context context) {
        super(context);

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.activity_login, this, true);

        textUser = findViewById(R.id.editText_user);
        textPassword = findViewById(R.id.editText_password);
        buttonLogin = findViewById(R.id.button_login);
    }

    public String getTextPassword() {
        return textPassword.getText().toString();
    }

    public String getTextUser() {
        return textUser.getText().toString();
    }

    public void setButtonLoginListener(OnClickListener listener) {
        buttonLogin.setOnClickListener(listener);
    }
}
