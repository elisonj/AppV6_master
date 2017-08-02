package br.com.bg7.appvistoria.data;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.SyncFailedException;

import br.com.bg7.appvistoria.data.source.PictureService;
import br.com.bg7.appvistoria.data.source.ProductInspectionService;
import br.com.bg7.appvistoria.data.source.remote.SyncCallback;
import br.com.bg7.appvistoria.sync.SyncStatus;

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
    ArgumentCaptor<SyncCallback> productCallBackCaptor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        productInspection = new ProductInspection();
    }

    @Test
    public void shouldSyncProductInspection() throws SyncFailedException{
        Assert.assertEquals(productInspection.ready(), SyncStatus.READY);

        productInspection.sync(productInspectionService, productCallBackCaptor.capture());
        productCallBackCaptor.getValue().onSuccess(productInspection);
        Assert.assertEquals(productInspection.getSyncStatus(), SyncStatus.PRODUCT_INSPECTION_SYNCED);
    }
}
