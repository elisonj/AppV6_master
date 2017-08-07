package br.com.bg7.appvistoria.data;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.File;

import javax.annotation.Nullable;

import br.com.bg7.appvistoria.data.source.PictureService;
import br.com.bg7.appvistoria.data.source.ProductInspectionService;
import br.com.bg7.appvistoria.data.source.remote.HttpProgressCallback;
import br.com.bg7.appvistoria.data.source.remote.HttpResponse;
import br.com.bg7.appvistoria.data.source.remote.SyncCallback;
import br.com.bg7.appvistoria.data.source.remote.dto.PictureResponse;
import br.com.bg7.appvistoria.data.source.remote.dto.ProductResponse;
import br.com.bg7.appvistoria.sync.SyncStatus;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

/**
 * Created by: elison
 * Date: 2017-08-02
 */
public class ProductInspectionTest {

    private static final double PROGRESS = 50;
    private static final int FILES_TO_SYNC = 3;
    private static final File FILE = new File("");
    private boolean isSuccessful = true;

    private ProductInspection productInspection;
    private ProductInspection callBackProductInspection;
    private ProductInspection callBackProductProgress;
    private Throwable callBackThrowable;

    @Mock
    ProductInspectionService productInspectionService;

    @Mock
    PictureService pictureService;

    @Captor
    ArgumentCaptor<HttpProgressCallback<ProductResponse>> productInspectorCallback;

    @Captor
    ArgumentCaptor<HttpProgressCallback<PictureResponse>> pictureCallback;

    @Before
    public void setUp() throws IllegalStateException {
        MockitoAnnotations.initMocks(this);
        productInspection = new ProductInspection();
    }

    @Test
    public void shouldErrorSyncPicture() throws InterruptedException {
        addOneImageToSync();
        setUpSyncPictures();
        pictureCallback.getValue().onFailure(new IllegalStateException("Cannot sync"));
        verifyCallbackResponse(callBackThrowable != null);
    }

    @Test
    public void shouldSyncPicture() throws InterruptedException {
        addOneImageToSync();
        syncPictures();
        Assert.assertEquals(productInspection.getSyncStatus(), SyncStatus.PICTURES_SYNCED);
    }

    @Test
    public void shouldShowFailWhenSyncPicture() throws InterruptedException {
        isSuccessful = false;
        addOneImageToSync();
        syncPictures();
        Assert.assertEquals(productInspection.getSyncStatus(), SyncStatus.FAILED);
    }

    @Test
    public void shouldShowFailWhenSyncPictureResponseIsNull() throws InterruptedException {
        addOneImageToSync();
        syncPicturesWithResponseNull();
        Assert.assertEquals(productInspection.getSyncStatus(), SyncStatus.FAILED);
    }

    @Test
    public void shouldStatusPictureBeingSyncedWhenProgressUpdated() throws InterruptedException {
        addOneImageToSync();
        setUpSyncPictures();
        pictureCallback.getValue().onProgressUpdated(PROGRESS);
        verifyCallbackResponse(callBackProductProgress != null);
        Assert.assertEquals(productInspection.getSyncStatus(), SyncStatus.PICTURES_BEING_SYNCED);
    }

    @Test
    public void shouldShowBeingSyncPictureWhenSendPartialList() throws InterruptedException {
        addImageToSync(FILES_TO_SYNC);
        syncPictures();
        Assert.assertEquals(productInspection.getSyncStatus(), SyncStatus.PICTURES_BEING_SYNCED);
    }

    @Test
    public void shouldShowPicturesSyncedWhenSendAll() throws InterruptedException {
        addImageToSync(FILES_TO_SYNC);
        shouldSyncStatusReady();
        numberOfTimesToSync(FILES_TO_SYNC);
        Assert.assertEquals(productInspection.getSyncStatus(), SyncStatus.PICTURES_SYNCED);
    }

    @Test
    public void shouldSyncProductInspection() throws InterruptedException {
        syncProduct();
        productInspectorCallback.getValue().onResponse(productResponse);
        Assert.assertEquals(productInspection.getSyncStatus(), SyncStatus.DONE);
    }

