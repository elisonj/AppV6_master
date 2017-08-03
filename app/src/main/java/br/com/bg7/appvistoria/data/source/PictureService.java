package br.com.bg7.appvistoria.data.source;

import java.io.File;

import br.com.bg7.appvistoria.data.ProductInspection;
import br.com.bg7.appvistoria.data.source.remote.HttpProgressCallback;
import br.com.bg7.appvistoria.data.source.remote.dto.PictureResponse;

/**
 * Created by: elison
 * Date: 2017-07-28
 *
 * TODO: Mover todos os Service para dentro de remote
 */
public interface PictureService {
    void send(File attachment,
              ProductInspection productInspection,
              HttpProgressCallback<PictureResponse> callback);
}
