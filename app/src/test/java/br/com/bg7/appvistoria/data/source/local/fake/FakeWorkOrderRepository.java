package br.com.bg7.appvistoria.data.source.local.fake;

import java.util.ArrayList;
import java.util.List;

import br.com.bg7.appvistoria.data.WorkOrder;
import br.com.bg7.appvistoria.data.source.local.WorkOrderRepository;
import br.com.bg7.appvistoria.workorder.WorkOrderStatus;

/**
 * Created by: elison
 * Date: 2017-08-17
 */
public class FakeWorkOrderRepository extends FakeRepository<String, WorkOrder> implements WorkOrderRepository {

    @Override
    public List<WorkOrder> findAllOrderByStatus(String language) {

        List<WorkOrder> workOrderList = new ArrayList<>();
        WorkOrder ob1 = new WorkOrder("Projeto 1", "Resumo completo",  WorkOrderStatus.IN_PROGRESS);
        WorkOrder ob2 = new WorkOrder("Projeto 2", "Resumo completo",  WorkOrderStatus.COMPLETED);
        WorkOrder ob3 = new WorkOrder("Projeto 3", "Resumo completo",  WorkOrderStatus.NOT_STARTED);
        WorkOrder ob4 = new WorkOrder("Projeto 4", "Resumo completo",  WorkOrderStatus.IN_PROGRESS);
        WorkOrder ob5 = new WorkOrder("Projeto 5", "Resumo completo",  WorkOrderStatus.COMPLETED);
        WorkOrder ob6 = new WorkOrder("Projeto 6", "Resumo completo",  WorkOrderStatus.NOT_STARTED);
        WorkOrder ob7 = new WorkOrder("Projeto 7", "Resumo completo",  WorkOrderStatus.IN_PROGRESS);
        WorkOrder ob8 = new WorkOrder("Projeto 8", "Resumo completo",  WorkOrderStatus.COMPLETED);
        WorkOrder ob9 = new WorkOrder("Projeto 9", "Resumo completo",  WorkOrderStatus.NOT_STARTED);

        workOrderList.add(ob1);
        workOrderList.add(ob2);
        workOrderList.add(ob3);
        workOrderList.add(ob4);
        workOrderList.add(ob5);
        workOrderList.add(ob6);
        workOrderList.add(ob7);
        workOrderList.add(ob8);
        workOrderList.add(ob9);

        return  workOrderList;
    }

    @Override
    String getKey(WorkOrder entity) {
        return entity.getName();
    }
}
