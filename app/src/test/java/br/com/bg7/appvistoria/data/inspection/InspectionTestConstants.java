package br.com.bg7.appvistoria.data.inspection;

import javax.annotation.Nullable;

import br.com.bg7.appvistoria.data.source.remote.dto.ProductResponse;
import br.com.bg7.appvistoria.data.source.remote.http.HttpResponse;

/**
 * Created by: luciolucio
 * Date: 2017-08-17
 */

class InspectionTestConstants {
    static final HttpResponse<ProductResponse> SUCCESS = new HttpResponse<ProductResponse>() {
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
    };

    static final HttpResponse<ProductResponse> ERROR_400 = new HttpResponse<ProductResponse>() {
        @Override
        public boolean isSuccessful() {
            return false;
        }

        @Nullable
        @Override
        public ProductResponse body() {
            return new ProductResponse();
        }

        @Override
        public int code() {
            return 400;
        }
    };
}
