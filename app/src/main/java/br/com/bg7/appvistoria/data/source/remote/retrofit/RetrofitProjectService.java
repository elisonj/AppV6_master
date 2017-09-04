package br.com.bg7.appvistoria.data.source.remote.retrofit;

import java.util.List;

import br.com.bg7.appvistoria.auth.Auth;
import br.com.bg7.appvistoria.data.source.remote.http.HttpCallback;
import br.com.bg7.appvistoria.data.source.remote.http.HttpResponse;
import br.com.bg7.appvistoria.data.source.remote.retrofit.http.RetrofitHttpCall;
import br.com.bg7.appvistoria.data.source.remote.retrofit.http.RetrofitProjectHttpResponse;
import br.com.bg7.appvistoria.projectselection.vo.Project;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by: elison
 * Date: 2017-09-04
 */
public class RetrofitProjectService implements br.com.bg7.appvistoria.data.source.remote.ProjectService {

    private final static String SEARCH_KEY = "searchKey:";
    private final static String KEY_TOKEN = "Bearer ";

    private final ProjectService projectService;

    public RetrofitProjectService(String baseUrl) {

        this.projectService = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ProjectService.class);
    }

    @Override
    public void findByIdOrDescription(String idOrDescription, final HttpCallback<List<Project>> callback) {
        String token = KEY_TOKEN+Auth.token();
        Call<br.com.bg7.appvistoria.data.source.remote.dto.Project>  call = projectService.findByIdOrDescription(token, SEARCH_KEY+idOrDescription);
        RetrofitHttpCall<br.com.bg7.appvistoria.data.source.remote.dto.Project> httpCall = new RetrofitHttpCall<>(call);

        httpCall.enqueue(new HttpCallback<br.com.bg7.appvistoria.data.source.remote.dto.Project>() {
            @Override
            public void onResponse(HttpResponse<br.com.bg7.appvistoria.data.source.remote.dto.Project> httpResponse) {
                br.com.bg7.appvistoria.data.source.remote.dto.Project response = httpResponse.body();
                List<Project> projects = br.com.bg7.appvistoria.data.source.remote.dto.Project.fromProjectResponse(response);
                callback.onResponse(new RetrofitProjectHttpResponse(projects, httpResponse.isSuccessful(), httpResponse.code()));

            }

            @Override
            public void onFailure(Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    @Override
    public void findAddressesForProject(Project project, HttpCallback<List<String>> callback) {
    }
}
