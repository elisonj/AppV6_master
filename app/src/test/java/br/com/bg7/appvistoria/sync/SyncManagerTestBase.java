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

class SyncManagerTestBase {
    FakeProductInspectionRepository productInspectionRepository = new FakeProductInspectionRepository();

    @Mock
    SyncExecutor syncExecutor;

    @Mock
    ProductInspectionService productInspectionService;

    @Mock
    PictureService pictureService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
}
