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
        checkStatusIsReady();
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
        startPictureSync();
    }

    @Test
    public void shouldChangeToBeingSyncedWhenProductStartsSyncing() {
        startProductSync();

        checkStatusIsInspectionBeingSynced();
    }

    @Test
    public void shouldRemainInBeingSyncedWhenProductReceivesProgressUpdate() {
        startProductSync();

        makeInspectionServiceUpdateProgressTo(10);

        checkStatusIsInspectionBeingSynced();
    }

    @Test
    public void shouldChangeToFailedWhenProductFailsSyncing() {
        startProductSync();

        makeInspectionServiceFail();

        checkStatusIsFailed();
    }

    @Test
    public void shouldChangeToFailedWhenNullResponse() {
        startProductSync();

        makeInspectionServiceRespondWith(null);

        checkStatusIsFailed();
    }

    @Test
    public void shouldChangeToFailedWhenBadHttpResponse() {
        startProductSync();

        makeInspectionServiceError();

        checkStatusIsFailed();
    }

    @Test
    public void shouldChangeToDoneWhenProductFinishesSyncing() {
        startProductSync();

        makeInspectionServiceSucceed();

        checkStatusIsDone();
    }

    @Test
    public void shouldNotChangeStatusWhenResetWithNoPictures() {
        inspection.reset();

        checkStatusIsReady();
    }

    @Test
    public void shouldChangeStatusToReadyWhenProductSyncStartedWithNoPictures() {
        startProductSync();

        inspection.reset();

        checkStatusIsReady();
    }

    @Test
    public void shouldNotResetWhenStatusIsDone() {
        startProductSync();
        makeInspectionServiceSucceed();

        inspection.reset();

        checkStatusIsDone();
    }

    private void startPictureSync() {
        inspection.sync(pictureService, new EmptySyncCallback());
    }

    private void startProductSync() {
        inspection.sync(inspectionService, new EmptySyncCallback());
    }

    private void makeInspectionServiceSucceed() {
        makeInspectionServiceRespondWith(SUCCESS);
    }

    private void makeInspectionServiceError() {
        makeInspectionServiceRespondWith(ERROR_400);
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

    private void checkStatusIsReady() {
        checkStatusIs(SyncStatus.READY);
    }

    private void checkStatusIsFailed() {
        checkStatusIs(SyncStatus.FAILED);
    }

    private void checkStatusIsInspectionBeingSynced() {
        checkStatusIs(SyncStatus.INSPECTION_BEING_SYNCED);
    }

    private void checkStatusIsDone() {
        checkStatusIs(SyncStatus.DONE);
    }

    private void checkStatusIs(SyncStatus status) {
        Assert.assertEquals(status, inspection.getSyncStatus());
    }
}
