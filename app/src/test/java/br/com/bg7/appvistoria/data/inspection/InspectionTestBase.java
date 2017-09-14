package br.com.bg7.appvistoria.data.inspection;

import junit.framework.Assert;

import org.junit.Before;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.io.IOException;

import br.com.bg7.appvistoria.data.Inspection;
import br.com.bg7.appvistoria.data.source.remote.InspectionService;
import br.com.bg7.appvistoria.data.source.remote.PictureService;
import br.com.bg7.appvistoria.data.source.remote.callback.SyncCallback;
import br.com.bg7.appvistoria.data.source.remote.dto.PictureResponse;
import br.com.bg7.appvistoria.data.source.remote.dto.ProductResponse;
import br.com.bg7.appvistoria.data.source.remote.http.HttpProgressCallback;
import br.com.bg7.appvistoria.data.source.remote.http.HttpResponse;
import br.com.bg7.appvistoria.sync.SyncStatus;

import static br.com.bg7.appvistoria.data.inspection.InspectionTestConstants.PICTURE_ERROR_400;
import static br.com.bg7.appvistoria.data.inspection.InspectionTestConstants.PICTURE_SUCCESS;
import static br.com.bg7.appvistoria.data.inspection.InspectionTestConstants.PRODUCT_ERROR_400;
import static br.com.bg7.appvistoria.data.inspection.InspectionTestConstants.PRODUCT_SUCCESS;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

/**
 * Created by: luciolucio
 * Date: 2017-08-17
 */

class InspectionTestBase {

    Inspection inspection;

    @Mock
    PictureService pictureService;

    @Mock
    InspectionService inspectionService;

    @Captor
    ArgumentCaptor<HttpProgressCallback<ProductResponse>> productServiceCallback;

    @Captor
    ArgumentCaptor<HttpProgressCallback<PictureResponse>> pictureServiceCallback;

    static final File PICTURE = new File("");

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        inspection = new Inspection();
    }

    void startPictureSync() {
        inspection.sync(pictureService, new EmptySyncCallback());
    }

    void startProductSync() {
        inspection.sync(inspectionService, new EmptySyncCallback());
    }

    void checkThatCanSyncProduct() {
        Assert.assertTrue(inspection.canSyncProduct());
    }

    void checkThatCanSyncPictures() {
        Assert.assertTrue(inspection.canSyncPictures());
    }

    void checkThatCannotSyncProduct() {
        Assert.assertFalse(inspection.canSyncProduct());
    }

    void checkThatCannotSyncPictures() {
        Assert.assertFalse(inspection.canSyncPictures());
    }

    void checkThatCannotSyncAnything() {
        checkThatCannotSyncPictures();
        checkThatCannotSyncProduct();
    }

    void makePictureServiceSucceed() {
        makePictureServiceSucceed(pictureServiceCallback);
    }

    void makePictureServiceError() {
        makePictureServiceError(pictureServiceCallback);
    }

    void makePictureServiceRespondNull() {
        makePictureServiceRespondNull(pictureServiceCallback);
    }

    void makePictureServiceUpdateProgressTo(int progress) {
        makePictureServiceUpdateProgressTo(progress, pictureServiceCallback);
    }

    void makePictureServiceFail() {
        makePictureServiceFail(pictureServiceCallback);
    }

    void makePictureServiceSucceed(ArgumentCaptor<HttpProgressCallback<PictureResponse>> captor) {
        makePictureServiceRespondWith(PICTURE_SUCCESS, captor);
    }

    void makePictureServiceError(ArgumentCaptor<HttpProgressCallback<PictureResponse>> captor) {
        makePictureServiceRespondWith(PICTURE_ERROR_400, captor);
    }

    private void makePictureServiceRespondNull(ArgumentCaptor<HttpProgressCallback<PictureResponse>> captor) {
        makePictureServiceRespondWith(null, captor);
    }

    private void makePictureServiceUpdateProgressTo(int progress, ArgumentCaptor<HttpProgressCallback<PictureResponse>> captor) {
        verify(pictureService).send(eq(PICTURE), eq(inspection), captor.capture());
        captor.getValue().onProgressUpdated(progress);
    }

    private void makePictureServiceFail(ArgumentCaptor<HttpProgressCallback<PictureResponse>> captor) {
        verify(pictureService).send(eq(PICTURE), eq(inspection), captor.capture());
        captor.getValue().onFailure(new IOException());
    }

    private void makePictureServiceRespondWith(HttpResponse<PictureResponse> response, ArgumentCaptor<HttpProgressCallback<PictureResponse>> captor) {
        verify(pictureService).send(eq(PICTURE), eq(inspection), captor.capture());
        captor.getValue().onResponse(response);
    }

    void makeInspectionServiceSucceed() {
        makeInspectionServiceRespondWith(PRODUCT_SUCCESS);
    }

    void makeInspectionServiceError() {
        makeInspectionServiceRespondWith(PRODUCT_ERROR_400);
    }

    void makeInspectionServiceRespondNull() {
        makeInspectionServiceRespondWith(null);
    }

    void makeInspectionServiceUpdateProgressTo(int progress) {
        verify(inspectionService).send(eq(inspection), productServiceCallback.capture());
        productServiceCallback.getValue().onProgressUpdated(progress);
    }

    void makeInspectionServiceFail() {
        verify(inspectionService).send(eq(inspection), productServiceCallback.capture());
        productServiceCallback.getValue().onFailure(new IOException());
    }

    private void makeInspectionServiceRespondWith(HttpResponse<ProductResponse> response) {
        verify(inspectionService).send(eq(inspection), productServiceCallback.capture());
        productServiceCallback.getValue().onResponse(response);
    }

    void checkStatusIsReady() {
        checkStatusIs(SyncStatus.READY);
    }

    void checkStatusIsPicturesBeingSynced() {
        checkStatusIs(SyncStatus.PICTURES_BEING_SYNCED);
    }

    void checkStatusIsPicturesSynced() {
        checkStatusIs(SyncStatus.PICTURES_SYNCED);
    }

    void checkStatusIsInspectionBeingSynced() {
        checkStatusIs(SyncStatus.INSPECTION_BEING_SYNCED);
    }

    void checkStatusIsFailed() {
        checkStatusIs(SyncStatus.FAILED);
    }

    void checkStatusIsDone() {
        checkStatusIs(SyncStatus.DONE);
    }

    private void checkStatusIs(SyncStatus status) {
        Assert.assertEquals(status, inspection.getSyncStatus());
    }

    class EmptySyncCallback implements SyncCallback {

        @Override
        public void onSuccess(Inspection inspection) {

        }

        @Override
        public void onProgressUpdated(Inspection inspection, Integer progress) {

        }

        @Override
        public void onFailure(Inspection inspection, Throwable t) {

        }
    }
}
