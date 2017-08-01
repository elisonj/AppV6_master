package br.com.bg7.appvistoria.data.source;

import br.com.bg7.appvistoria.data.ProductInspection;
import br.com.bg7.appvistoria.data.source.remote.HttpProgressCallback;
import br.com.bg7.appvistoria.data.source.remote.dto.PictureResponse;

/**
 * Created by: elison
 * Date: 2017-08-01
 */
public interface ProductInspectionService {
    void send(ProductInspection productInspection,
              HttpProgressCallback<PictureResponse> callback);
}
