package br.com.bg7.appvistoria.sync;

import org.junit.Test;

import br.com.bg7.appvistoria.data.ProductInspection;

import static br.com.bg7.appvistoria.sync.MockInspection.mockInspection;

/**
 * Created by: luciolucio
 * Date: 2017-08-05
 */

public class SyncManagerIllegalStateExceptionTest extends SyncManagerTestBase {
    @Test
    public void shouldNotCallOnFailureForIllegalStateExceptionPicture() {
        ProductInspection inspection = mockInspection()
                .thatCanSyncPictures()
                .thatThrowsOnPictureSync()
                .create();
        save(inspection);
        syncManager.subscribe(new FailWhenOnFailureCalledCallback());

        runSync();

        // Verification happens when the callback gets called
    }

    @Test
    public void shouldNotCallOnFailureForIllegalStateExceptionProduct() {
        ProductInspection inspection = mockInspection()
                .thatCannotSyncPictures()
                .thatCanSyncProduct()
                .thatThrowsOnProductSync()
                .create();
        save(inspection);
        syncManager.subscribe(new FailWhenOnFailureCalledCallback());

        runSync();

        // Verification happens when the callback gets called
    }
}
