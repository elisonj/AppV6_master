package br.com.bg7.appvistoria.sync;

import org.junit.Before;
import org.junit.Test;

import br.com.bg7.appvistoria.data.source.local.ProductInspectionRepository;
import br.com.bg7.appvistoria.data.source.local.fake.FakeProductInspectionRepository;

/**
 * Created by: luciolucio
 * Date: 2017-07-31
 */

public class SyncManagerTest {
    private SyncManager syncManager;
    private ProductInspectionRepository productInspectionRepository;

    @Before
    public void setUp() {
        productInspectionRepository = new FakeProductInspectionRepository();
        syncManager = new SyncManager(5, productInspectionRepository);
    }

    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullRepository() {
        syncManager = new SyncManager(5, null);
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
}
