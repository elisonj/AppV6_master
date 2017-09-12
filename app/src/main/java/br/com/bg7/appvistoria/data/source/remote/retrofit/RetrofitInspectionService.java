package br.com.bg7.appvistoria.data.source.remote.retrofit;

import br.com.bg7.appvistoria.data.Inspection;
import br.com.bg7.appvistoria.data.source.remote.InspectionService;
import br.com.bg7.appvistoria.data.source.remote.dto.ProductResponse;
import br.com.bg7.appvistoria.data.source.remote.http.HttpProgressCallback;

/**
 * Created by: elison
 * Date: 2017-09-12
 */
public class RetrofitInspectionService implements InspectionService {
    @Override
    public void send(Inspection inspection, HttpProgressCallback<ProductResponse> callback) {

    }
}
