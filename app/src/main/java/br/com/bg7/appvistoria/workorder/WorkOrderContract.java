package br.com.bg7.appvistoria.workorder;

import java.util.List;

import br.com.bg7.appvistoria.BasePresenter;
import br.com.bg7.appvistoria.BaseView;
import br.com.bg7.appvistoria.data.WorkOrder;

/**
 * Created by: elison
 * Date: 2017-08-15
 */
interface WorkOrderContract {

    interface View extends BaseView<WorkOrderContract.Presenter> {

        void showList(List<WorkOrder> list, boolean showMapButtons);

        void highlightInfoButton(WorkOrder workOrder);

        void removeInfoButtonHighlight(WorkOrder workOrder);

        void  expandInfoPanel(WorkOrder workOrder);

        void  shrinkInfoPanel(WorkOrder workOrder);

        boolean isMapAvailable();

        void showOpenMapConfirmation(WorkOrder workOrder);

        void hideOpenMapConfirmation();

        void showInMap(String address);

        void showNewWorkOrderConfirmation();

        void hideNewWorkOrderConfirmation();

        void showNewWorkOrderScreen();
    }


    interface Presenter extends BasePresenter {

        void start();

        void moreInfoClicked(WorkOrder workOrder);

        void hideInfoClicked(WorkOrder workOrder);

        void openMapClicked(WorkOrder workOrder);

        void confirmOpenMapClicked(WorkOrder workOrder);

        void cancelOpenMapClicked();

        void newWorkOrderClicked();

        void confirmNewWorkOrderClicked();

        void cancelNewWorkOrderClicked();
    }
}
