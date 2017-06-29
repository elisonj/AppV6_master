package br.com.bg7.appvistoria;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import br.com.bg7.appvistoria.core.Util;
import br.com.bg7.appvistoria.vo.Product;


/**
 * Created by Elisonj on 21/06/2017.
 */
public class MainActivity extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getItemsFromDB();

        Button btClick = (Button) findViewById(R.id.button_add_item);
        if(btClick != null) {

            btClick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String json = Util.loadJSONFromAsset(MainActivity.this);
                    if(json != null) {
                        Util.Log.i("Json carregado");
                        try {
                            Product product = Product.fromJson(new JSONObject(json));
                            if(product != null) {
                                Util.Log.i("Sucesso! "+ product.getProductYourRef());
                                product.save();
                                getItemsFromDB();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                }
            });
        }
    }

    /**
     * Load all data products from DB and update in listView
     */
    private void getItemsFromDB() {

        listView = (ListView) findViewById(R.id.listView);
        List<Product> items = Product.listAll(Product.class);

        MainDBAdapter adapter = new MainDBAdapter(this, items);
        listView.setAdapter(adapter);

    }

}