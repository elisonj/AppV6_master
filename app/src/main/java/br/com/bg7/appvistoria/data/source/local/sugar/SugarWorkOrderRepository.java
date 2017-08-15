package br.com.bg7.appvistoria.data.source.local.sugar;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.com.bg7.appvistoria.data.WorkOrder;
import br.com.bg7.appvistoria.data.source.local.WorkOrderRepository;

/**
 * Created by: elison
 * Date: 2017-08-15
 */
public class SugarWorkOrderRepository extends SugarRepository<WorkOrder> implements WorkOrderRepository {
    public SugarWorkOrderRepository() {
        super(WorkOrder.class);
    }

    @Override
    public List<WorkOrder> findAllOrderByStatus(String language) {
        if(language == null)  return null;

        String[] params = new String[]{language};
        List<WorkOrder> workOrderList = WorkOrder.find(WorkOrder.class, "language = ?", params);



        if (workOrderList.size() <= 0) {
            return null;
        }

        Collections.sort(workOrderList, new Comparator<WorkOrder>() {
            @Override
            public int compare(WorkOrder workOrder, WorkOrder workOrder1) {
                return workOrder.getStatus().compareTo(workOrder1.getStatus());
            }
        });

        return workOrderList;
    }


}
