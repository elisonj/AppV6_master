package br.com.bg7.appvistoria.data.source.remote.retrofit;

import java.io.File;

import br.com.bg7.appvistoria.data.ProductInspection;
import br.com.bg7.appvistoria.data.source.remote.HttpProgress;
import br.com.bg7.appvistoria.data.source.remote.HttpProgressCallback;
import br.com.bg7.appvistoria.data.source.remote.dto.PictureResponse;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Part;

/**
 * Created by: elison
 * Date: 2017-07-28
 */
public class RetrofitHttpProgressService implements PictureService {

    private final PictureService pictureService;

    ProductInspection productInspection;

    public RetrofitHttpProgressService(String baseUrl, ProductInspection productInspection) {
        this.productInspection = productInspection;
        this.pictureService = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(PictureService.class);
    }

    @Override
    public Call<PictureResponse> send(File attachment, @Part MultipartBody.Part file, ProductInspection productInspection, HttpProgressCallback httpProgressCallback) {

        HttpProgress fileBody = new HttpProgress(attachment, httpProgressCallback);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("image", attachment.getName(), fileBody);

        Call<PictureResponse> call = pictureService.send(attachment, filePart, productInspection, httpProgressCallback);
        RetrofitHttpCall<PictureResponse> httpCall = new RetrofitHttpCall<>(call);


        httpCall.enqueue(httpProgressCallback);

        return call;
    }
}
