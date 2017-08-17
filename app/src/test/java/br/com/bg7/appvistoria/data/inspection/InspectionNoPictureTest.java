package br.com.bg7.appvistoria.data.inspection;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;

import javax.annotation.Nullable;

import br.com.bg7.appvistoria.data.Inspection;
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
public class InspectionNoPictureTest extends InspectionTestBase {

    @Before
    public void setUp() {
        inspection = new Inspection();
    }

    @Test
    public void shouldInitializeWithStatusReady() {
        Assert.assertEquals(SyncStatus.READY, inspection.getSyncStatus());
    }

    @Test
    public void shouldNotBeAbleToSyncPicturesWithNoPictures() {
        Assert.assertFalse(inspection.canSyncPictures());
    }

    @Test
    public void shouldBeAbleToSyncProductWithNoPictures() {
        Assert.assertTrue(inspection.canSyncProduct());
    }
}
