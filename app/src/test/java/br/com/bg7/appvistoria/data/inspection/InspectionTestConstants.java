package br.com.bg7.appvistoria.data.inspection;

import javax.annotation.Nullable;

import br.com.bg7.appvistoria.data.source.remote.dto.PictureResponse;
import br.com.bg7.appvistoria.data.source.remote.dto.ProductResponse;
import br.com.bg7.appvistoria.data.source.remote.http.HttpResponse;

/**
 * Created by: elison
 * Date: 2017-08-17
 */

class InspectionTestConstants {
    static final HttpResponse<ProductResponse> PRODUCT_SUCCESS = new HttpResponse<ProductResponse>() {
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

    static final HttpResponse<ProductResponse> PRODUCT_ERROR_400 = new HttpResponse<ProductResponse>() {
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

    static final HttpResponse<PictureResponse> PICTURE_SUCCESS = new HttpResponse<PictureResponse>() {
        @Override
        public boolean isSuccessful() {
            return true;
        }

        @Nullable
        @Override
        public PictureResponse body() {
            return new PictureResponse();
        }

        @Override
        public int code() {
            return 200;
        }
    };

    static final HttpResponse<PictureResponse> PICTURE_ERROR_400 = new HttpResponse<PictureResponse>() {
        @Override
        public boolean isSuccessful() {
            return false;
        }

        @Nullable
        @Override
        public PictureResponse body() {
            return new PictureResponse();
        }

        @Override
        public int code() {
            return 400;
        }
    };
}
