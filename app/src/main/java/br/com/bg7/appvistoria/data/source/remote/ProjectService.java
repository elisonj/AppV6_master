package br.com.bg7.appvistoria.data.source.remote;

import java.util.List;

import br.com.bg7.appvistoria.data.source.remote.http.HttpCallback;
import br.com.bg7.appvistoria.projectselection.vo.Location;
import br.com.bg7.appvistoria.projectselection.vo.Project;

/**
 * Created by: elison
 * Date: 2017-08-31
 */
public interface ProjectService {
    void findByIdOrDescription(String idOrDescription, HttpCallback<List<Project>> callback);
    void findLocationsForProject(Project project, HttpCallback<List<Location>> callback);
}
