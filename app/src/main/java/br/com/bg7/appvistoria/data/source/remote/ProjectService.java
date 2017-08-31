package br.com.bg7.appvistoria.data.source.remote;

import java.util.List;

import br.com.bg7.appvistoria.projectselection.vo.Project;

/**
 * Created by: elison
 * Date: 2017-08-31
 */
public interface ProjectService {
    List<Project> findByIdOrDescription(String idOrDescription);
    List<String> findAddressesForProject(Project project);
}
