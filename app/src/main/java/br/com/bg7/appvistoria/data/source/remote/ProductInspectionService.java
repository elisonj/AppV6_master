package br.com.bg7.appvistoria.data.source.remote;

import br.com.bg7.appvistoria.data.Inspection;
import br.com.bg7.appvistoria.data.source.remote.dto.ProductResponse;
import br.com.bg7.appvistoria.data.source.remote.http.HttpProgressCallback;

/**
 * Created by: luciolucio
 * Date: 2017-08-01
 */

public interface ProductInspectionService {
    void send(Inspection inspection, HttpProgressCallback<ProductResponse> callback);
}
