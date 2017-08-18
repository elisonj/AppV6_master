package br.com.bg7.appvistoria.data.source.local.sugar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.com.bg7.appvistoria.data.WorkOrder;
import br.com.bg7.appvistoria.data.source.local.WorkOrderRepository;
import br.com.bg7.appvistoria.workorder.WorkOrderStatus;

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
      //  List<WorkOrder> workOrderList = WorkOrder.find(WorkOrder.class, "language = ?", params);

        List<WorkOrder> workOrderList = getTestData();

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


    private List<WorkOrder> getTestData() {
        List<WorkOrder> workOrderList = new ArrayList<>();
        WorkOrder ob1 = new WorkOrder("Projeto 1", "Resumo completo - 50 carros, 30 motos, 20 caminhões, 13 vans, 5 empilhadeiras, 1 trator", "Resumo parcial 1 - 50 carros, 30 motos, 20 caminhões, 13 vans, 5 empilhadeiras, 1 trator", WorkOrderStatus.IN_PROGRESS);
        WorkOrder ob2 = new WorkOrder("Projeto 2", "Resumo completo - 50 carros, 30 motos, 20 caminhões, 13 vans, 5 empilhadeiras, 1 trator", "Resumo parcial 2 - 50 carros, 30 motos, 20 caminhões, 13 vans, 5 empilhadeiras, 1 trator", WorkOrderStatus.COMPLETED);
        WorkOrder ob3 = new WorkOrder("Projeto 3", "Resumo completo - 50 carros, 30 motos, 20 caminhões, 13 vans, 5 empilhadeiras, 1 trator", "Resumo parcial 3 - 50 carros, 30 motos, 20 caminhões, 13 vans, 5 empilhadeiras, 1 trator", WorkOrderStatus.NOT_STARTED);
        WorkOrder ob4 = new WorkOrder("Projeto 4", "Resumo completo - 50 carros, 30 motos, 20 caminhões, 13 vans, 5 empilhadeiras, 1 trator", "Resumo parcial 4 - 50 carros, 30 motos, 20 caminhões, 13 vans, 5 empilhadeiras, 1 trator", WorkOrderStatus.IN_PROGRESS);
        WorkOrder ob5 = new WorkOrder("Projeto 5", "Resumo completo - 50 carros, 30 motos, 20 caminhões, 13 vans, 5 empilhadeiras, 1 trator", "Resumo parcial 5 - 50 carros, 30 motos, 20 caminhões, 13 vans, 5 empilhadeiras, 1 trator", WorkOrderStatus.COMPLETED);
        WorkOrder ob6 = new WorkOrder("Projeto 6", "Resumo completo - 50 carros, 30 motos, 20 caminhões, 13 vans, 5 empilhadeiras, 1 trator", "Resumo parcial 6 - 50 carros, 30 motos, 20 caminhões, 13 vans, 5 empilhadeiras, 1 trator", WorkOrderStatus.NOT_STARTED);
        WorkOrder ob7 = new WorkOrder("Projeto 7", "Resumo completo - 50 carros, 30 motos, 20 caminhões, 13 vans, 5 empilhadeiras, 1 trator", "Resumo parcial 7 - 50 carros, 30 motos, 20 caminhões, 13 vans, 5 empilhadeiras, 1 trator", WorkOrderStatus.IN_PROGRESS);
        WorkOrder ob8 = new WorkOrder("Projeto 8", "Resumo completo - 50 carros, 30 motos, 20 caminhões, 13 vans, 5 empilhadeiras, 1 trator", "Resumo parcial 8 - 50 carros, 30 motos, 20 caminhões, 13 vans, 5 empilhadeiras, 1 trator", WorkOrderStatus.COMPLETED);
        WorkOrder ob9 = new WorkOrder("Projeto 9", "Resumo completo - 50 carros, 30 motos, 20 caminhões, 13 vans, 5 empilhadeiras, 1 trator", "Resumo parcial 9 - 50 carros, 30 motos, 20 caminhões, 13 vans, 5 empilhadeiras, 1 trator", WorkOrderStatus.NOT_STARTED);

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

}
