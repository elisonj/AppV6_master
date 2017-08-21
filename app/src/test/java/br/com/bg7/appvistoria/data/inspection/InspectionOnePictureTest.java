package br.com.bg7.appvistoria.data.inspection;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by: elison
 * Date: 2017-08-02
 */
public class InspectionOnePictureTest extends InspectionTestBase {

    @Before
    public void setUp() {
        super.setUp();
        inspection.addImageToSync(PICTURE);
        inspection.readyToSync();
    }

    @Test(expected = IllegalStateException.class)
    public void shouldNotAllowSyncProductWithPendingPictures() {
        startProductSync();
    }

    @Test
    public void shouldHaveStatusReadyWithOnePicture() {
        checkStatusIsReady();
    }

    @Test
    public void shouldBeAbleToSyncPicturesWithOnePicture() {
        checkThatCanSyncPictures();
    }

    @Test
    public void shouldNotBeAbleToSyncProductWithOnePictureNotSynced() {
        checkThatCannotSyncProduct();
    }

    @Test
    public void shouldHaveStatusBeingSyncedAndNotBeAbleToSyncAnythingWhenTheOnePictureIsSyncing() {
        startPictureSync();

        checkStatusIsPicturesBeingSynced();
        checkThatCannotSyncAnything();
    }

    @Test
    public void shouldHaveStatusBeingSyncedAndNotBeAbleToSyncAnythingWhenTheOnePictureUpdatesProgress() {
        startPictureSync();

        makePictureServiceUpdateProgressTo(10);

        checkStatusIsPicturesBeingSynced();
        checkThatCannotSyncAnything();
    }

    @Test
    public void shouldFailAndBeAbleToSyncPicturesIfServiceErrorsOnOnlyPicture() {
        startPictureSync();

        makePictureServiceError();

        checkStatusIsFailed();
        checkThatCanSyncPictures();
        checkThatCannotSyncProduct();
    }

    @Test
    public void shouldFailAndBeAbleToSyncPicturesIfServiceRespondsNullOnOnlyPicture() {
        startPictureSync();

        makePictureServiceRespondNull();

        checkStatusIsFailed();
        checkThatCanSyncPictures();
        checkThatCannotSyncProduct();
    }

    @Test
    public void shouldFailAndBeAbleToSyncPicturesIfServiceFailsOnOnlyPicture() {
        startPictureSync();
        makePictureServiceFail();

        checkStatusIsFailed();
        checkThatCanSyncPictures();
        checkThatCannotSyncProduct();
    }

    @Test
    public void shouldHaveStatusReadyWhenResetFromReady() {
        inspection.reset();
        checkStatusIsReady();
    }

    @Test
    public void shouldHaveStatusBeingSyncedWhenResetMidSync() {
        startPictureSync();

        makePictureServiceUpdateProgressTo(10);
        inspection.reset();

        checkStatusIsPicturesBeingSynced();
    }

    @Test
    public void shouldHaveStatusBeingSyncedWhenResetAfterError() {
        startPictureSync();

        makePictureServiceError();
        inspection.reset();

        checkStatusIsReady();
    }

    @Test
    public void shouldHaveStatusBeingSyncedWhenResetAfterFailure() {
        startPictureSync();

        makePictureServiceFail();
        inspection.reset();

        checkStatusIsReady();
    }

    @Test
    public void shouldHaveStatusBeingSyncedWhenResetAfterNullResponse() {
        startPictureSync();

        makePictureServiceRespondNull();
        inspection.reset();

        checkStatusIsReady();
    }
}
