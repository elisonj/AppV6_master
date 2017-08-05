package br.com.bg7.appvistoria.sync;

import junit.framework.Assert;

import org.junit.Test;

import br.com.bg7.appvistoria.data.ProductInspection;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

/**
 * Created by: luciolucio
 * Date: 2017-08-02
 */

public class SyncManagerInitializationTest extends SyncManagerTestBase {

    private SyncManager instantiateSyncManager() {
        return new SyncManager(
                productInspectionRepository,
                productInspectionService,
                pictureService,
                syncExecutor);
    }

    private SyncManager instantiateSyncManagerWith(ProductInspection productInspection)
    {
        productInspectionRepository.save(productInspection);
        return instantiateSyncManager();
    }

    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullRepository() {
        new SyncManager(null, productInspectionService, pictureService, syncExecutor);
    }

    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullExecutor() {
        new SyncManager(productInspectionRepository, productInspectionService, pictureService, null);
    }

    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullProductInspectionService() {
        new SyncManager(productInspectionRepository, null, pictureService, syncExecutor);
    }

    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullPictureService() {
        new SyncManager(productInspectionRepository, productInspectionService, null, syncExecutor);
    }

    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullSubscriber()
    {
        instantiateSyncManager().subscribe(null);
    }

    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullToUnsubscribe()
    {
        instantiateSyncManager().unsubscribe(null);
    }

    @Test
    public void shouldScheduleQueueUpdates() {
        instantiateSyncManager();
        verify(syncExecutor).scheduleQueueUpdates(any(Runnable.class));
    }

    @Test
    public void shouldScheduleSyncLoop() {
        instantiateSyncManager();
        verify(syncExecutor).scheduleSyncLoop(any(Runnable.class));
    }
}
