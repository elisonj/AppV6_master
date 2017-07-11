package br.com.bg7.appvistoria;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.widget.ListView;

import com.orm.SugarRecord;
import com.wslibrary.bg7.ws.GetRequestUITask;
import com.wslibrary.bg7.ws.LibraryUtil;
import com.wslibrary.bg7.ws.Parameter;
import com.wslibrary.bg7.ws.SBCommands;
import com.wslibrary.bg7.ws.WSAsyncTaskGeneric;
import com.wslibrary.bg7.ws.WSCallBack;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.com.bg7.appvistoria.core.Util;
import br.com.bg7.appvistoria.database.ProductDB;
import br.com.bg7.appvistoria.vo.Product;
import br.com.bg7.appvistoria.vo.Properties;
import br.com.bg7.appvistoria.vo.Property;
import br.com.bg7.appvistoria.vo.PropertyList;

/**
 * Created by elison on 06/07/17.
 */

public class MainController {

    private MainActivity activity;
    private ListView listView;

    private static final Logger logger = LoggerFactory.getLogger(MainController.class.getSimpleName());

    private static ArrayList<Parameter<String, File>> filesToSend = new ArrayList<>();
    private static ArrayList<Product> productsToSend = new ArrayList<Product>();
    int qtdImagesFolder = 0;

    public MainController(MainActivity activity) {
        this.activity = activity;
    }

    public int getQtdImagesFolder() {
        return qtdImagesFolder;
    }

    public void setQtdImagesFolder(int qtdImagesFolder) {
        this.qtdImagesFolder = qtdImagesFolder;
    }

    /**
     * Execute request to send all Products in database
     */
    private void syncronizeProduct(LocalDateTime start) {
        if(productsToSend.size() > 0) {
            Product product = productsToSend.get(0);
            if (product != null) {
                JSONObject obj = product.toJSON();
                CallbackProduct callback = new CallbackProduct(start);
                List<Parameter> params = new ArrayList<Parameter>();
                params.add(new Parameter<String, String>("body", obj.toString()));
                Applic.getInstance().getWsRequests().requestGetWS(activity, SBCommands.CMD_SEND_PRODUCTS.getValue(), callback, params, GetRequestUITask.HttpMethod.POST);
            }
        }
    }

    /**
     * Execute request OAuth and wait a token as result
     */
    protected void login() {
        CallbackLogin callback = new CallbackLogin();
        List<Parameter> params = new ArrayList<Parameter>();
        params.add(new Parameter<String, String>("grant_type", "password"));
        params.add(new Parameter<String, String>("client_id", "61922-5b5d19794c749cfbc5d20becc3826e08-s4b-cda-app"));
        params.add(new Parameter<String, String>("username", "unicornio"));
        params.add(new Parameter<String, String>("password", "unicornio"));
        Applic.getInstance().getWsRequests().requestGetWS(activity, SBCommands.CMD_OAUTH.getValue(), callback,  params, GetRequestUITask.HttpMethod.POST);
    }

    /**
     * Return of Product Send request
     */
    class CallbackProduct implements WSCallBack {
        private LocalDateTime start;

        public CallbackProduct(LocalDateTime start) {
            this.start = start;
        }

