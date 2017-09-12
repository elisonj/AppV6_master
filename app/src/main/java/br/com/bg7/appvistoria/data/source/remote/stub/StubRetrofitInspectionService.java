package br.com.bg7.appvistoria.data.source.remote.stub;

import javax.annotation.Nullable;

import br.com.bg7.appvistoria.data.Inspection;
import br.com.bg7.appvistoria.data.source.remote.InspectionService;
import br.com.bg7.appvistoria.data.source.remote.dto.ProductResponse;
import br.com.bg7.appvistoria.data.source.remote.http.HttpProgressCallback;
import br.com.bg7.appvistoria.data.source.remote.http.HttpResponse;

/**
 * Created by: elison
 * Date: 2017-09-12
 */
public class StubRetrofitInspectionService implements InspectionService {
    @Override
    public void send(Inspection inspection, HttpProgressCallback<ProductResponse> callback) {
        callback.onResponse(new HttpResponse<ProductResponse>() {
            @Override
            public boolean isSuccessful() {
                return true;
            }

            @Nullable
            @Override
            public ProductResponse body() {
                return new ProductResponse();
            }

            @Override
            public int code() {
                return 200;
            }
        });
    }
}
