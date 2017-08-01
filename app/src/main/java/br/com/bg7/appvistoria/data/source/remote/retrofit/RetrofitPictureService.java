package br.com.bg7.appvistoria.data.source.remote.retrofit;

import java.io.File;

import br.com.bg7.appvistoria.data.ProductInspection;
import br.com.bg7.appvistoria.data.source.remote.HttpProgressCallback;
import br.com.bg7.appvistoria.data.source.remote.dto.PictureResponse;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by: elison
 * Date: 2017-07-28
 */
public class RetrofitPictureService implements br.com.bg7.appvistoria.data.source.PictureService {

    private static final String FORM_FIELD_NAME = "image";
    private final PictureService pictureService;
    private int bufferSize;

    private ProductInspection productInspection;

    public RetrofitPictureService(String baseUrl, ProductInspection productInspection, int bufferSize) {
        this.productInspection = productInspection;
        this.bufferSize = bufferSize;
        this.pictureService = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(PictureService.class);
    }

    @Override
    public void send(File attachment, ProductInspection productInspection, HttpProgressCallback<PictureResponse> httpProgressCallback) {

        ProgressRequestBody fileBody = new ProgressRequestBody(attachment, bufferSize, httpProgressCallback);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData(FORM_FIELD_NAME, attachment.getName(), fileBody);

        Call<PictureResponse> call = pictureService.send(attachment, filePart, productInspection, httpProgressCallback);
        RetrofitHttpCall<PictureResponse> httpCall = new RetrofitHttpCall<>(call);

        httpCall.enqueue(httpProgressCallback);

    }
}
