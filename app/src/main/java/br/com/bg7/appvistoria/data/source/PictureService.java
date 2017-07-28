package br.com.bg7.appvistoria.data.source;

import java.io.File;

import br.com.bg7.appvistoria.data.ProductInspection;
import br.com.bg7.appvistoria.data.source.remote.HttpProgressCallback;
import br.com.bg7.appvistoria.data.source.remote.dto.PictureResponse;
import okhttp3.MultipartBody;
import retrofit2.http.Part;

/**
 * Created by: elison
 * Date: 2017-07-28
 */
public interface PictureService {
    void send(File attachment,
              @Part MultipartBody.Part file,
              ProductInspection productInspection,
              HttpProgressCallback<PictureResponse> callback);
}
