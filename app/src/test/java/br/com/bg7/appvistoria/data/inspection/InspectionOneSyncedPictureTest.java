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
        startPictureSync();
        makePictureServiceSucceed();
    }

    @Test
    public void shouldNotBeAbleToSyncPicturesOnceServiceSucceededTheOnePicture() {
        checkStatusIsPicturesSynced();
        checkThatCannotSyncPictures();
        checkThatCanSyncProduct();
    }
}
