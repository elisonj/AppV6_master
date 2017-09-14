package br.com.bg7.appvistoria.data.source.local.stub;

import java.util.ArrayList;
import java.util.List;

import br.com.bg7.appvistoria.data.Inspection;
import br.com.bg7.appvistoria.data.StubInspection;
import br.com.bg7.appvistoria.data.WorkOrder;
import br.com.bg7.appvistoria.data.source.local.InspectionRepository;
import br.com.bg7.appvistoria.sync.SyncStatus;
import br.com.bg7.appvistoria.sync.InspectionStatus;

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
    public List<Inspection> findBySyncStatus(InspectionStatus status) {
        return getInspectionData();
    }

    private List<Inspection> getInspectionData() {
        List<Inspection> list = new ArrayList<>();

        WorkOrder workOrder = new WorkOrder("Name","Summary", "Address");


        StubInspection inspection1 = new StubInspection(1L,"FORD/BB51");
        inspection1.setWorkOrder(workOrder);
        StubInspection inspection2 = new StubInspection(2L,"FORD/BB51");
        inspection2.setWorkOrder(workOrder);
        StubInspection inspection3 = new StubInspection(3L,"FORD/BB51");
        inspection3.setWorkOrder(workOrder);
        StubInspection inspection4 = new StubInspection(4L,"FORD/BB51");
        inspection4.setWorkOrder(workOrder);
        StubInspection inspection5 = new StubInspection(5L,"FORD/BB51");
        inspection5.setWorkOrder(workOrder);

        inspection1.readyToSync();
        inspection2.setSyncStatus(SyncStatus.INSPECTION_BEING_SYNCED);
        inspection3.setSyncStatus(SyncStatus.FAILED);
        inspection4.setSyncStatus(SyncStatus.DONE);
        inspection5.readyToSync();

        list.add(inspection1);
        list.add(inspection2);
        list.add(inspection3);
        list.add(inspection4);
        list.add(inspection5);

        return list;
    }


}
