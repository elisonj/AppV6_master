package br.com.bg7.appvistoria;

import com.wslibrary.bg7.ws.WSHelper;
import com.wslibrary.bg7.ws.WSRequests;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by elison on 29/06/17.
 */

public class Applic extends com.orm.SugarApp {

    /**   Name of the folter used to store images  */
    public final static String KEY_IMAGE_FOLDER = "ImagesAppVistoria";

    private static Applic instance = null;
    private JSONObject token = null;

    /**
     * Get a new instance of WSRequests class
     * @return
     */
    public WSRequests getWsRequests() {
        WSRequests	wsRequests = new WSRequests();

        if(token != null) {
            WSHelper.setToken(token);
        }

        return wsRequests;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;


    }


    /**
     * Set the current OAuth Access-token
     * @param token
     */
    public void setToken(JSONObject token) {
        this.token = token;
    }

    /**
     * get OAuth Access-token JSON Object
     * @return
     */
    public JSONObject getTokenJsonObject() {
        return token;
    }

    /**
     *  get the actual OAuth access_token
     * @return
     */
    public String getAccessToken() {
        if(token != null && token.has("access_token")) {
            try {
                return token.getString("access_token");
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        return null;
    }

    public static Applic getInstance() {
        return instance;
    }


}
