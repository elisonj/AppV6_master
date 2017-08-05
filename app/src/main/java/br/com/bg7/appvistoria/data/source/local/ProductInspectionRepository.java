package br.com.bg7.appvistoria.data.source.local;

import br.com.bg7.appvistoria.data.ProductInspection;
import br.com.bg7.appvistoria.sync.SyncStatus;

/**
 * Created by: luciolucio
 * Date: 2017-07-31
 *
 * TODO: Resolver o problema de ter que passar a classe em {@link #findById(Class, Long)},
 * {@link #deleteAll(Class)} e {@link #first(Class)}
 */

public interface ProductInspectionRepository extends Repository<ProductInspection> {
    Iterable<ProductInspection> findBySyncStatus(SyncStatus status);
}
