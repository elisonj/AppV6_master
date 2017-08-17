package br.com.bg7.appvistoria.data.inspection;

import junit.framework.Assert;

import org.junit.Test;

import java.io.IOException;

import br.com.bg7.appvistoria.data.source.remote.dto.ProductResponse;
import br.com.bg7.appvistoria.data.source.remote.http.HttpResponse;
import br.com.bg7.appvistoria.sync.SyncStatus;

import static br.com.bg7.appvistoria.data.inspection.InspectionTestConstants.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

/**
 * Created by: elison
 * Date: 2017-08-02
 */
public class InspectionNoPictureTest extends InspectionTestBase {

    @Test
    public void shouldInitializeWithStatusReady() {
        checkStatusIs(SyncStatus.READY);
    }

    @Test
    public void shouldNotBeAbleToSyncPicturesWithNoPictures() {
        Assert.assertFalse(inspection.canSyncPictures());
    }

    @Test
    public void shouldBeAbleToSyncProductWithNoPictures() {
        Assert.assertTrue(inspection.canSyncProduct());
    }

    @Test(expected = IllegalStateException.class)
    public void shouldNotAllowSyncPicturesWithNoPictures() {
        runPictureSync();
    }

    @Test
    public void shouldChangeToBeingSyncedWhenProductStartsSyncing() {
        runProductSync();

        checkStatusIs(SyncStatus.INSPECTION_BEING_SYNCED);
    }

    @Test
    public void shouldRemainInBeingSyncedWhenProductReceivesProgressUpdate() {
        runProductSync();

        makeInspectionServiceUpdateProgressTo(10);

        checkStatusIs(SyncStatus.INSPECTION_BEING_SYNCED);
    }

    @Test
    public void shouldChangeToFailedWhenProductFailsSyncing() {
        runProductSync();

        makeInspectionServiceFail();

        checkStatusIs(SyncStatus.FAILED);
    }

    @Test
    public void shouldChangeToFailedWhenNullResponse() {
        runProductSync();

        makeInspectionServiceRespondWith(null);

        checkStatusIs(SyncStatus.FAILED);
    }

    @Test
    public void shouldChangeToFailedWhenBadHttpResponse() {
        runProductSync();

        makeInspectionServiceRespondWith(ERROR_400);

        checkStatusIs(SyncStatus.FAILED);
    }

    @Test
    public void shouldChangeToDoneWhenProductFinishesSyncing() {
        runProductSync();

        makeInspectionServiceRespondWith(SUCCESS);

        checkStatusIs(SyncStatus.DONE);
    }

    @Test
    public void shouldNotChangeStatusFromReadyWhenResetWithNoPictures() {
        inspection.reset();

        checkStatusIs(SyncStatus.READY);
    }

    @Test
    public void shouldChangeToReadyWhenResetWithNoPictures() {
        runProductSync();

        inspection.reset();

        checkStatusIs(SyncStatus.READY);
    }

    @Test
    public void shouldNotResetWhenStatusIsDone() {
        runProductSync();
        makeInspectionServiceRespondWith(SUCCESS);

        inspection.reset();

        checkStatusIs(SyncStatus.DONE);
    }

    private void runPictureSync() {
        inspection.sync(pictureService, new EmptySyncCallback());
    }

    private void runProductSync() {
        inspection.sync(inspectionService, new EmptySyncCallback());
    }

    private void makeInspectionServiceRespondWith(HttpResponse<ProductResponse> response) {
        verify(inspectionService).send(eq(inspection), productServiceCallback.capture());
        productServiceCallback.getValue().onResponse(response);
    }

    private void makeInspectionServiceUpdateProgressTo(int progress) {
        verify(inspectionService).send(eq(inspection), productServiceCallback.capture());
        productServiceCallback.getValue().onProgressUpdated(progress);
    }

    private void makeInspectionServiceFail() {
        verify(inspectionService).send(eq(inspection), productServiceCallback.capture());
        productServiceCallback.getValue().onFailure(new IOException());
    }

    private void checkStatusIs(SyncStatus status) {
        Assert.assertEquals(status, inspection.getSyncStatus());
    }
}
