package br.com.bg7.appvistoria.sync;

import org.junit.Test;

import br.com.bg7.appvistoria.data.Inspection;

import static br.com.bg7.appvistoria.sync.MockInspection.mockInspection;

/**
 * Created by: elison
 * Date: 2017-08-05
 */

public class SyncManagerIllegalStateExceptionTest extends SyncManagerTestBase {
    @Test
    public void shouldNotCallOnFailureForIllegalStateExceptionPicture() {
        Inspection inspection = mockInspection()
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
        Inspection inspection = mockInspection()
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
