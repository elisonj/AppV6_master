package br.com.bg7.appvistoria.productselection;

import java.util.List;

import br.com.bg7.appvistoria.BasePresenter;
import br.com.bg7.appvistoria.BaseView;
import br.com.bg7.appvistoria.productselection.vo.Category;
import br.com.bg7.appvistoria.productselection.vo.Product;
import br.com.bg7.appvistoria.productselection.vo.ProductSelection;
import br.com.bg7.appvistoria.projectselection.vo.Project;

/**
 * Created by: elison
 * Date: 2017-09-05
 */
public interface ProductSelectionContract {
    interface View extends BaseView<ProductSelectionContract.Presenter> {
        void showProducts(List<ProductSelection> productSelectionList);
        void showSelectedQuantity(Category category, Product product, int quantity);
        void showConnectivityError();
        void showConfirmation();
        void hideConfirmation();
        void showCannotDuplicateWorkorderError();
        void showWorkOrderScreen();
        void showProjectSelection(Project project, String address);
    }
    interface Presenter extends BasePresenter {
        void chooseQuantity(Category category, Product product,  int quantity);
        void cancelClicked();
        void createWorkOrderClicked();
        void cancelCreateWorkOrderClicked();
        void confirmCreateWorkOrderClicked();
        void backClicked();
        boolean isProductSelected(Product product, Category category);
    }

}
