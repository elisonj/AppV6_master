package br.com.bg7.appvistoria;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;

import br.com.bg7.appvistoria.core.Util;
import br.com.bg7.appvistoria.database.ProductDB;
import pl.brightinventions.slf4android.FileLogHandlerConfiguration;
import pl.brightinventions.slf4android.LoggerConfiguration;


/**
 * Created by Elisonj on 21/06/2017.
 */
public class MainActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_STORAGE = 1;
    private String[] storage_permissions =
            {
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            };

    private TextView qtdPhotos;
    private MainController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        controller = new MainController(MainActivity.this);

        requestPermissionUser();
        qtdPhotos = (TextView) findViewById(R.id.textView_qtd_imagens);
        controller.updateCountPhotos();
        qtdPhotos.setText(""+controller.getQtdImagesFolder());
        controller.getItemsFromDB();
        controller.login();

        final Button btClick = (Button) findViewById(R.id.button_add_item);
        final Button btSync = (Button) findViewById(R.id.button_syncronize);
        final Button btPhoto = (Button) findViewById(R.id.button_add_photo);
        final EditText editTextQtdPhotos = (EditText) findViewById(R.id.editText_qtd_imagens);

        if(btClick != null) {       //  Execute action for add_item
            btClick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String json = Util.loadJSONFromAsset(MainActivity.this);
                    if(json != null) {
                        ProductDB db = new ProductDB();
                        db.save(json);
                        controller.getItemsFromDB();
                    }
                }
            });
        }
        if(btPhoto != null) {       // Execute action to create more images
            btPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(editTextQtdPhotos.getText().toString() != null && editTextQtdPhotos.getText().toString().length() > 0) {
                        int add = Integer.parseInt(editTextQtdPhotos.getText().toString());
                        controller.setQtdImagesFolder(controller.getQtdImagesFolder() + add);
                        controller.createDefaultImage(add);
                        controller.updateCountPhotos();
                    }
                }
            });
        }
        if(btSync != null) {        //  execute action for syncronize
            btSync.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    controller.syncronizeDB();
                }
            });
        }

        FileLogHandlerConfiguration fileHandler = LoggerConfiguration.fileLogHandler(this);
        String YYYYMMDD = LocalDate.now().format(DateTimeFormatter.ISO_DATE);
        fileHandler.setFullFilePathPattern(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES).getPath() + "/log_app_vistoria_"+YYYYMMDD+".%g.log.txt");
        fileHandler.setLevel(Level.INFO);
        LoggerConfiguration.configuration().addHandlerToRootLogger(fileHandler);
    }

    public TextView getQtdPhotos() {
        return qtdPhotos;
    }

    /**
     *  Request permissions to user to controlle storage in app
     */
    private void requestPermissionUser() {

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
                            }
                        });
                        builder.show();
                    } else {
                        ActivityCompat.requestPermissions(this, storage_permissions, 0);
                    }
                } else {
                    ActivityCompat.requestPermissions(this,
                            storage_permissions,
                            MY_PERMISSIONS_REQUEST_STORAGE);
                }
            }
        }
    }


}