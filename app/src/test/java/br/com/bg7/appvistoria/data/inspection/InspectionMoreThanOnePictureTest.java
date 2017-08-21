package br.com.bg7.appvistoria.data.inspection;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;

import br.com.bg7.appvistoria.data.source.remote.dto.PictureResponse;
import br.com.bg7.appvistoria.data.source.remote.http.HttpProgressCallback;

import static org.mockito.Mockito.reset;

/**
 * Created by: luciolucio
 * Date: 2017-08-21
 */

public class InspectionMoreThanOnePictureTest extends InspectionTestBase {

    @Captor
    ArgumentCaptor<HttpProgressCallback<PictureResponse>> secondPictureServiceCallback;

    @Before
    public void setUp() {
        super.setUp();
        inspection.addImageToSync(PICTURE);
        inspection.addImageToSync(PICTURE);

        startPictureSync();
        makePictureServiceSucceed();
        reset(pictureService);
    }

    @Test
    public void shouldHaveStatusBeingSynced() {
        checkStatusIsPicturesBeingSynced();
    }

    @Test
    public void shouldBeAbleToSyncPicturesButNotProducts() {
        checkThatCanSyncPictures();
        checkThatCannotSyncProduct();
    }

    @Test
    public void shouldHaveStatusBeingSyncedAndNotAllowSyncAnythingAfterLastSyncStarts() {
        startPictureSync();

        checkStatusIsPicturesBeingSynced();
        checkThatCannotSyncAnything();
    }

    @Test
    public void shouldHaveStatusPicturesSyncedAndAllowProductSyncAfterLastSyncSucceeds() {
        startPictureSync();
        makePictureServiceSucceed(secondPictureServiceCallback);

        checkStatusIsPicturesSynced();
        checkThatCannotSyncPictures();
        checkThatCanSyncProduct();
    }

    @Test
    public void shouldHaveStatusFailedAndAllowPictureSyncAfterLastSyncFails() {
        startPictureSync();
        makePictureServiceError(secondPictureServiceCallback);

        checkStatusIsFailed();
        checkThatCanSyncPictures();
        checkThatCannotSyncProduct();
    }
}
