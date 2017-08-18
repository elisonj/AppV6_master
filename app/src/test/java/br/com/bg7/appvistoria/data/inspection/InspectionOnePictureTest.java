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
    public void shouldNotBeAbleToSyncPicturesOnceAllDone() {
        startPictureSync();

        makePictureServiceSucceed();

        checkStatusIsPicturesSynced();
        checkThatCannotSyncPictures();
        checkThatCanSyncProduct();
    }
}
