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

    }


    interface Presenter extends BasePresenter {

        void start();

        void search(String searchTerm);

        void moreInfoClicked(WorkOrder workOrder);

        void hideInfoClicked(WorkOrder workOrder);

    }
}
