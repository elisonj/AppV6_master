package br.com.bg7.appvistoria.sync;

import br.com.bg7.appvistoria.data.ProductInspection;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by: luciolucio
 * Date: 2017-08-04
 */

class MockInspection {
    private ProductInspection productInspection = mock(ProductInspection.class);

    private MockInspection() { }

    static MockInspection mockInspection() {
        return new MockInspection();
    }

    MockInspection withStatus(SyncStatus syncStatus) {
        when(this.productInspection.getSyncStatus()).thenReturn(syncStatus);
        return this;
    }

    MockInspection thatCanSyncPictures() {
        when(this.productInspection.canSyncPictures()).thenReturn(true);
        return this;
    }

    MockInspection thatCannotSyncPictures() {
        when(this.productInspection.canSyncPictures()).thenReturn(false);
        return this;
    }

    MockInspection thatCanSyncProduct() {
        when(this.productInspection.canSyncProduct()).thenReturn(true);
        return this;
    }

    MockInspection thatCannotSyncProduct() {
        when(this.productInspection.canSyncProduct()).thenReturn(false);
        return this;
    }

    ProductInspection create() {
        return this.productInspection;
    }
}
