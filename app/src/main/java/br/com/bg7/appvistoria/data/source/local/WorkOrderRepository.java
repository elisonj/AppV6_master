package br.com.bg7.appvistoria.data.source.local;

import java.util.List;

import br.com.bg7.appvistoria.data.WorkOrder;

/**
 * Created by: elison
 * Date: 2017-08-15
 */
public interface WorkOrderRepository extends Repository<WorkOrder> {
    List<WorkOrder> findAllOrderByStatus(String language);
    List<WorkOrder> findAllOrderByProjectAndAddress(String description,  String address);
}
