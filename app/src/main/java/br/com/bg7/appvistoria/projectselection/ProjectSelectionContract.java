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

        void showLocations(List<Location> locations);

        void showSelectedLocation(Long projectId, Location location);

        void clearProjectField();

        void clearLocationField();

        void showLoading();

        void hideLoading();

        void showProductSelectionScreen(Project project, Location location);

        void showLoadErrorMessage();
    }

    interface Presenter extends BasePresenter {
        void search(String idOrDescription);

        void selectProject(Project project);

        void selectLocation(Location location);

        void projectFieldClicked();

        void locationFieldClicked();

        void nextClicked();
    }
}
