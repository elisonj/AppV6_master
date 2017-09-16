package br.com.bg7.appvistoria.productselection;

import java.util.List;

import br.com.bg7.appvistoria.BasePresenter;
import br.com.bg7.appvistoria.BaseView;
import br.com.bg7.appvistoria.productselection.vo.Category;
import br.com.bg7.appvistoria.productselection.vo.Product;
import br.com.bg7.appvistoria.productselection.vo.ProductSelection;
import br.com.bg7.appvistoria.productselection.vo.ProductSelectionItem;
import br.com.bg7.appvistoria.projectselection.vo.Location;
import br.com.bg7.appvistoria.projectselection.vo.Project;

/**
 * Created by: elison
 * Date: 2017-09-05
 */
public interface ProductSelectionContract {
    interface View extends BaseView<ProductSelectionContract.Presenter> {
        void showProducts(List<ProductSelection> productSelectionList);
        void showSelectedQuantity(ProductSelectionItem item, int quantity);
        void showConnectivityError();
        void showConfirmation();
        void hideConfirmation();
        void showCannotDuplicateWorkorderError();
        void showWorkOrderScreen();
        void showProjectSelection();
    }
    interface Presenter extends BasePresenter {
        void chooseQuantity(ProductSelectionItem item,  int quantity);
        void cancelClicked();
        void createWorkOrderClicked();
        void cancelCreateWorkOrderClicked();
        void confirmCreateWorkOrderClicked();
        void backClicked();
        boolean isItemSelected(ProductSelectionItem product);
    }

}
