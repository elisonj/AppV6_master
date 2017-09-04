package br.com.bg7.appvistoria.product;

import android.os.Bundle;

import br.com.bg7.appvistoria.BaseActivity;

/**
 * Created by: elison
 * Date: 2017-09-04
 */
public class ProductActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProductView view = new ProductView(this);
        setContentView(view);
    }

}
