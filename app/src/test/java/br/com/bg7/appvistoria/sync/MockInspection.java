package br.com.bg7.appvistoria.sync;

import br.com.bg7.appvistoria.data.ProductInspection;
import br.com.bg7.appvistoria.data.source.PictureService;
import br.com.bg7.appvistoria.data.source.ProductInspectionService;
import br.com.bg7.appvistoria.data.source.remote.SyncCallback;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by: luciolucio
 * Date: 2017-08-04
 */

class MockInspection {
    private ProductInspection productInspection = mock(ProductInspection.class);

    private MockInspection() { }

    static MockInspection mockInspection() {
        return new MockInspection().withStatus(SyncStatus.READY);
    }

    MockInspection withStatus(SyncStatus syncStatus) {
        when(productInspection.getSyncStatus()).thenReturn(syncStatus);
        return this;
    }

    MockInspection thatCanSyncPictures() {
        when(productInspection.canSyncPictures()).thenReturn(true);
        return this;
    }

    MockInspection thatCannotSyncPictures() {
        when(productInspection.canSyncPictures()).thenReturn(false);
        return this;
    }

    MockInspection thatCanSyncProduct() {
        when(productInspection.canSyncProduct()).thenReturn(true);
        return this;
    }

    MockInspection thatCannotSyncProduct() {
        when(productInspection.canSyncProduct()).thenReturn(false);
        return this;
    }

    MockInspection thatThrowsOnProductSync() {
        MockInspection inspection = this
                .thatCannotSyncPictures()
                .thatCanSyncProduct();

        doThrow(new IllegalStateException()).when(inspection.create())
                .sync(any(ProductInspectionService.class), any(SyncCallback.class));

        return inspection;
    }

    MockInspection thatThrowsOnPictureSync() {
        MockInspection inspection = this
                .thatCanSyncPictures();

        doThrow(new IllegalStateException()).when(inspection.create())
                .sync(any(PictureService.class), any(SyncCallback.class));

        return inspection;
    }

    ProductInspection create() {
        return this.productInspection;
    }
}