    @Test
    public void shouldShowFailedProductInspectionWhenResponseIsNull() throws InterruptedException {
        syncProduct();
        productInspectorCallback.getValue().onResponse(null);
        Assert.assertEquals(productInspection.getSyncStatus(), SyncStatus.FAILED);
    }

    @Test
    public void shouldShowFailedProductInspectionWhenIsNotSuccessful() throws InterruptedException {
        trySyncProductWhenCannotSync();
        productInspectorCallback.getValue().onResponse(productResponse);
        Assert.assertFalse(productResponse.isSuccessful());
        Assert.assertEquals(productInspection.getSyncStatus(), SyncStatus.FAILED);
    }

    @Test
    public void shouldErrorProductInspection() throws InterruptedException {
        syncProduct();
        productInspectorCallback.getValue().onFailure(new IllegalStateException("Cannot sync"));
        Assert.assertEquals(productInspection.getSyncStatus(), SyncStatus.FAILED);
    }

    @Test
    public void shouldStatusProductBeingSyncedWhenProgressUpdated() throws InterruptedException {
        syncProduct();
        productInspectorCallback.getValue().onProgressUpdated(PROGRESS);
        Assert.assertEquals(productInspection.getSyncStatus(), SyncStatus.INSPECTION_BEING_SYNCED);
    }

    @Test
    public void shouldCanSyncProduct() throws InterruptedException {
        setUpSyncProduct();
    }

    @Test
    public void shouldCanSyncPicture() throws InterruptedException {
        shouldSyncStatusReady();
    }

    @Test
    public void shouldCanReady() throws InterruptedException {
        canReady();
    }

    private void numberOfTimesToSync(int cont) {
        for(int i=0; i<cont; i++) {
            productInspection.sync(pictureService, callback);
        }
        verify(pictureService, Mockito.times(cont)).send(eq(FILE), eq(productInspection), pictureCallback.capture());
        pictureCallback.getValue().onResponse(pictureResponse);
    }

    private void verifyCallbackResponse(boolean condition) {
        Assert.assertTrue(condition);
    }

    private void shouldSyncStatusReady(){
        canReady();
        canSyncPictures();
    }

    private void canReady() {
        Assert.assertEquals(productInspection.ready(), SyncStatus.READY);
    }

    private void addOneImageToSync() throws InterruptedException {
            addImageToSync(1);
    }

    private void addImageToSync(int cont) throws InterruptedException{
        for(int i=0; i<cont; i++) {
            productInspection.addImageToSync(FILE);
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
        productInspection.sync(pictureService, callback);
        verify(pictureService).send(eq(FILE), eq(productInspection), pictureCallback.capture());
    }

    private void canSyncPictures() {
        Assert.assertTrue(productInspection.canSyncPictures());
    }

    private void syncProduct() throws InterruptedException {
        setUpSyncProduct();
        verifySyncProduct();
    }

    private void trySyncProductWhenCannotSync() throws InterruptedException {
        setUpSyncProduct();
        isSuccessful = false;
        verifySyncProduct();
    }

    private void verifySyncProduct() {
        productInspection.sync(productInspectionService, callback);
        verify(productInspectionService).send(eq(productInspection), productInspectorCallback.capture());
        verifyCallbackResponse(callBackProductInspection != null);
    }

    private void setUpSyncProduct() throws InterruptedException {
        addImageToSync(1);
        syncPictures();
        canSyncProduct();
    }

    private void canSyncProduct() {
        Assert.assertTrue(productInspection.canSyncProduct());
    }

    private SyncCallback callback = new SyncCallback() {
        @Override
        public void onSuccess(ProductInspection productInspection) {
            callBackProductInspection = productInspection;
        }

        @Override
        public void onProgressUpdated(ProductInspection productInspection, double progress) {
            callBackProductProgress = productInspection;
        }

        @Override
        public void onFailure(ProductInspection productInspection, Throwable t) {
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
