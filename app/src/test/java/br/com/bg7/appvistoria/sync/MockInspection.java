package br.com.bg7.appvistoria.sync;

import br.com.bg7.appvistoria.data.Inspection;
import br.com.bg7.appvistoria.data.source.remote.InspectionService;
import br.com.bg7.appvistoria.data.source.remote.PictureService;
import br.com.bg7.appvistoria.data.source.remote.callback.SyncCallback;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by: luciolucio
 * Date: 2017-08-04
 */

class MockInspection {
    private Inspection inspection = mock(Inspection.class);

    private MockInspection() { }

    static MockInspection mockInspection() {
        return new MockInspection()
                .withStatus(SyncStatus.READY)
                .thatCanSyncPictures()
                .thatCannotSyncProduct();
    }

    MockInspection withStatus(SyncStatus syncStatus) {
        when(inspection.getSyncStatus()).thenReturn(syncStatus);
        return this;
    }

    MockInspection thatCanSyncPictures() {
        when(inspection.canSyncPictures()).thenReturn(true);
        return this;
    }

    MockInspection thatCannotSyncPictures() {
        when(inspection.canSyncPictures()).thenReturn(false);
        return this;
    }

    MockInspection thatCanSyncProduct() {
        when(inspection.canSyncProduct()).thenReturn(true);
        return this;
    }

    MockInspection thatCannotSyncProduct() {
        when(inspection.canSyncProduct()).thenReturn(false);
        return this;
    }

    MockInspection thatThrowsOnProductSync() {
        MockInspection inspection = this
                .thatCannotSyncPictures()
                .thatCanSyncProduct();

        doThrow(new IllegalStateException()).when(inspection.create())
                .sync(any(InspectionService.class), any(SyncCallback.class));

        return inspection;
    }

    MockInspection thatThrowsOnPictureSync() {
        MockInspection inspection = this
                .thatCanSyncPictures();

        doThrow(new IllegalStateException()).when(inspection.create())
                .sync(any(PictureService.class), any(SyncCallback.class));

        return inspection;
    }

    Inspection create() {
        return this.inspection;
    }
}
