package br.com.bg7.appvistoria.data.source.local.stub;

import java.util.ArrayList;
import java.util.List;

import br.com.bg7.appvistoria.data.Inspection;
import br.com.bg7.appvistoria.data.WorkOrder;
import br.com.bg7.appvistoria.data.source.local.InspectionRepository;
import br.com.bg7.appvistoria.sync.SyncStatus;

/**
 * Created by: elison
 * Date: 2017-09-12
 */
public class StubInspectionRepository  implements InspectionRepository {
    @Override
    public void save(Inspection entity) {

    }

    @Override
    public Iterable<Inspection> findBySyncStatus(SyncStatus status) {
        return getInspectionData();
    }

    @Override
    public List<Inspection> findBySyncStatusNotNull() {
        return getInspectionData();
    }

    public List<Inspection> getInspectionData() {
        List<Inspection> list = new ArrayList<>();

        WorkOrder workOrder = new WorkOrder("Name","Summary", "Address");


        Inspection inspection1 = new Inspection();
        inspection1.setWorkOrder(workOrder);
        Inspection inspection2 = new Inspection();
        inspection2.setWorkOrder(workOrder);
        Inspection inspection3 = new Inspection();
        inspection3.setWorkOrder(workOrder);
        Inspection inspection4 = new Inspection();
        inspection4.setWorkOrder(workOrder);
        Inspection inspection5 = new Inspection();
        inspection5.setWorkOrder(workOrder);

        inspection1.readyToSync();
        inspection2.readyToSync();
        inspection3.readyToSync();
        inspection4.readyToSync();
        inspection5.readyToSync();

        list.add(inspection1);
        list.add(inspection2);
        list.add(inspection3);
        list.add(inspection4);
        list.add(inspection5);

        return list;
    }

}
