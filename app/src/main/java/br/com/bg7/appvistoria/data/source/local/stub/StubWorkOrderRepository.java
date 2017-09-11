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

    private WorkOrder wo1 = new WorkOrder("Projeto 1", "- Carros: 50, motos: 30, caminhões: 20, vans: 13, empilhadeiras: 5, trator: 1", "Av. Colombo, 500 Maringá - PR");
    private WorkOrder wo2 = new WorkOrder("Projeto 2", "-- Carros: 50, motos: 30, caminhões: 20, vans: 13, empilhadeiras: 5, trator: 1", "Av. Colombo, 501 Maringá - PR");
    private WorkOrder wo3 = new WorkOrder("Projeto 3", "---- Carros: 50, motos: 30, caminhões: 20, vans: 13, empilhadeiras: 5, trator: 1", "Av. Colombo, 502 Maringá - PR");
    private WorkOrder wo4 = new WorkOrder("Projeto 4", "----- Carros: 50, motos: 30, caminhões: 20, vans: 13, empilhadeiras: 5, trator: 1", "Av. Colombo, 503 Maringá - PR");
    private WorkOrder wo5 = new WorkOrder("Projeto 5", "------ Carros: 50, motos: 30, caminhões: 20, vans: 13, empilhadeiras: 5, trator: 1", "Av. Colombo, 500 Maringá - PR");
    private WorkOrder wo6 = new WorkOrder("Projeto 6", "------- Carros: 50, motos: 30, caminhões: 20, vans: 13, empilhadeiras: 5, trator: 1", "Av. Colombo, 501 Maringá - PR");
    private WorkOrder wo7 = new WorkOrder("Projeto 7", "-------- Carros: 50, motos: 30, caminhões: 20, vans: 13, empilhadeiras: 5, trator: 1", "Av. Colombo, 502 Maringá - PR");
    private WorkOrder wo8 = new WorkOrder("Projeto 8", "--------- Carros: 50, motos: 30, caminhões: 20, vans: 13, empilhadeiras: 5, trator: 1", "Av. Colombo, 503 Maringá - PR");
    private WorkOrder wo9 = new WorkOrder("Projeto 9", "---------- Carros: 50, motos: 30, caminhões: 20, vans: 13, empilhadeiras: 5, trator: 1", "Av. Colombo, 500 Maringá - PR");


    @Override
    public void save(WorkOrder entity) {
        throw new RuntimeException("Stub!");
    }

    @Override
    public List<WorkOrder> findAllOrderByStatus(String language) {
        return getAllData();
    }

    @Override
    public List<WorkOrder> findAllOrderByProjectAndAddress(String description, String address) {
        List<WorkOrder> list = getAllData();

        for(WorkOrder workOrder: list) {
            if(workOrder.getName().equals(description) && address.equals("Av. Colombo, 500 Maringá - PR")) {
                list.add(workOrder);
            }
        }

        return list;
    }

    private List<WorkOrder> getAllData() {
        ArrayList<WorkOrder> list = new ArrayList<>();
        wo1.start();
        wo2.start();
        wo3.start();

        wo4.finish();
        wo5.finish();
        wo6.finish();

        list.add(wo1);
        list.add(wo2);
        list.add(wo3);
        list.add(wo4);
        list.add(wo5);
        list.add(wo6);
        list.add(wo7);
        list.add(wo8);
        list.add(wo9);

        return list;
    }

}
