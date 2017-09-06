package br.com.bg7.appvistoria.projectselection;

import java.util.List;

import br.com.bg7.appvistoria.BasePresenter;
import br.com.bg7.appvistoria.BaseView;
import br.com.bg7.appvistoria.projectselection.vo.Project;

/**
 * Created by: elison
 * Date: 2017-08-30
 */
public interface ProjectSelectionContract {

    interface View extends BaseView<Presenter> {
        void showProjectResults(List<Project> projectList);

        void showSelectedProject(Project project, List<String> addresses);

        void showProductSelection(Long projectId, String address);

        void clearProjectField();

        void clearAddressField();

        void showLoading();

        void hideLoading();

        void showProductSelectionScreen();
    }

    interface Presenter extends BasePresenter {
        void search(String idOrDescription);

        void selectProject(Project project);

        void selectAddress(String address);

        void projectFieldClicked();

        void addressFieldClicked();

        void nextClicked();
    }
}
