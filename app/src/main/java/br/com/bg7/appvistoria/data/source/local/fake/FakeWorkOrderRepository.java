package br.com.bg7.appvistoria.data.source.local.fake;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.com.bg7.appvistoria.data.WorkOrder;
import br.com.bg7.appvistoria.data.source.local.WorkOrderRepository;

/**
 * Created by: elison
 * Date: 2017-08-17
 */
public class FakeWorkOrderRepository extends FakeRepository<Long, WorkOrder> implements WorkOrderRepository {

    @Override
    public List<WorkOrder> findAllOrderByStatus() {
        ArrayList<WorkOrder> workOrderList = new ArrayList<>(ENTITIES_BY_KEY.values());

        Collections.sort(workOrderList, new Comparator<WorkOrder>() {
            @Override
            public int compare(WorkOrder workOrder, WorkOrder workOrder1) {
                return workOrder.getStatus().compareTo(workOrder1.getStatus());
            }
        });

        return  workOrderList;
    }

    @Override
    public WorkOrder findByProjectAndLocation(Long projectId, Long locationId) {
        return ENTITIES_BY_KEY.get(projectId);
    }

    @Override
    Long getKey(WorkOrder entity) {
        return entity.getProjectId();
    }
}
