package br.com.bg7.appvistoria.projectselection;

import java.util.List;

import br.com.bg7.appvistoria.data.source.remote.ProjectService;
import br.com.bg7.appvistoria.projectselection.vo.Project;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by: elison
 * Date: 2017-08-30
 */
class ProjectSelectionPresenter implements  ProjectSelectionContract.Presenter {

    private ProjectService projectService;
    private ProjectSelectionContract.View projectServiceView;
    private Project project = null;

    ProjectSelectionPresenter(ProjectService projectService, ProjectSelectionContract.View view) {
        this.projectService = checkNotNull(projectService);
        projectServiceView = checkNotNull(view);
        projectServiceView.setPresenter(this);
    }

    @Override
    public void start() {
    }

    @Override
    public void search(String idOrDescription) {
        projectServiceView.showLoading();
        List<Project> projects = projectService.findByIdOrDescription(idOrDescription);
        projectServiceView.hideLoading();
        projectServiceView.showProjectResults(projects);

    }

    @Override
    public void selectProject(Project project) {
        this.project = project;
        projectServiceView.showLoading();
        List<String> addressesForProject = projectService.findAddressesForProject(project);
        projectServiceView.hideLoading();
        projectServiceView.showSelectedProject(project, addressesForProject);
    }

    @Override
    public void selectAddress(String address) {
        projectServiceView.showProductSelection(project.getId(), address);
    }

    @Override
    public void addressFieldClicked() {
        projectServiceView.clearAddressField();
    }

    @Override
    public void projectFieldClicked() {
        projectServiceView.clearProjectField();
    }
}
