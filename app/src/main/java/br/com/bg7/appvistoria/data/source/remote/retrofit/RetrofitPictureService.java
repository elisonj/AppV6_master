package br.com.bg7.appvistoria.data.source.remote.retrofit;

import java.io.File;

import br.com.bg7.appvistoria.data.Inspection;
import br.com.bg7.appvistoria.data.source.remote.dto.PictureResponse;
import br.com.bg7.appvistoria.data.source.remote.http.HttpProgressCallback;
import br.com.bg7.appvistoria.data.source.remote.retrofit.http.RetrofitHttpCall;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by: elison
 * Date: 2017-07-28
 */
public class RetrofitPictureService implements br.com.bg7.appvistoria.data.source.remote.PictureService {

    private static final String FORM_FIELD_NAME = "image";
    private final PictureService pictureService;
    private int bufferSize;

    private Inspection inspection;

    public RetrofitPictureService(String baseUrl, Inspection inspection, int bufferSize) {
        this.inspection = inspection;
        this.bufferSize = bufferSize;
        this.pictureService = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(PictureService.class);
    }

    @Override
    public void send(File attachment, Inspection inspection, HttpProgressCallback<PictureResponse> httpProgressCallback) {

        ProgressRequestBody fileBody = new ProgressRequestBody(attachment, bufferSize, httpProgressCallback);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData(FORM_FIELD_NAME, attachment.getName(), fileBody);

        Call<PictureResponse> call = pictureService.send(attachment, filePart, inspection, httpProgressCallback);
        RetrofitHttpCall<PictureResponse> httpCall = new RetrofitHttpCall<>(call);

        httpCall.enqueue(httpProgressCallback);

    }
}
