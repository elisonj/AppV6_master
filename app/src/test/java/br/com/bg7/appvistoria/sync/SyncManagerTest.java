package br.com.bg7.appvistoria.sync;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.concurrent.ScheduledThreadPoolExecutor;

import br.com.bg7.appvistoria.data.source.PictureService;
import br.com.bg7.appvistoria.data.source.ProductInspectionService;
import br.com.bg7.appvistoria.data.source.local.ProductInspectionRepository;
import br.com.bg7.appvistoria.data.source.local.fake.FakeProductInspectionRepository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

/**
 * Created by: luciolucio
 * Date: 2017-07-31
 */

public class SyncManagerTest {
    private SyncManager syncManager;

    private FakeProductInspectionRepository productInspectionRepository;

    @Mock
    SyncExecutor syncExecutor;

    @Mock
    ProductInspectionService productInspectionService;

    @Mock
    PictureService pictureService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        productInspectionRepository = new FakeProductInspectionRepository();
        syncManager = new SyncManager(productInspectionRepository, productInspectionService, pictureService, syncExecutor);
    }

    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullRepository() {
        syncManager = new SyncManager(null, productInspectionService, pictureService, syncExecutor);
    }

    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullExecutor() {
        syncManager = new SyncManager(productInspectionRepository, productInspectionService, pictureService, null);
    }

    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullSubscriber()
    {
        syncManager.subscribe(null);
    }

    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullToUnsubscribe()
    {
        syncManager.unsubscribe(null);
    }

    @Test
    public void shouldScheduleQueueUpdates() {
        verify(syncExecutor).scheduleQueueUpdates(any(Runnable.class));
    }
}