        @Override
        public void onResult(final JSONObject result) {
            if (result != null) {
                try {
                    LibraryUtil.Log.i("RESULT - CallbackProduct: " + result.toString());
                    Product p =  Product.fromJson(result);
                    if (p != null) {
                        logger.info("Produto: {}", p.getProductId());
                    }
                    syncronizeImages(p, start);
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
            if (result != null) {
                try {
                    LibraryUtil.Log.i("RESULT - CallbackLogin: " + result.toString());
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
    protected void getItemsFromDB() {
        listView = (ListView) activity.findViewById(R.id.listView);
        List<Product> items = Product.listAll(Product.class);
        MainDBAdapter adapter = new MainDBAdapter(activity, items);
        listView.setAdapter(adapter);
    }

    /**
     * Create a number of images based in the number choose by user
     */
    protected void createDefaultImage(int add) {
        new SaveImagesLocalAsyncTask(add).execute();
    }

    /**
     *  Get all data from local Database and send to Server
     */
    protected void syncronizeDB() {
        ProductDB db = new ProductDB();
        List<Product> items = db.getAll();

        logger.info("Inicio sync", items.size());
        LocalDateTime now = LocalDateTime.now();
        if(items.size() > 0) {
            logger.info("Produtos: {}", items.size());
            logger.info("Imagens: {}", qtdImagesFolder);

            for(Product product: items) {
                if (product.toJSON() != null) {
                    productsToSend.add(product);
                }
            }
            syncronizeProduct(now);
        }
        else {
            logger.info("Fim sync", items.size());
        }
    }

    /**
     * Execute request to send all image files
     */
    private void syncronizeImages(Product obj, LocalDateTime start) {
        filesToSend.clear();
        File[] files = Util.getPhotos(activity);
        for (File file: files) {
            filesToSend.add(new Parameter<String, File>(file.getName(), file));
        }
        LibraryUtil.Log.file(" ****   Start Syncronization ****      Qtd files: "+ qtdImagesFolder+ "");
        sendFileToServer(obj, start);
    }


    private void sendFileToServer(Product obj, LocalDateTime start) {
        if(filesToSend != null && filesToSend.size() > 0) {
            Parameter<String, File> param = filesToSend.get(0);
            File file = param.getValue();
            if (LibraryUtil.getMimeType(file.getAbsolutePath()).equalsIgnoreCase("image/jpeg")) {  // if is a jpeg file, send to server
                CallbackSendImage callback = new CallbackSendImage(obj, file, start);
                List<Parameter> params = new ArrayList<Parameter>();
                params.add(new Parameter<String, File>("image", file));
                LibraryUtil.Log.file("Syncronizing file " + file.getName() + " - Product: " + obj.getProductId());
                Applic.getInstance().getWsRequests().requestGetWS(activity, SBCommands.PREFIX_SEND_IMAGE.getValue() + obj.getProductId() + SBCommands.CMD_SEND_IMAGE.getValue(), callback, params, GetRequestUITask.HttpMethod.POST);
            } else {
                filesToSend.remove(0);      //  else, remove from list
                sendFileToServer(obj, start);
            }
        } else {   //  IF Send all files
            LibraryUtil.Log.file("End of syncronizing files ");
            ProductDB db = new ProductDB();    //  Remove actual product
            if(db.delete(obj)) {
                LibraryUtil.Log.i("  Produto deletado");
            } else {
                LibraryUtil.Log.i("  Produto nao deletado");
            }
            productsToSend.remove(0);
            if(productsToSend.size() == 0) {
                LocalDateTime now = LocalDateTime.now();
                logger.info("Millis: {}", Duration.between(start, now).toMillis());
                logger.info("Fim sync");
                SugarRecord.deleteAll(Product.class);
                SugarRecord.deleteAll(Properties.class);
                SugarRecord.deleteAll(Property.class);
                SugarRecord.deleteAll(PropertyList.class);
            }

            getItemsFromDB();                   //  Update ListView
            syncronizeProduct(start);           //  If has more products, sent it to server
            updateCountPhotos();                //  update count photos in screen
        }
    }

    /**
     * Update Count photos in screen
     */
    protected void updateCountPhotos() {

        qtdImagesFolder = 0;
        File[] files = Util.getPhotos(activity);
        if(files != null) {
            for (File file : files) {
                if (LibraryUtil.getMimeType(file.getAbsolutePath()).equalsIgnoreCase("image/jpeg")) {
                    qtdImagesFolder++;
                }
            }
            if(activity.getQtdPhotos() != null) {
                activity.getQtdPhotos().setText(""+qtdImagesFolder);
            }
        }
    }


    class CallbackSendImage implements WSCallBack {

        File file = null;
        Product product = null;
        LocalDateTime start;

        public CallbackSendImage(Product product, File file, LocalDateTime start) {
            this.product = product;
            this.file = file;
            this.start = start;
        }

        @Override
        public void onResult(final JSONObject result) {
            if (result != null) {
                try {
                    LibraryUtil.Log.i("RESULT - CallbackSendImage: "+ result.toString());
                    if(result.has("total") && result.getInt("total") > 0) {
                        String filename = file.getName();
                        filesToSend.remove(0);
                        boolean deleted = file.delete();
                        if(deleted) {
                            LibraryUtil.Log.file("-- Send File OK: "+ filename + " delected");
                        }
                        sendFileToServer(product, start);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }



    private class SaveImagesLocalAsyncTask extends WSAsyncTaskGeneric<Object, Void, JSONObject> {
        private int add = 0;
        public SaveImagesLocalAsyncTask(int add) {
            this.add = add;
        }

        @Override
        protected JSONObject doInBackground(Object... params) {
            File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES), "AppVistoria");
            if (!mediaStorageDir.exists()) {
                if (!mediaStorageDir.mkdirs()) {
                    LibraryUtil.Log.d( "failed to create directory");
                }
            }
            Bitmap photo = BitmapFactory.decodeResource(activity.getResources(),
                    R.drawable.androidjp);
            if(add > 0) {                               //  iterate the number and create a new file
                for(int i=0; i<add; i++) {
                    Util.saveToInternalStorage(activity, photo);
                }
                Util.showGenericAlertOK(activity, "Sucesso!", "Imagens adicionadas para envio.");
            }
            return null;
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            dismissDialogOrActionBarProgress(activity);
            updateCountPhotos();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            createDefaultDialog(activity);
            LibraryUtil.registerDialog(dialog);
        }
    }

}
