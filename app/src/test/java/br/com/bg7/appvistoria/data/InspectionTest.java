package br.com.bg7.appvistoria.data;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;

import javax.annotation.Nullable;

import br.com.bg7.appvistoria.data.source.remote.InspectionService;
import br.com.bg7.appvistoria.data.source.remote.PictureService;
import br.com.bg7.appvistoria.data.source.remote.callback.SyncCallback;
import br.com.bg7.appvistoria.data.source.remote.dto.PictureResponse;
import br.com.bg7.appvistoria.data.source.remote.dto.ProductResponse;
import br.com.bg7.appvistoria.data.source.remote.http.HttpProgressCallback;
import br.com.bg7.appvistoria.data.source.remote.http.HttpResponse;
import br.com.bg7.appvistoria.sync.SyncStatus;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;

/**
 * Created by: elison
 * Date: 2017-08-02
 */
public class InspectionTest {

    private static final double PROGRESS = 50;
    private static final int FILES_TO_SYNC = 3;
    private static final File FILE = new File("");
    private boolean isSuccessful = true;

    private Inspection inspection;
    private Inspection callBackInspection;
    private Inspection callBackProductProgress;
    private Throwable callBackThrowable;

    @Mock
    InspectionService inspectionService;

    @Mock
    PictureService pictureService;

    @Captor
    ArgumentCaptor<HttpProgressCallback<ProductResponse>> productServiceCallback;

    @Captor
    ArgumentCaptor<HttpProgressCallback<PictureResponse>> pictureCallback;

    @Before
    public void setUp() throws IllegalStateException {
        MockitoAnnotations.initMocks(this);
        inspection = new Inspection();
    }

    @Test
    public void shouldErrorSyncPicture() {
        addOneImageToSync();
        setUpSyncPictures();
        pictureCallback.getValue().onFailure(new IllegalStateException("Cannot sync"));
        verifyCallbackResponse(callBackThrowable != null);
    }

    @Test
    public void shouldSyncPicture() {
        addOneImageToSync();
        syncPictures();
        Assert.assertEquals(inspection.getSyncStatus(), SyncStatus.PICTURES_SYNCED);
    }

    @Test
    public void shouldShowFailWhenSyncPicture() {
        isSuccessful = false;
        addOneImageToSync();
        syncPictures();
        verifyStatusFailed();
    }

    @Test
    public void shouldShowFailWhenSyncPictureResponseIsNull() {
        addOneImageToSync();
        syncPicturesWithResponseNull();
        verifyStatusFailed();
    }

    @Test
    public void shouldStatusPictureBeingSyncedWhenProgressUpdated() {
        addOneImageToSync();
        setUpSyncPictures();
        pictureCallback.getValue().onProgressUpdated(PROGRESS);
        verifyCallbackResponse(callBackProductProgress != null);
        verifyStatusPictureBeingSynced();
    }

    @Test
    public void shouldShowBeingSyncPictureWhenSendPartialList() {
        addImageToSync(FILES_TO_SYNC);
        syncPictures();
        verifyStatusPictureBeingSynced();
    }

    @Test
    public void shouldShowPicturesSyncedWhenSendAll() {
        addImageToSync(FILES_TO_SYNC);
        shouldSyncStatusReady();
        numberOfTimesToSync(FILES_TO_SYNC);
        Assert.assertEquals(inspection.getSyncStatus(), SyncStatus.PICTURES_SYNCED);
    }

    @Test
    public void shouldSyncInspection() {
        syncProduct();
        productServiceCallback.getValue().onResponse(productResponse);
        Assert.assertEquals(inspection.getSyncStatus(), SyncStatus.DONE);
    }

    @Test
    public void shouldShowFailedInspectionWhenResponseIsNull() {
        syncProduct();
        productServiceCallback.getValue().onResponse(null);
        verifyStatusFailed();
    }

    @Test
    public void shouldShowFailedInspectionWhenIsNotSuccessful() {
        trySyncProductWhenCannotSync();
        productServiceCallback.getValue().onResponse(productResponse);
        Assert.assertFalse(productResponse.isSuccessful());
        verifyStatusFailed();
    }

    @Test
    public void shouldErrorInspection() {
        syncProduct();
        productServiceCallback.getValue().onFailure(new IllegalStateException("Cannot sync"));
        verifyStatusFailed();
    }

