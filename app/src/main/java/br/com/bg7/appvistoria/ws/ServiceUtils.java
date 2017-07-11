package br.com.bg7.appvistoria.ws;

/**
 * Created by elison on 11/07/17.
 */

public class ServiceUtils {

    public static final String BASE_URL = "https://preapi.s4bdigital.net";

    /**
     * get Service to request an Object Response return
     * @return
     */
    public static ServiceInterface getService() {
        return RetrofitClient.getClient(BASE_URL).create(ServiceInterface.class);
    }

    /**
     * get Service to request a Json return
     * @return
     */
    public static ServiceInterface getJSONService() {
        return RetrofitJSONClient.getClient(BASE_URL).create(ServiceInterface.class);
    }

}
