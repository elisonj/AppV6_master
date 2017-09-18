package br.com.bg7.appvistoria.data.source.remote.retrofit.http;

import java.util.List;

import javax.annotation.Nullable;

import br.com.bg7.appvistoria.data.source.remote.http.HttpResponse;
import br.com.bg7.appvistoria.projectselection.vo.Project;

/**
 * Created by: elison
 * Date: 2017-09-04
 */
public class RetrofitProjectHttpResponse implements HttpResponse<List<Project>> {

    private List<Project> project;
    private boolean isSuccessful;
    private int code;

    public RetrofitProjectHttpResponse(List<Project> project, boolean isSuccessful, int code) {
        this.project = project;
        this.isSuccessful = isSuccessful;
        this.code = code;
    }

    @Override
    public boolean isSuccessful() {
        return isSuccessful;
    }

    @Nullable
    @Override
    public List<Project> body() {
        return project;
    }

    @Override
    public int code() {
        return code;
    }
}
