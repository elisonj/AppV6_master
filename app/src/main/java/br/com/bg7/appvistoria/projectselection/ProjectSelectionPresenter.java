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
        if (Strings.isNullOrEmpty(idOrDescription) || Strings.isNullOrEmpty(idOrDescription.trim())) {
            return;
        }
        projectServiceView.showLoading();
        ProjectSelectionCallback callback = new ProjectSelectionCallback();
        projectService.findByIdOrDescription(idOrDescription, callback);
    }

    @Override
    public void selectProject(Project project) {
        this.project = project;
        projectServiceView.showLoading();
        AddressSelectionCallback callback = new AddressSelectionCallback();
        projectService.findAddressesForProject(project, callback);

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

    @Override
    public void nextClicked() {
        projectServiceView.showProductSelectionScreen();
    }

    private class ProjectSelectionCallback implements HttpCallback<List<Project>>

    {
        @Override
        public void onResponse(HttpResponse<List<Project>> httpResponse) {
            if(httpResponse.isSuccessful() && httpResponse.body() != null) {
                List<Project> projects = httpResponse.body();
                projectServiceView.showProjectResults(projects);
            }
            projectServiceView.hideLoading();
        }

        @Override
        public void onFailure(Throwable t) {
            projectServiceView.hideLoading();
            t.printStackTrace();
        }
    }

    private class AddressSelectionCallback implements HttpCallback<List<String>>

    {
        @Override
        public void onResponse(HttpResponse<List<String>> httpResponse) {
            if(httpResponse.isSuccessful() && httpResponse.body() != null) {
                List<String> address = httpResponse.body();
                projectServiceView.showSelectedProject(project, address);
            }
            projectServiceView.hideLoading();
        }

        @Override
        public void onFailure(Throwable t) {
            projectServiceView.hideLoading();
        }
    }

}
