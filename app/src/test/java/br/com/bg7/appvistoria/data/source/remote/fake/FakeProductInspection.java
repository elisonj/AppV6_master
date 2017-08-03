package br.com.bg7.appvistoria.data.source.remote.fake;

import br.com.bg7.appvistoria.data.ProductInspection;
import br.com.bg7.appvistoria.data.source.PictureService;
import br.com.bg7.appvistoria.data.source.ProductInspectionService;
import br.com.bg7.appvistoria.data.source.remote.HttpProgressCallback;
import br.com.bg7.appvistoria.data.source.remote.HttpResponse;
import br.com.bg7.appvistoria.data.source.remote.SyncCallback;
import br.com.bg7.appvistoria.data.source.remote.dto.ProductResponse;
import br.com.bg7.appvistoria.sync.SyncStatus;

/**
 * Created by: luciolucio
 * Date: 2017-08-02
 */

public class FakeProductInspection extends ProductInspection {
    public FakeProductInspection(SyncStatus syncStatus) {
        super(syncStatus);
    }

    @Override
    public boolean canSyncProduct() {
        return true;
    }

    @Override
    public void sync(ProductInspectionService productInspectionService, final SyncCallback callback) {
        productInspectionService.send(this, new HttpProgressCallback<ProductResponse>() {
            @Override
            public void onProgressUpdated(double percentage) {
                callback.onProgressUpdated(FakeProductInspection.this, percentage);
            }

            @Override
            public void onResponse(HttpResponse<ProductResponse> httpResponse) {
                callback.onSuccess(FakeProductInspection.this);
            }

            @Override
            public void onFailure(Throwable t) {
                FakeProductInspection.this.syncStatus = SyncStatus.FAILED;
                callback.onFailure(FakeProductInspection.this, t);
            }
        });
    }

    @Override
    public void sync(PictureService pictureService, SyncCallback callback) {
        super.sync(pictureService, callback);
    }
}
