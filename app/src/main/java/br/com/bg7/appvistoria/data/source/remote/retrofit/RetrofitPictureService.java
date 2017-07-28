package br.com.bg7.appvistoria.data.source.remote.retrofit;

import java.io.File;

import br.com.bg7.appvistoria.data.ProductInspection;
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
public class RetrofitPictureService implements PictureService {

    private static final int DEFAULT_BUFFER_SIZE = 2048;
    private final PictureService pictureService;

    ProductInspection productInspection;

    public RetrofitPictureService(String baseUrl, ProductInspection productInspection) {
        this.productInspection = productInspection;
        this.pictureService = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(PictureService.class);
    }

    @Override
    public Call<PictureResponse> send(File attachment, @Part MultipartBody.Part file, ProductInspection productInspection, HttpProgressCallback httpProgressCallback) {

        ProgressRequestBody fileBody = new ProgressRequestBody(attachment, httpProgressCallback, DEFAULT_BUFFER_SIZE);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("image", attachment.getName(), fileBody);

        Call<PictureResponse> call = pictureService.send(attachment, filePart, productInspection, httpProgressCallback);
        RetrofitHttpCall<PictureResponse> httpCall = new RetrofitHttpCall<>(call);


        httpCall.enqueue(httpProgressCallback);

        return call;
    }
}
