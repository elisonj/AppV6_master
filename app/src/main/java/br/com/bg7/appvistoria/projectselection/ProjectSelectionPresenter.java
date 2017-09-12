package br.com.bg7.appvistoria.projectselection;

import com.google.common.base.Strings;

import java.util.List;

import br.com.bg7.appvistoria.data.source.remote.ProjectService;
import br.com.bg7.appvistoria.data.source.remote.http.HttpCallback;
import br.com.bg7.appvistoria.data.source.remote.http.HttpResponse;
import br.com.bg7.appvistoria.projectselection.vo.Project;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by: elison
 * Date: 2017-08-30
 */
class ProjectSelectionPresenter implements  ProjectSelectionContract.Presenter {

    private ProjectService projectService;
    private ProjectSelectionContract.View projectServiceView;
    private Project project;
    private String address;

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
        if (Strings.isNullOrEmpty(idOrDescription) || Strings.isNullOrEmpty(idOrDescription.trim())) {
            return;
        }

        projectServiceView.showLoading();
        projectService.findByIdOrDescription(idOrDescription, new FindProjectsCallback());
    }

    @Override
    public void selectProject(Project project) {
        this.project = project;

        projectServiceView.showSelectedProject(project);
        projectServiceView.showLoading();
        projectService.findAddressesForProject(project, new FindAddressesCallback());
    }

    @Override
    public void selectAddress(String address) {
        this.address = address;

        projectServiceView.showSelectedAddress(project.getId(), address);
    }

    @Override
    public void addressFieldClicked() {
        this.address = null;
        projectServiceView.clearAddressField();
    }

    @Override
    public void projectFieldClicked()
    {
        this.address = null;
        this.project = null;
        projectServiceView.clearProjectField();
    }

    @Override
    public void nextClicked() {
        if (project != null && address != null) {
            projectServiceView.showProductSelectionScreen(project, address);
        }
    }

    private class FindProjectsCallback implements HttpCallback<List<Project>>
    {
        @Override
        public void onResponse(HttpResponse<List<Project>> httpResponse) {
            List<Project> projects = httpResponse.body();
            projectServiceView.showProjectResults(projects);
            projectServiceView.hideLoading();
        }

        @Override
        public void onFailure(Throwable t) {
            projectServiceView.hideLoading();
            projectServiceView.showLoadErrorMessage();
        }
    }

    private class FindAddressesCallback implements HttpCallback<List<String>>
    {
        @Override
        public void onResponse(HttpResponse<List<String>> httpResponse) {
            List<String> addresses = httpResponse.body();
            projectServiceView.showAddresses(addresses);
            projectServiceView.hideLoading();
        }

        @Override
        public void onFailure(Throwable t) {
            projectServiceView.hideLoading();
            projectServiceView.showLoadErrorMessage();
        }
    }
}
