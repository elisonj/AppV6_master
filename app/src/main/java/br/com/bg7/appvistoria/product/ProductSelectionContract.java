package br.com.bg7.appvistoria.product;

import br.com.bg7.appvistoria.BasePresenter;
import br.com.bg7.appvistoria.BaseView;
import br.com.bg7.appvistoria.projectselection.ProjectSelectionContract;

/**
 * Created by: elison
 * Date: 2017-09-05
 */
public interface ProductSelectionContract {
    interface View extends BaseView<ProjectSelectionContract.Presenter> {

    }
    interface Presenter extends BasePresenter {

    }

}
