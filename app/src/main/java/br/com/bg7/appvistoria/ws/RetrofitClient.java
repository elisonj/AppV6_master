package br.com.bg7.appvistoria.ws;

import br.com.bg7.appvistoria.Applic;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by: elison
 * Date: 2017-07-11
 */

public class RetrofitClient {

    private static Retrofit retrofit = null;


    public static Retrofit getClient(String baseUrl) {
        if (retrofit==null) {

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new ConnectivityInterceptor(Applic.getInstance().getApplicationContext()))
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
