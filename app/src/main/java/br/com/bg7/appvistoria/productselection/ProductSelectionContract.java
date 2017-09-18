package br.com.bg7.appvistoria.productselection;

import java.util.List;

import br.com.bg7.appvistoria.BasePresenter;
import br.com.bg7.appvistoria.BaseView;
import br.com.bg7.appvistoria.data.WorkOrderProduct;
import br.com.bg7.appvistoria.productselection.vo.ProductSelectionItem;

/**
 * Created by: elison
 * Date: 2017-09-05
 */
public interface ProductSelectionContract {

    interface View extends BaseView<ProductSelectionContract.Presenter> {
        void showProducts(List<WorkOrderProduct> productSelectionList);

        void showConnectivityError();

        void showButtons();

        void hideButtons();

        void showConfirmation();

        void hideConfirmation();

        void showWorkOrderScreen();

        void showProjectSelection();
    }

    interface Presenter extends BasePresenter {
        void quantitySelected(ProductSelectionItem item, Integer quantity);

        void cancelClicked();

        void createWorkOrderClicked();

        void cancelCreateWorkOrderClicked();

        void confirmCreateWorkOrderClicked();

        void backClicked();
    }
}
