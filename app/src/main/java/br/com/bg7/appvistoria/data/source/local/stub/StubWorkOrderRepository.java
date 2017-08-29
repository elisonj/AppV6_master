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

        WorkOrder wo1 = new WorkOrder("Projeto 1", "- Carros: 50, motos: 30, caminhões: 20, vans: 13, empilhadeiras: 5, trator: 1", "Av. Colombo, 500 Maringá - PR");
        WorkOrder wo2 = new WorkOrder("Projeto 2", "-- Carros: 50, motos: 30, caminhões: 20, vans: 13, empilhadeiras: 5, trator: 1", "Av. Colombo, 500 Maringá - PR");
        WorkOrder wo3 = new WorkOrder("Projeto 3", "---- Carros: 50, motos: 30, caminhões: 20, vans: 13, empilhadeiras: 5, trator: 1", "Av. Colombo, 500 Maringá - PR");
        WorkOrder wo4 = new WorkOrder("Projeto 4", "----- Carros: 50, motos: 30, caminhões: 20, vans: 13, empilhadeiras: 5, trator: 1", "Av. Colombo, 500 Maringá - PR");
        WorkOrder wo5 = new WorkOrder("Projeto 5", "------ Carros: 50, motos: 30, caminhões: 20, vans: 13, empilhadeiras: 5, trator: 1", "Av. Colombo, 500 Maringá - PR");
        WorkOrder wo6 = new WorkOrder("Projeto 6", "------- Carros: 50, motos: 30, caminhões: 20, vans: 13, empilhadeiras: 5, trator: 1", "Av. Colombo, 500 Maringá - PR");
        WorkOrder wo7 = new WorkOrder("Projeto 7", "-------- Carros: 50, motos: 30, caminhões: 20, vans: 13, empilhadeiras: 5, trator: 1", "Av. Colombo, 500 Maringá - PR");
        WorkOrder wo8 = new WorkOrder("Projeto 8", "--------- Carros: 50, motos: 30, caminhões: 20, vans: 13, empilhadeiras: 5, trator: 1", "Av. Colombo, 500 Maringá - PR");
        WorkOrder wo9 = new WorkOrder("Projeto 9", "---------- Carros: 50, motos: 30, caminhões: 20, vans: 13, empilhadeiras: 5, trator: 1", "Av. Colombo, 500 Maringá - PR");

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
