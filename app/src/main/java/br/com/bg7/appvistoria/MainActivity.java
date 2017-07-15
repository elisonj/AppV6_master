package br.com.bg7.appvistoria;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import br.com.bg7.appvistoria.login.LoginActivity;

/**
 * Created by: elison
 * Date: 2017-07-10
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);

    }
}
