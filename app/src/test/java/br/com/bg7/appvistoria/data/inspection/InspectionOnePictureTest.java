package br.com.bg7.appvistoria.data.inspection;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import java.io.File;

/**
 * Created by: elison
 * Date: 2017-08-02
 */
public class InspectionOnePictureTest extends InspectionTestBase {

    @Before
    public void setUp() {
        super.setUp();
        inspection.addImageToSync(new File(""));
    }

    @Test
    public void shouldBeAbleToSyncPicturesWithOnePicture() {
        Assert.assertTrue(inspection.canSyncPictures());
    }
}
