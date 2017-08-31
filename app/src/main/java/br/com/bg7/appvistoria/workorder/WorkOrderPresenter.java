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
        String language = config.getLanguageName();

        List<WorkOrder> workOrderList =  workOrderRepository.findAllOrderByStatus(language);
        workOrderView.showList(workOrderList, workOrderView.isMapAvailable());
    }

    @Override
    public void cancelNewWorkOrderClicked() {
        workOrderView.hideNewWorkOrderConfirmation();
    }

    @Override
    public void confirmNewWorkOrderClicked() {
        workOrderView.showNewWorkOrderScreen();
    }

    @Override
    public void moreInfoClicked(WorkOrder workOrder) {
        workOrderView.highlightInfoButton(workOrder);
        workOrderView.expandInfoPanel(workOrder);
    }

    @Override
    public void newWorkOrderClicked() {
        workOrderView.showNewWorkOrderConfirmation();
    }

    @Override
    public void hideInfoClicked(WorkOrder workOrder) {
        workOrderView.removeInfoButtonHighlight(workOrder);
        workOrderView.shrinkInfoPanel(workOrder);
    }

    @Override
    public void openMapClicked(WorkOrder workOrder) {
        workOrderView.showOpenMapConfirmation(workOrder);
    }

    @Override
    public void confirmOpenMapClicked(WorkOrder workOrder) {
        if (workOrderView.isMapAvailable()) {
            workOrderView.showInMap(workOrder.getAddress());
        }

        workOrderView.hideOpenMapConfirmation();
    }

    @Override
    public void cancelOpenMapClicked() {
        workOrderView.hideOpenMapConfirmation();
    }
}
