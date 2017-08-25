package br.com.bg7.appvistoria.data.source.local.ormlite;

import com.j256.ormlite.dao.RuntimeExceptionDao;

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
public class OrmLiteWorkOrderRepository extends OrmLiteRepository<WorkOrder> implements WorkOrderRepository {

    public OrmLiteWorkOrderRepository(RuntimeExceptionDao<WorkOrder, Long> dao) {
        super(dao);
    }

    @Override
    public List<WorkOrder> findAllOrderByStatus(String language) {
        // TODO: Buscar por idioma quando tivermos idiomas
        if(language == null)  return new ArrayList<>();

        List<WorkOrder> workOrderList = getTestData();
//        List<WorkOrder> workOrderList = dao.queryForAll();

        // TODO: Ver se dá para ordernar pelo banco
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
        WorkOrder ob1 = new WorkOrder("Projeto 1", "- Carros: 50, motos: 30, caminhões: 20, vans: 13, empilhadeiras: 5, trator: 1");
        WorkOrder ob2 = new WorkOrder("Projeto 2", "-- Carros: 50, motos: 30, caminhões: 20, vans: 13, empilhadeiras: 5, trator: 1");
        WorkOrder ob3 = new WorkOrder("Projeto 3", "---- Carros: 50, motos: 30, caminhões: 20, vans: 13, empilhadeiras: 5, trator: 1");
        WorkOrder ob4 = new WorkOrder("Projeto 4", "----- Carros: 50, motos: 30, caminhões: 20, vans: 13, empilhadeiras: 5, trator: 1");
        WorkOrder ob5 = new WorkOrder("Projeto 5", "------ Carros: 50, motos: 30, caminhões: 20, vans: 13, empilhadeiras: 5, trator: 1");
        WorkOrder ob6 = new WorkOrder("Projeto 6", "------- Carros: 50, motos: 30, caminhões: 20, vans: 13, empilhadeiras: 5, trator: 1");
        WorkOrder ob7 = new WorkOrder("Projeto 7", "-------- Carros: 50, motos: 30, caminhões: 20, vans: 13, empilhadeiras: 5, trator: 1");
        WorkOrder ob8 = new WorkOrder("Projeto 8", "--------- Carros: 50, motos: 30, caminhões: 20, vans: 13, empilhadeiras: 5, trator: 1");
        WorkOrder ob9 = new WorkOrder("Projeto 9", "---------- Carros: 50, motos: 30, caminhões: 20, vans: 13, empilhadeiras: 5, trator: 1");

        ob1.setStatus(WorkOrderStatus.IN_PROGRESS);
        ob2.setStatus(WorkOrderStatus.COMPLETED);
        ob3.setStatus(WorkOrderStatus.NOT_STARTED);
        ob4.setStatus(WorkOrderStatus.IN_PROGRESS);
        ob5.setStatus(WorkOrderStatus.COMPLETED);
        ob6.setStatus(WorkOrderStatus.NOT_STARTED);
        ob7.setStatus(WorkOrderStatus.IN_PROGRESS);
        ob8.setStatus(WorkOrderStatus.COMPLETED);
        ob9.setStatus(WorkOrderStatus.NOT_STARTED);

        ob1.setAddress("Av. Colombo, 500 Maringá - PR");
        ob2.setAddress("Av. Colombo, 500 Maringá - PR");
        ob3.setAddress("Av. Colombo, 500 Maringá - PR");
        ob4.setAddress("Av. Colombo, 500 Maringá - PR");
        ob5.setAddress("Av. Colombo, 500 Maringá - PR");
        ob6.setAddress("Av. Colombo, 500 Maringá - PR");
        ob7.setAddress("Av. Colombo, 500 Maringá - PR");
        ob8.setAddress("Av. Colombo, 500 Maringá - PR");
        ob9.setAddress("Av. Colombo, 500 Maringá - PR");


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

