package br.com.bg7.appvistoria.sync;

import org.junit.Test;

/**
 * Created by: luciolucio
 * Date: 2017-07-31
 */

public class SyncManagerTest {
    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullSubscriber()
    {
        SyncManager syncManager = new SyncManager();
        syncManager.subscribe(null);
    }

    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullToUnsubscribe()
    {
        SyncManager syncManager = new SyncManager();
        syncManager.unsubscribe(null);
    }
}
