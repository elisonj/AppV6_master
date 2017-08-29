package br.com.bg7.appvistoria.data.source.local.stub;

import java.util.ArrayList;
import java.util.List;

import br.com.bg7.appvistoria.data.WorkOrder;
import br.com.bg7.appvistoria.data.source.local.WorkOrderRepository;

/**
 * Created by: luciolucio
 * Date: 2017-08-24
 */

public class StubWorkOrderRepository implements WorkOrderRepository {
    @Override
    public void save(WorkOrder entity) {
        throw new RuntimeException("Stub!");
    }

    @Override
    public List<WorkOrder> findAllOrderByStatus(String language) {
        ArrayList<WorkOrder> list = new ArrayList<>();

        list.add(new WorkOrder("name", "summary"));
        return list;
    }
}
