package br.com.bg7.appvistoria.data.source.local.ormlite;

import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.com.bg7.appvistoria.data.WorkOrder;
import br.com.bg7.appvistoria.data.source.local.WorkOrderRepository;

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
        if(language == null)  return null;

        List<WorkOrder> workOrderList = dao.queryForAll();

        //TODO: Ver se dá para ordernar pelo banco
        Collections.sort(workOrderList, new Comparator<WorkOrder>() {
            @Override
            public int compare(WorkOrder workOrder, WorkOrder workOrder1) {
                return workOrder.getStatus().compareTo(workOrder1.getStatus());
            }
        });

        return workOrderList;
    }
}

