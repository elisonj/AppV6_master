package br.com.bg7.appvistoria.sync;

import org.junit.Test;

import br.com.bg7.appvistoria.data.Inspection;

import static br.com.bg7.appvistoria.sync.MockInspection.mockInspection;
import static br.com.bg7.appvistoria.sync.MockInspectionChecker.checkThat;

/**
 * Created by: luciolucio
 * Date: 2017-08-02
 */

public class SyncManagerCanSyncTest extends SyncManagerTestBase {

    @Test
    public void shouldNotAttemptToSyncPictureWhenCannotSyncPictures() {
        Inspection inspection = mockInspection()
                .thatCannotSyncPictures()
                .create();
        save(inspection);

        runSync();

        checkThat(inspection).doesNotSyncPictures();
    }

    @Test
    public void shouldNotAttemptToSyncProductWhenCannotSyncProduct() {
        Inspection inspection = mockInspection()
                .thatCannotSyncProduct()
                .create();
        save(inspection);

        runSync();

        checkThat(inspection).doesNotSyncProducts();
    }

    @Test
    public void shouldNotAttemptToSyncProductWhenCanSyncPictures() {
        Inspection inspection = mockInspection()
                .thatCanSyncPictures()
                .thatCanSyncProduct()
                .create();
        save(inspection);

        runSync();

        checkThat(inspection).doesNotSyncProducts();
    }

    @Test
    public void shouldSyncPictures() {
        Inspection inspection = mockInspection()
                .thatCanSyncPictures()
                .create();
        save(inspection);

        runSync();

        checkThat(inspection).syncsPictures();
    }

    @Test
    public void shouldSyncProductWhenCannotSyncPictures() {
        Inspection inspection = mockInspection()
                .thatCannotSyncPictures()
                .thatCanSyncProduct()
                .create();
        save(inspection);

        runSync();

        checkThat(inspection).syncsProducts();
    }
}
