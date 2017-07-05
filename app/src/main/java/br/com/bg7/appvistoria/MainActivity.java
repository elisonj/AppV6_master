package br.com.bg7.appvistoria;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.wslibrary.bg7.ws.GetRequestUITask;
import com.wslibrary.bg7.ws.LibraryUtil;
import com.wslibrary.bg7.ws.Parameter;
import com.wslibrary.bg7.ws.SBCommands;
import com.wslibrary.bg7.ws.WSCallBack;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import br.com.bg7.appvistoria.core.Util;
import br.com.bg7.appvistoria.database.ProductDB;
import br.com.bg7.appvistoria.vo.Product;


/**
 * Created by Elisonj on 21/06/2017.
 */
public class MainActivity extends AppCompatActivity {

    private ListView listView;
    public static final String WS_BASE_URL = "https://preapi.s4bdigital.net/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createDefaultImage();

        getItemsFromDB();

        login();


        Button btClick = (Button) findViewById(R.id.button_add_item);
        Button btSync = (Button) findViewById(R.id.button_syncronize);

        if(btClick != null) {       //  Execute action for add_item

            btClick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String json = Util.loadJSONFromAsset(MainActivity.this);
                    if(json != null) {
                        Util.Log.i("Json carregado");
                        ProductDB db = new ProductDB();
                        db.save(json);
                        getItemsFromDB();
                    }

                }
            });
        }

        if(btSync != null) {        //  execute action for syncronize
            btSync.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    syncronizeDB();
                }
            });
        }
    }


    /**
     *  Get all data from local Database and send to Server
     */
    private void syncronizeDB() {

        ProductDB db = new ProductDB();

        List<Product> items = db.getAll();
        if(items != null && items.size() > 0) {

            for(Product product: items) {
                if (product.toJSON() != null) {
                    syncronizeProduct(product.toJSON());
                }
            }
        }
    }


    /**
     * Execute request to send all image files
     */
    private void syncronizeImages(Product obj) {

        File[] files = Util.getPhotos(MainActivity.this);

        for (File file: files) {

            if(LibraryUtil.getMimeType(file.getAbsolutePath()).equalsIgnoreCase("image/jpeg")) {
                CallbackSendImage callback = new CallbackSendImage(file);

                List<Parameter> params = new ArrayList<Parameter>();
                params.add(new Parameter<String, File>("image", files[0]));

                LibraryUtil.Log.file("Syncronizing file " + files[0].getName() + " - Product: " + obj.getProductId());
                Applic.getInstance().getWsRequests().requestGetWS(this, SBCommands.PREFIX_SEND_IMAGE.getValue() + obj.getProductId() + SBCommands.CMD_SEND_IMAGE.getValue(), callback, params, GetRequestUITask.HttpMethod.POST);
            }
        }
    }


    class CallbackSendImage implements WSCallBack {

        File file = null;

        public CallbackSendImage(File file) {
            this.file = file;
        }

        @Override
        public void onResult(final JSONObject result) {

            Util.Log.i("Executou retorno envio da imagem");

            if (result != null) {
                try {
                    Util.Log.i("RESULT: "+ result.toString());

                    if(result.has("total") && result.getInt("total") > 0) {
                        String filename = file.getName();
                   /*     boolean deleted = file.delete();
                        if(deleted) {
                            LibraryUtil.Log.file("File: "+ filename + "delected");
                        }
                        */
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }


    /**
     * Execute request to send all Products in database
     */
    private void syncronizeProduct(JSONObject obj) {

        CallbackProduct callback = new CallbackProduct();
        List<Parameter> params = new ArrayList<Parameter>();
        params.add(new Parameter<String, String>("body", obj.toString()));

        Applic.getInstance().getWsRequests().requestGetWS(this, SBCommands.CMD_SEND_PRODUCTS.getValue(), callback,  params, GetRequestUITask.HttpMethod.POST);

    }


    /**
     * Execute request OAuth and wait a token as result
     */
    private void login() {

        CallbackLogin callback = new CallbackLogin();

        List<Parameter> params = new ArrayList<Parameter>();
        params.add(new Parameter<String, String>("grant_type", "password"));
        params.add(new Parameter<String, String>("client_id", "61922-5b5d19794c749cfbc5d20becc3826e08-s4b-cda-app"));
        params.add(new Parameter<String, String>("username", "unicornio"));
        params.add(new Parameter<String, String>("password", "unicornio"));

        Applic.getInstance().getWsRequests().requestGetWS(this, SBCommands.CMD_OAUTH.getValue(), callback,  params, GetRequestUITask.HttpMethod.POST);

    }

    /**
     * Return of Product Send request
     */
    class CallbackProduct implements WSCallBack {
        @Override
        public void onResult(final JSONObject result) {

            Util.Log.i("Executou retorno do Produto - CallbackProduct");

            if (result != null) {
                try {
                    Util.Log.i("RESULT: " + result.toString());
                    Product p =  Product.fromJson(result);

                    syncronizeImages(p);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * Return of Login request -  Save callback in Application class
     */
    class CallbackLogin implements WSCallBack {
        @Override
        public void onResult(final JSONObject result) {

            Util.Log.i("Executou retorno do Login - CallbackLogin");

            if (result != null) {
                try {
                    Util.Log.i("RESULT: " + result.toString());
                    Applic.getInstance().setToken(result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
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

    private static final int MY_PERMISSIONS_REQUEST_STORAGE = 1;
    private String[] storage_permissions =
            {
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            };

    private void createDefaultImage() {



        if ( Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE) && ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE) && ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
                        builder.setMessage("To get storage access you have to allow us access to your sd card content.");
                        builder.setTitle("Storage");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(MainActivity.this, storage_permissions, 0);
                               // onContactsClick();
                            }
                        });

                        builder.show();
                    } else {
                        ActivityCompat.requestPermissions(this, storage_permissions, 0);
                      //  onContactsClick();
                    }
                } else {
                    ActivityCompat.requestPermissions(this,
                            storage_permissions,
                            MY_PERMISSIONS_REQUEST_STORAGE);
                   // onContactsClick();
                }

            }
        }


        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "AppVistoria");

       // File mediaStorageDir = Environment.getExternalStoragePublicDirectory(
       //         Environment.DIRECTORY_PICTURES);
      //  File mediaStorageDir = getDir(Applic.KEY_IMAGE_FOLDER, Context.MODE_PRIVATE);

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Util.Log.d( "failed to create directory");
            }
        }

        Bitmap photo = BitmapFactory.decodeResource(getResources(),
                R.drawable.androidjp);

        Util.saveToInternalStorage(this, photo);
    }


}