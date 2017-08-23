package br.com.bg7.appvistoria.data.inspection;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by: elison
 * Date: 2017-08-02
 */
public class InspectionNoPictureTest extends InspectionTestBase {

    @Before
    public void setUp() {
        super.setUp();

        inspection.readyToSync();
    }

    @Test
    public void shouldInitializeWithStatusReady() {
        checkStatusIsReady();
    }

    @Test
    public void shouldNotBeAbleToSyncPicturesWithNoPictures() {
        checkThatCannotSyncPictures();
    }

    @Test
    public void shouldBeAbleToSyncProductWithNoPictures() {
        checkThatCanSyncProduct();
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

        makeInspectionServiceRespondNull();

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
    public void shouldChangeStatusToReadyWhenResetAfterProductSyncStartedWithNoPictures() {
        startProductSync(); // Vai para INSPECTION_BEING_SYNCED

        inspection.reset();

        checkStatusIsReady(); // Verifica que volta para READY
    }

    @Test
    public void shouldNotResetWhenStatusIsDone() {
        startProductSync();
        makeInspectionServiceSucceed();

        inspection.reset();

        checkStatusIsDone();
    }
}
