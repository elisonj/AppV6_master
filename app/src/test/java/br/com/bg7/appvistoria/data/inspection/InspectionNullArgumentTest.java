package br.com.bg7.appvistoria.data.inspection;

import org.junit.Test;

import br.com.bg7.appvistoria.data.Inspection;
import br.com.bg7.appvistoria.data.source.remote.InspectionService;
import br.com.bg7.appvistoria.data.source.remote.PictureService;

/**
 * Created by: luciolucio
 * Date: 2017-08-17
 */

public class InspectionNullArgumentTest extends InspectionTestBase {

    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullPictureService() {
        new Inspection().sync((PictureService) null, new EmptySyncCallback());
    }

    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullCallbackOnSyncPicture() {
        new Inspection().sync(pictureService, null);
    }

    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullInspectionService() {
        new Inspection().sync((InspectionService) null, new EmptySyncCallback());
    }

    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullCallbackOnSyncInspection() {
        new Inspection().sync(inspectionService, null);
    }

    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullImage() {
        new Inspection().addImageToSync(null);
    }
}
