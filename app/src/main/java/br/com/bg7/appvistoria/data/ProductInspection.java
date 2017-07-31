package br.com.bg7.appvistoria.data;

import com.orm.SugarRecord;

import br.com.bg7.appvistoria.sync.SyncStatus;

/**
 * Created by: elison
 * Date: 2017-07-27
 */
public class ProductInspection extends SugarRecord<ProductInspection> {

    private SyncStatus syncStatus;

    /**
     * Default constructor used by Sugar
     */
    @SuppressWarnings("unused")
    public ProductInspection() {}

    public SyncStatus getSyncStatus() {
        return syncStatus;
    }
}
