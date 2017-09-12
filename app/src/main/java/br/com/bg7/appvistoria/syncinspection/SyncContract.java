package br.com.bg7.appvistoria.syncinspection;

import br.com.bg7.appvistoria.BasePresenter;
import br.com.bg7.appvistoria.BaseView;
import br.com.bg7.appvistoria.syncinspection.vo.SyncList;

/**
 * Created by: elison
 * Date: 2017-09-12
 */
public interface SyncContract {
    interface View extends BaseView<Presenter> {
        void showInspections(SyncList syncList);

    }
    interface Presenter extends BasePresenter {
//        void syncClicked(Long inspectionId);
//        void retryClicked(Long inspectionId);
    }
}