    @Test
    public void shouldStatusProductBeingSyncedWhenProgressUpdated() {
        syncProduct();
        productServiceCallback.getValue().onProgressUpdated(PROGRESS);
        Assert.assertEquals(inspection.getSyncStatus(), SyncStatus.INSPECTION_BEING_SYNCED);
    }

    @Test
    public void shouldCanSyncProduct() {
        setUpSyncProduct();
    }

    @Test
    public void shouldCanSyncPicture() {
        shouldSyncStatusReady();
    }

    @Test
    public void shouldReady() {
        verifyStatusCanReady();
    }

    private void numberOfTimesToSync(int cont) {
        for(int i=0; i<cont; i++) {
            reset(pictureService);
            inspection.sync(pictureService, callback);
            verify(pictureService).send(eq(FILE), eq(inspection), pictureCallback.capture());
            pictureCallback.getValue().onResponse(pictureResponse);
        }
    }

    private void verifyStatusFailed() {
        Assert.assertEquals(inspection.getSyncStatus(), SyncStatus.FAILED);
    }

    private void verifyCallbackResponse(boolean condition) {
        Assert.assertTrue(condition);
    }

    private void verifyStatusPictureBeingSynced() {
        Assert.assertEquals(inspection.getSyncStatus(), SyncStatus.PICTURES_BEING_SYNCED);
    }

    private void shouldSyncStatusReady(){
        verifyStatusCanReady();
        canSyncPictures();
    }

    private void verifyStatusCanReady() {
        Assert.assertEquals(inspection.getSyncStatus(), SyncStatus.READY);
    }

    private void addOneImageToSync() {
            addImageToSync(1);
    }

    private void addImageToSync(int cont) {
        for(int i=0; i<cont; i++) {
            inspection.addImageToSync(FILE);
        }
    }

    private void syncPictures() {
        setUpSyncPictures();
        pictureCallback.getValue().onResponse(pictureResponse);
    }

    private void syncPicturesWithResponseNull() {
        setUpSyncPictures();
        pictureCallback.getValue().onResponse(null);
        verifyCallbackResponse(callBackThrowable != null);
    }

    private void setUpSyncPictures() {
        shouldSyncStatusReady();
        inspection.sync(pictureService, callback);
        verify(pictureService).send(eq(FILE), eq(inspection), pictureCallback.capture());
    }

    private void canSyncPictures() {
        Assert.assertTrue(inspection.canSyncPictures());
    }

    private void syncProduct() {
        setUpSyncProduct();
        verifySyncProduct();
    }

    private void trySyncProductWhenCannotSync() {
        setUpSyncProduct();
        isSuccessful = false;
        verifySyncProduct();
    }

    private void verifySyncProduct() {
        inspection.sync(inspectionService, callback);
        verify(inspectionService).send(eq(inspection), productServiceCallback.capture());
        verifyCallbackResponse(callBackInspection != null);
    }

    private void setUpSyncProduct() {
        addImageToSync(1);
        syncPictures();
        canSyncProduct();
    }

    private void canSyncProduct() {
        Assert.assertTrue(inspection.canSyncProduct());
    }

    private SyncCallback callback = new SyncCallback() {
        @Override
        public void onSuccess(Inspection inspection) {
            callBackInspection = inspection;
        }

        @Override
        public void onProgressUpdated(Inspection inspection, double progress) {
            callBackProductProgress = inspection;
        }

        @Override
        public void onFailure(Inspection inspection, Throwable t) {
            callBackThrowable = t;
        }
    };

    private HttpResponse<PictureResponse> pictureResponse = new HttpResponse<PictureResponse>() {
        @Override
        public boolean isSuccessful() {
            return isSuccessful;
        }

        @Nullable
        @Override
        public PictureResponse body() {
            return null;
        }

        @Override
        public int code() {
            return 0;
        }
    };

    private HttpResponse<ProductResponse> productResponse = new HttpResponse<ProductResponse>() {
        @Override
        public boolean isSuccessful() {
            return isSuccessful;
        }

        @Nullable
        @Override
        public ProductResponse body() {
            return null;
        }

        @Override
        public int code() {
            return 0;
        }
    };
}
