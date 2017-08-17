package br.com.bg7.appvistoria.data.inspection;

import junit.framework.Assert;

import org.junit.Test;

import br.com.bg7.appvistoria.sync.SyncStatus;

/**
 * Created by: elison
 * Date: 2017-08-02
 */
public class InspectionNoPictureTest extends InspectionTestBase {

    @Test
    public void shouldInitializeWithStatusReady() {
        Assert.assertEquals(SyncStatus.READY, inspection.getSyncStatus());
    }

    @Test
    public void shouldNotBeAbleToSyncPicturesWithNoPictures() {
        Assert.assertFalse(inspection.canSyncPictures());
    }

    @Test(expected = IllegalStateException.class)
    public void shouldNotAllowSyncPicturesWithNoPictures() {
        inspection.sync(pictureService, new EmptySyncCallback());
    }

    @Test
    public void shouldBeAbleToSyncProductWithNoPictures() {
        Assert.assertTrue(inspection.canSyncProduct());
    }

    @Test
    public void shouldNotChangeStatusFromReadyWhenResetWithNoPictures() {
        inspection.reset();

        Assert.assertEquals(SyncStatus.READY, inspection.getSyncStatus());
    }
}
