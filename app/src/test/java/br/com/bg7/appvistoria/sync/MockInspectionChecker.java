package br.com.bg7.appvistoria.sync;

import br.com.bg7.appvistoria.data.Inspection;
import br.com.bg7.appvistoria.data.source.remote.InspectionService;
import br.com.bg7.appvistoria.data.source.remote.PictureService;
import br.com.bg7.appvistoria.data.source.remote.callback.SyncCallback;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * Created by: luciolucio
 * Date: 2017-08-04
 */

class MockInspectionChecker {
    private Inspection inspection;

    private MockInspectionChecker(Inspection inspection) {
        this.inspection = inspection;
    }

    static MockInspectionChecker checkThat(Inspection inspection) {
        return new MockInspectionChecker(inspection);
    }

    void doesNotSyncPictures() {
        verify(inspection, never()).sync(any(PictureService.class), any(SyncCallback.class));
    }

    void doesNotSyncProducts() {
        verify(inspection, never()).sync(any(InspectionService.class), any(SyncCallback.class));
    }

    void syncsPictures() {
        verify(inspection).sync(any(PictureService.class), any(SyncCallback.class));
    }

    void syncsProducts() {
        verify(inspection).sync(any(InspectionService.class), any(SyncCallback.class));
    }
}
