package br.com.bg7.appvistoria.data.inspection;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;

import br.com.bg7.appvistoria.data.source.remote.dto.PictureResponse;
import br.com.bg7.appvistoria.data.source.remote.http.HttpProgressCallback;

import static org.mockito.Mockito.reset;

/**
 * Created by: elison
 * Date: 2017-08-21
 */

public class InspectionUncommonStatusTransitionsTest extends InspectionTestBase {

    @Captor
    ArgumentCaptor<HttpProgressCallback<PictureResponse>> secondPictureServiceCallback;

    @Captor
    ArgumentCaptor<HttpProgressCallback<PictureResponse>> thirdPictureServiceCallback;

    @Test
    public void shouldNotBeAbleToSyncAnythingBeforeReady() {
        checkThatCannotSyncAnything();
    }

    @Test
    public void shouldHaveStatusFailedEvenWhenNextOneSucceeds() {
        inspection.addImageToSync(PICTURE);
        inspection.addImageToSync(PICTURE);
        inspection.addImageToSync(PICTURE);

        inspection.readyToSync();

        startPictureSync();
        makePictureServiceSucceed();
        reset(pictureService);

        startPictureSync();
        makePictureServiceError(secondPictureServiceCallback);
        reset(pictureService);

        startPictureSync();
        makePictureServiceSucceed(thirdPictureServiceCallback);
        reset(pictureService);

        checkStatusIsFailed();
    }
}
