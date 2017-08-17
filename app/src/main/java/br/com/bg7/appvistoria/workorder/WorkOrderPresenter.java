package br.com.bg7.appvistoria.workorder;

import java.util.List;

import br.com.bg7.appvistoria.auth.Auth;
import br.com.bg7.appvistoria.data.Config;
import br.com.bg7.appvistoria.data.WorkOrder;
import br.com.bg7.appvistoria.data.source.local.ConfigRepository;
import br.com.bg7.appvistoria.data.source.local.WorkOrderRepository;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by: elison
 * Date: 2017-08-15
 */
public class WorkOrderPresenter implements WorkOrderContract.Presenter{

    private final WorkOrderRepository workOrderRepository;
    private final WorkOrderContract.View workOrderView;
    private final ConfigRepository configRepository;

    private String language;

    public WorkOrderPresenter(WorkOrderRepository workOrderRepository, WorkOrderContract.View workOrderView,
                       ConfigRepository configRepository) {
        this.workOrderRepository = checkNotNull(workOrderRepository);
        this.workOrderView = checkNotNull(workOrderView);
        this.configRepository = checkNotNull(configRepository);

        this.workOrderView.setPresenter(this);
    }


    @Override
    public void start() {

        Config config = configRepository.findByUser(Auth.user());
        if(config == null) {
            return;
        }

        language = config.getLanguageName();
    }

    @Override
    public void search(String searchTerm) {
        List<WorkOrder> workOrderList =  workOrderRepository.findAllOrderByStatus(language);
        workOrderView.showList(workOrderList, true);
    }

    @Override
    public void moreInfoClicked(WorkOrder workOrder) {
        workOrderView.highlightInfoButton(workOrder);
        workOrderView.expandInfoPanel(workOrder);
    }

    @Override
    public void hideInfoClicked(WorkOrder workOrder) {
        workOrderView.removeInfoButtonHighlight(workOrder);
        workOrderView.shrinkInfoPanel(workOrder);
    }
}
