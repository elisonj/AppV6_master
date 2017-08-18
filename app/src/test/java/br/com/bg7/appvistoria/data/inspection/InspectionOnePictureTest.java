package br.com.bg7.appvistoria.data.inspection;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import java.io.File;

import br.com.bg7.appvistoria.data.source.remote.dto.PictureResponse;
import br.com.bg7.appvistoria.data.source.remote.http.HttpResponse;

import static br.com.bg7.appvistoria.data.inspection.InspectionTestConstants.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

/**
 * Created by: elison
 * Date: 2017-08-02
 */
public class InspectionOnePictureTest extends InspectionTestBase {

    private static final File PICTURE = new File("");

    @Before
    public void setUp() {
        super.setUp();
        inspection.addImageToSync(PICTURE);
    }

    @Test
    public void shouldBeAbleToSyncPicturesWithOnePicture() {
        Assert.assertTrue(inspection.canSyncPictures());
    }

    @Test
    public void shouldNotBeAbleToSyncProductWithOnePictureNotSynced() {
        Assert.assertFalse(inspection.canSyncProduct());
    }

    @Test
    public void shouldNotBeAbleToSyncAnythingWhenOneAndOnlyPictureIsSyncing() {
        startPictureSync();

        Assert.assertFalse(inspection.canSyncPictures());
        Assert.assertFalse(inspection.canSyncProduct());
    }

    @Test
    public void shouldNotBeAbleToSyncPicturesOnceAllDone() {
        startPictureSync();
        makePictureServiceSucceed();

        Assert.assertFalse(inspection.canSyncPictures());
        Assert.assertTrue(inspection.canSyncProduct());
    }

    @Test
    public void shouldBeAbleToSyncPicturesIfOneInError() {
        startPictureSync();
        makePictureServiceError();

        Assert.assertTrue(inspection.canSyncPictures());
        Assert.assertFalse(inspection.canSyncProduct());
    }

    private void makePictureServiceSucceed() {
        makePictureServiceRespondWith(PICTURE_SUCCESS);
    }

    private void makePictureServiceError() {
        makePictureServiceRespondWith(PICTURE_ERROR_400);
    }

    private void makePictureServiceRespondWith(HttpResponse<PictureResponse> response) {
        verify(pictureService).send(eq(PICTURE), eq(inspection), pictureServiceCallback.capture());
        pictureServiceCallback.getValue().onResponse(response);
    }
}
