package br.com.bg7.appvistoria.data.source.remote.stub;

import com.google.common.collect.HashMultimap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import br.com.bg7.appvistoria.data.source.remote.ProjectService;
import br.com.bg7.appvistoria.projectselection.vo.Category;
import br.com.bg7.appvistoria.projectselection.vo.Product;
import br.com.bg7.appvistoria.projectselection.vo.Project;

/**
 * Created by: elison
 * Date: 2017-08-31
 */
public class StubProjectService implements ProjectService {

    private Project project1 = new Project(1L, "Projeto 1");
    private Project project2 = new Project(2L, "Projeto 2");
    private Project project3 = new Project(3L, "Projeto 3");
    private Project project4 = new Project(4L, "Projeto 4");
    private Project project5 = new Project(5L, "Projeto 5");

    @Override
    public List<Project> findByIdOrDescription(String idOrDescription) {

        List<Project> listReturn = new ArrayList<>();
        listReturn.add(project1);
        listReturn.add(project2);
        listReturn.add(project3);
        listReturn.add(project4);
        listReturn.add(project5);

        return listReturn;
    }

    /**
     * Return addresses for the fixed projects
     *
     * SuppressWarnings because we don't care about the increasing
     * size of the multi-map
     *
     * @param project the project to search for
     * @return fixed list of addresses
     */
    @Override
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public List<String> findAddressesForProject(Project project) {
        List<String> list = new ArrayList<>();

        HashMultimap<Project, String> address = HashMultimap.create();
        address.put(project1, "Endereço 1 do projeto 1");
        address.put(project1, "Endereço 2 do projeto 1");
        address.put(project1, "Endereço 3 do projeto 1");
        address.put(project2, "Endereço 1 do projeto 2");
        address.put(project2, "Endereço 2 do projeto 2");
        address.put(project2, "Endereço 3 do projeto 2");
        address.put(project3, "Endereço 1 do projeto 3");
        address.put(project3, "Endereço 2 do projeto 3");
        address.put(project3, "Endereço 3 do projeto 3");
        address.put(project4, "Endereço 1 do projeto 4");
        address.put(project4, "Endereço 2 do projeto 4");
        address.put(project4, "Endereço 3 do projeto 4");
        address.put(project5, "Endereço 1 do projeto 5");
        address.put(project5, "Endereço 2 do projeto 5");
        address.put(project5, "Endereço 3 do projeto 5");

        for(Map.Entry<Project, String> entry: address.entries()) {
            if(entry.getKey().equals(project)) {
                list.add(entry.getValue());
            }
        }

        return list;
    }
}
