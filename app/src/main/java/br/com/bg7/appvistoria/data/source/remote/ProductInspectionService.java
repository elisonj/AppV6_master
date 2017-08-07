package br.com.bg7.appvistoria.data.source.remote;

import br.com.bg7.appvistoria.data.ProductInspection;
import br.com.bg7.appvistoria.data.source.remote.http.HttpProgressCallback;
import br.com.bg7.appvistoria.data.source.remote.dto.ProductResponse;

/**
 * Created by: luciolucio
 * Date: 2017-08-01
 */

public interface ProductInspectionService {
    void send(ProductInspection productInspection, HttpProgressCallback<ProductResponse> callback);
}
