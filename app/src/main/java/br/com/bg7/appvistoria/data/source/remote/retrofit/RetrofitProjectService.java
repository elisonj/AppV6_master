package br.com.bg7.appvistoria.data.source.remote.retrofit;

import java.util.List;

import br.com.bg7.appvistoria.data.source.remote.http.HttpCallback;
import br.com.bg7.appvistoria.data.source.remote.http.HttpResponse;
import br.com.bg7.appvistoria.data.source.remote.retrofit.http.RetrofitHttpCall;
import br.com.bg7.appvistoria.data.source.remote.retrofit.http.RetrofitProjectHttpResponse;
import br.com.bg7.appvistoria.projectselection.vo.Location;
import br.com.bg7.appvistoria.projectselection.vo.Project;
import retrofit2.Call;

/**
 * Created by: elison
 * Date: 2017-09-04
 */
public class RetrofitProjectService extends RetrofitLoggedInService<ProjectService> implements br.com.bg7.appvistoria.data.source.remote.ProjectService {

    private final static String SEARCH_KEY = "searchKey:";

    private final ProjectService projectService;

    public RetrofitProjectService(String baseUrl) {
        super(baseUrl);

        this.projectService = buildService(ProjectService.class);
    }

    @Override
    public void findByIdOrDescription(String idOrDescription, final HttpCallback<List<Project>> callback) {
        Call<br.com.bg7.appvistoria.data.source.remote.dto.Project> call = projectService.findByIdOrDescription(TOKEN, SEARCH_KEY + idOrDescription);
        RetrofitHttpCall<br.com.bg7.appvistoria.data.source.remote.dto.Project> httpCall = new RetrofitHttpCall<>(call);

        httpCall.enqueue(new HttpCallback<br.com.bg7.appvistoria.data.source.remote.dto.Project>() {
            @Override
            public void onResponse(HttpResponse<br.com.bg7.appvistoria.data.source.remote.dto.Project> httpResponse) {
                if (httpResponse == null || !httpResponse.isSuccessful() || httpResponse.body() == null) {
                    callback.onFailure(new BadResponseException(httpResponse));
                    return;
                }

                List<Project> projects = Project.fromProjectResponse(httpResponse.body());
                callback.onResponse(new RetrofitProjectHttpResponse(projects, httpResponse.isSuccessful(), httpResponse.code()));
            }

            @Override
            public void onFailure(Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    @Override
    public void findAddressesForProject(Project project, HttpCallback<List<Location>> callback) {
        // TODO: Implementar chamada que retorna todos os enderecos relacionados a um projeto, quando a SB implementar
    }
}
