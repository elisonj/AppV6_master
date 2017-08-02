package br.com.bg7.appvistoria.data;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.SyncFailedException;

import javax.annotation.Nullable;

import br.com.bg7.appvistoria.data.source.PictureService;
import br.com.bg7.appvistoria.data.source.ProductInspectionService;
import br.com.bg7.appvistoria.data.source.remote.HttpProgressCallback;
import br.com.bg7.appvistoria.data.source.remote.HttpResponse;
import br.com.bg7.appvistoria.data.source.remote.SyncCallback;
import br.com.bg7.appvistoria.data.source.remote.dto.ProductResponse;
import br.com.bg7.appvistoria.sync.SyncStatus;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

/**
 * Created by: elison
 * Date: 2017-08-02
 */
public class ProductInspectionTest {

    ProductInspection productInspection;

    @Mock
    ProductInspectionService productInspectionService;

    @Mock
    PictureService pictureService;

    @Captor
    ArgumentCaptor<HttpProgressCallback<ProductResponse>> productInspectorCallback;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        productInspection = new ProductInspection();
    }

    @Test
    public void shouldSyncProductInspection() throws SyncFailedException{
        Assert.assertEquals(productInspection.ready(), SyncStatus.READY);

        productInspection.sync(productInspectionService, callback);
        verify(productInspectionService).send(eq(productInspection), productInspectorCallback.capture());
        productInspectorCallback.getValue().onResponse(productResponse);
        Assert.assertEquals(productInspection.getSyncStatus(), SyncStatus.PRODUCT_INSPECTION_SYNCED);
    }

    private SyncCallback callback = new SyncCallback() {
        @Override
        public void onSuccess(ProductInspection productInspection) { }

        @Override
        public void onProgressUpdated(ProductInspection productInspection, double progress) { }

        @Override
        public void onFailure(ProductInspection productInspection, Throwable t) { }
    };

    private HttpResponse<ProductResponse> productResponse = new HttpResponse<ProductResponse>() {
        @Override
        public boolean isSuccessful() {
            return true;
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
