package br.com.bg7.appvistoria.data.source.remote.retrofit;

import java.io.File;

import br.com.bg7.appvistoria.data.ProductInspection;
import br.com.bg7.appvistoria.data.source.remote.HttpProgressCallback;
import br.com.bg7.appvistoria.data.source.remote.dto.PictureResponse;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by: elison
 * Date: 2017-07-28
 */
interface PictureService {
    @Multipart
    @POST("/upload")
    Call<PictureResponse> send(File attachment, @Part MultipartBody.Part file,
                               ProductInspection productInspection,
                               HttpProgressCallback httpProgressCallback);
}
