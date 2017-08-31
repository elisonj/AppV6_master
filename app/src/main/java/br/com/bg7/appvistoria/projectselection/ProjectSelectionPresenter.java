package br.com.bg7.appvistoria.projectselection;

import java.util.List;

import br.com.bg7.appvistoria.data.source.remote.ProjectService;
import br.com.bg7.appvistoria.projectselection.vo.Project;

/**
 * Created by: elison
 * Date: 2017-08-30
 */
class ProjectSelectionPresenter implements  ProjectSelectionContract.Presenter {

    private ProjectService projectService;
    private ProjectSelectionContract.View projectServiceView;

    ProjectSelectionPresenter(ProjectService projectService, ProjectSelectionContract.View view) {
        this.projectService = projectService;
        projectServiceView = view;
        projectServiceView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void search(String idOrDescription) {
        List<Project> projects = projectService.findByIdOrDescription(idOrDescription);
        projectServiceView.showProjectResults(projects);

    }
}
