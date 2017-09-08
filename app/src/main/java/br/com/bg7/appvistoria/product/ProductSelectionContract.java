package br.com.bg7.appvistoria.product;

import java.util.List;

import br.com.bg7.appvistoria.BasePresenter;
import br.com.bg7.appvistoria.BaseView;
import br.com.bg7.appvistoria.projectselection.vo.Category;
import br.com.bg7.appvistoria.projectselection.vo.Product;
import br.com.bg7.appvistoria.projectselection.vo.ProductSelection;

/**
 * Created by: elison
 * Date: 2017-09-05
 */
public interface ProductSelectionContract {
    interface View extends BaseView<ProductSelectionContract.Presenter> {
        void showProducts(List<ProductSelection> productSelectionList);
        void showSelectedQuantity(Category category, Product product, int quantity);
    }
    interface Presenter extends BasePresenter {
        void chooseQuantity(Category category, Product product,  int quantity);
    }

}
