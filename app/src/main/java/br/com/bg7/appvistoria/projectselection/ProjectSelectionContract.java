package br.com.bg7.appvistoria.projectselection;

import java.util.List;

import br.com.bg7.appvistoria.BasePresenter;
import br.com.bg7.appvistoria.BaseView;
import br.com.bg7.appvistoria.projectselection.vo.Location;
import br.com.bg7.appvistoria.projectselection.vo.Project;

/**
 * Created by: elison
 * Date: 2017-08-30
 */
public interface ProjectSelectionContract {

    interface View extends BaseView<Presenter> {
        void showProjectResults(List<Project> projectList);

        void showSelectedProject(Project project);

        void showAddresses(List<Location> addresses);

        void showSelectedAddress(Long projectId, Location address);

        void clearProjectField();

        void clearAddressField();

        void showLoading();

        void hideLoading();

        void showProductSelectionScreen(Project project, Location address);

        void showLoadErrorMessage();
    }

    interface Presenter extends BasePresenter {
        void search(String idOrDescription);

        void selectProject(Project project);

        void selectAddress(Location address);

        void projectFieldClicked();

        void addressFieldClicked();

        void nextClicked();
    }
}
