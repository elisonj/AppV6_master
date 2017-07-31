package br.com.bg7.appvistoria.data.source.local.fake;

import br.com.bg7.appvistoria.data.ProductInspection;
import br.com.bg7.appvistoria.data.source.local.ProductInspectionRepository;

/**
 * Created by: luciolucio
 * Date: 2017-07-31
 */

public class FakeProductInspectionRepository extends FakeRepository<Long, ProductInspection> implements ProductInspectionRepository {
    @Override
    Long getKey(ProductInspection entity) {
        return entity.getId();
    }
}
