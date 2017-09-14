package br.com.bg7.appvistoria.data.source.remote;

import java.io.File;

import br.com.bg7.appvistoria.data.Inspection;
import br.com.bg7.appvistoria.data.source.remote.dto.PictureResponse;
import br.com.bg7.appvistoria.data.source.remote.http.HttpProgressCallback;

/**
 * Created by: elison
 * Date: 2017-07-28
 */
public interface PictureService {
    void send(File attachment,
              HttpProgressCallback<PictureResponse> callback);
}
