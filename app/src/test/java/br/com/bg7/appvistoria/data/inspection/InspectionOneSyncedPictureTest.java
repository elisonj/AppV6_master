package br.com.bg7.appvistoria.data.inspection;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by: luciolucio
 * Date: 2017-08-18
 */

public class InspectionOneSyncedPictureTest extends InspectionTestBase {

    @Before
    public void setUp() {
        super.setUp();
        inspection.addImageToSync(PICTURE);

        inspection.readyToSync();

        startPictureSync();
        makePictureServiceSucceed();
    }

    @Test
    public void shouldNotBeAbleToSyncPicturesOnceServiceSucceededTheOnePicture() {
        checkStatusIsPicturesSynced();
        checkThatCannotSyncPictures();
        checkThatCanSyncProduct();
    }

    @Test(expected = IllegalStateException.class)
    public void shouldNotAllowSyncPicturesAfterAllPicturesSynced() {
        startPictureSync();
    }

    @Test
    public void resetShouldNotChangeStatus() {
        inspection.reset();

        checkStatusIsPicturesSynced();
    }

    @Test(expected = IllegalStateException.class)
    public void shouldNotAllowAddingPicturesAfterSyncStarts() {
        inspection.addImageToSync(PICTURE);
    }

    @Test
    public void shouldNotBeAbleToSyncPicturesWithNoPicturesToSync() {
        checkThatCannotSyncPictures();
    }

    @Test
    public void shouldBeAbleToSyncProductWithNoPicturesToSync() {
        checkThatCanSyncProduct();
    }

    @Test(expected = IllegalStateException.class)
    public void shouldNotAllowSyncPicturesWithNoPicturesToSync() {
        startPictureSync();
    }

    @Test
    public void shouldResetToPicturesSyncedWhenProductFails() {
        startProductSync();
        makeInspectionServiceFail();
        checkStatusIsFailed();

        inspection.reset();

        checkStatusIsPicturesSynced();
    }
}
