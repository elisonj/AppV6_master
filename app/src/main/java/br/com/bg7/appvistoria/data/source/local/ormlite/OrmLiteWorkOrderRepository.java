package br.com.bg7.appvistoria.data.source.local.ormlite;

import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.com.bg7.appvistoria.data.WorkOrder;
import br.com.bg7.appvistoria.data.source.local.WorkOrderRepository;

import static br.com.bg7.appvistoria.data.WorkOrder.LOCATION_ID_FIELD;
import static br.com.bg7.appvistoria.data.WorkOrder.PROJECT_ID_FIELD;

/**
 * Created by: elison
 * Date: 2017-08-15
 */
public class OrmLiteWorkOrderRepository extends OrmLiteRepository<WorkOrder> implements WorkOrderRepository {

    private static final Logger LOG = LoggerFactory.getLogger(OrmLiteWorkOrderRepository.class);

    public OrmLiteWorkOrderRepository(RuntimeExceptionDao<WorkOrder, Long> dao) {
        super(dao);
    }

    @Override
    public List<WorkOrder> findAllOrderByStatus() {
        List<WorkOrder> workOrderList = dao.queryForAll();

        Collections.sort(workOrderList, new Comparator<WorkOrder>() {
            @Override
            public int compare(WorkOrder workOrder, WorkOrder workOrder1) {
                return workOrder.getStatus().compareTo(workOrder1.getStatus());
            }
        });

        return workOrderList;
    }

    @Override
    public WorkOrder findByProjectAndLocation(Long projectId, Long locationId) {
        QueryBuilder<WorkOrder, Long> queryBuilder = dao.queryBuilder();

        try {
            PreparedQuery<WorkOrder> query = queryBuilder
                    .where()
                    .eq(PROJECT_ID_FIELD, projectId)
                    .and()
                    .eq(LOCATION_ID_FIELD, locationId)
                    .prepare();

            return dao.queryForFirst(query);
        } catch (SQLException e) {
            LOG.error(SQL_EXCEPTION_MESSAGE, e);
            throw new android.database.SQLException(SQL_EXCEPTION_MESSAGE, e);
        }
    }
}
