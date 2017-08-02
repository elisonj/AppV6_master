package br.com.bg7.appvistoria.data;

import br.com.bg7.appvistoria.data.source.remote.HttpProgressCallback;
import br.com.bg7.appvistoria.data.source.remote.HttpResponse;
import br.com.bg7.appvistoria.data.source.remote.SyncCallback;
import br.com.bg7.appvistoria.data.source.remote.dto.ProductResponse;
import br.com.bg7.appvistoria.sync.SyncStatus;

public class ProductInspectionCallback implements HttpProgressCallback<ProductResponse> {
        private final ProductInspection productInspection;
        private final SyncCallback syncCallback;

        public ProductInspectionCallback(ProductInspection productInspection, SyncCallback syncCallback) {
            this.productInspection = productInspection;
            this.syncCallback = syncCallback;
        }

        @Override
        public void onProgressUpdated(double percentage) {
            syncCallback.onProgressUpdated(productInspection, percentage);
        }

        @Override
        public void onResponse(HttpResponse<ProductResponse> httpResponse) {
            productInspection.setSyncStatus(SyncStatus.PRODUCT_INSPECTION_SYNCED);
            syncCallback.onSuccess(productInspection);
        }

        @Override
        public void onFailure(Throwable t) {
            productInspection.setSyncStatus(SyncStatus.FAILED);
            syncCallback.onFailure(productInspection, t);
        }
}