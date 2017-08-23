package br.com.bg7.appvistoria.data.source.local.fake;

import java.util.ArrayList;
import java.util.List;

import br.com.bg7.appvistoria.data.WorkOrder;
import br.com.bg7.appvistoria.data.source.local.WorkOrderRepository;
import br.com.bg7.appvistoria.workorder.CompletedWorkOrder;
import br.com.bg7.appvistoria.workorder.InProgressWorkOrder;

/**
 * Created by: elison
 * Date: 2017-08-17
 */
public class FakeWorkOrderRepository extends FakeRepository<String, WorkOrder> implements WorkOrderRepository {

    @Override
    public List<WorkOrder> findAllOrderByStatus(String language) {

        List<WorkOrder> workOrderList = new ArrayList<>();
        WorkOrder ob1 = new InProgressWorkOrder("Projeto 1", "Resumo completo");
        WorkOrder ob2 = new CompletedWorkOrder("Projeto 2", "Resumo completo");
        WorkOrder ob3 = new WorkOrder("Projeto 3", "Resumo completo");
        WorkOrder ob4 = new InProgressWorkOrder("Projeto 4", "Resumo completo");
        WorkOrder ob5 = new CompletedWorkOrder("Projeto 5", "Resumo completo");
        WorkOrder ob6 = new WorkOrder("Projeto 6", "Resumo completo");
        WorkOrder ob7 = new InProgressWorkOrder("Projeto 7", "Resumo completo");
        WorkOrder ob8 = new CompletedWorkOrder("Projeto 8", "Resumo completo");
        WorkOrder ob9 = new WorkOrder("Projeto 9", "Resumo completo");

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
