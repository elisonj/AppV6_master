package br.com.bg7.appvistoria.sync;

import br.com.bg7.appvistoria.data.ProductInspection;
import br.com.bg7.appvistoria.data.source.PictureService;
import br.com.bg7.appvistoria.data.source.ProductInspectionService;
import br.com.bg7.appvistoria.data.source.remote.SyncCallback;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * Created by: luciolucio
 * Date: 2017-08-04
 */

class MockInspectionChecker {
    private ProductInspection inspection;

    private MockInspectionChecker(ProductInspection inspection) {
        this.inspection = inspection;
    }

    static MockInspectionChecker checkThat(ProductInspection inspection) {
        return new MockInspectionChecker(inspection);
    }

    void doesNotSyncPictures() {
        verify(inspection, never()).sync(any(PictureService.class), any(SyncCallback.class));
    }

    void doesNotSyncProducts() {
        verify(inspection, never()).sync(any(ProductInspectionService.class), any(SyncCallback.class));
    }

    void syncsPictures() {
        verify(inspection).sync(any(PictureService.class), any(SyncCallback.class));
    }

    void syncsProducts() {
        verify(inspection).sync(any(PictureService.class), any(SyncCallback.class));
    }
}
