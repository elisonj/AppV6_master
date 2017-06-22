package br.com.bg7photospoc;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

import br.com.bg7photospoc.core.Util;


/**
 * Created by Elisonj on 21/06/2017.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isStoragePermissionGranted();

        Button btClick = (Button) findViewById(R.id.button_select_photo);
        if(btClick != null) {

            btClick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    onSelectImageClick(view);
                }
            });
        }
    }

    /**
     * Start pick image activity with chooser.
     */
    public void onSelectImageClick(View view) {
        CropImage.activity(null)
                .setMaxCropResultSize(1200,900)         // max size:  1200 x 900
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // handle result of CropImageActivity
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                Bitmap bmp = Util.getJpgCompressed(this, result.getUri());  // Convert to JPEG encode and compress to 80%

                if(bmp != null) {

                    if(isStoragePermissionGranted()){
                        //saveImage(bmp);
                        Util.saveToInternalStorage(this, bmp);
                    }

                    ((ImageView) findViewById(R.id.quick_start_cropped_image)).setImageBitmap(bmp);
                    Toast.makeText(this, "Redimencionamento realizado com sucesso!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Erro ao efetuar a compressão da imagem", Toast.LENGTH_LONG).show();
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, "Erro ao efetuar o Redimencionamento: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     *  Requires permission to save in storage, if hasn't ask for it.
     * @return boolean
     */
    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("BG7","Permission is granted");
                return true;
            } else {

                Log.v("BG7","Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("BG7","Permission is granted");
            return true;
        }
    }

}
