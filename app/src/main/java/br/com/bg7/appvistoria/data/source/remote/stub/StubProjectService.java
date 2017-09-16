package br.com.bg7.appvistoria.data.source.remote.stub;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import br.com.bg7.appvistoria.data.source.remote.ProjectService;
import br.com.bg7.appvistoria.data.source.remote.http.HttpCallback;
import br.com.bg7.appvistoria.data.source.remote.http.HttpResponse;
import br.com.bg7.appvistoria.projectselection.vo.Location;
import br.com.bg7.appvistoria.projectselection.vo.Project;

/**
 * Created by: elison
 * Date: 2017-08-31
 */
public class StubProjectService implements ProjectService {

    private static Project PROJECT_1 = new Project(1L, "Projeto 1") {{
        addLocation(new Location(11L, "Endereço 1 do projeto 1"));
        addLocation(new Location(12L, "Endereço 2 do projeto 1"));
        addLocation(new Location(13L, "Endereço 3 do projeto 1"));
    }};

    private static Project PROJECT_2 = new Project(2L, "Projeto 2") {{
        addLocation(new Location(21L, "Endereço 1 do projeto 2"));
        addLocation(new Location(22L, "Endereço 2 do projeto 2"));
        addLocation(new Location(23L, "Endereço 3 do projeto 2"));
    }};

    private static Project PROJECT_3 = new Project(3L, "Projeto 3") {{
        addLocation(new Location(31L, "Endereço 1 do projeto 3"));
        addLocation(new Location(32L, "Endereço 2 do projeto 3"));
        addLocation(new Location(33L, "Endereço 3 do projeto 3"));
    }};

    private static Project PROJECT_4 = new Project(4L, "Projeto 4") {{
        addLocation(new Location(41L, "Endereço 1 do projeto 4"));
        addLocation(new Location(42L, "Endereço 2 do projeto 4"));
        addLocation(new Location(43L, "Endereço 3 do projeto 4"));
    }};

    private static Project PROJECT_5 = new Project(5L, "Projeto 5") {{
        addLocation(new Location(51L, "Endereço 1 do projeto 5"));
        addLocation(new Location(52L, "Endereço 2 do projeto 5"));
        addLocation(new Location(53L, "Endereço 3 do projeto 5"));
    }};

    private List<Project> projects = new ArrayList<Project>() {{
        add(PROJECT_1);
        add(PROJECT_2);
        add(PROJECT_3);
        add(PROJECT_4);
        add(PROJECT_5);
    }};

    @Override
    public void findByIdOrDescription(String idOrDescription, HttpCallback<List<Project>> callback) {

        callback.onResponse(new HttpResponse<List<Project>>() {
            @Override
            public boolean isSuccessful() {
                return true;
            }

            @Nullable
            @Override
            public List<Project> body() {
                return projects;
            }

            @Override
            public int code() {
                return 200;
            }
        });
    }

    @Override
    public void findLocationsForProject(final Project project, HttpCallback<List<Location>> callback) {

        callback.onResponse(new HttpResponse<List<Location>>() {
            @Override
            public boolean isSuccessful() {
                return true;
            }

            @Nullable
            @Override
            public List<Location> body() {
                return project.getLocations();
            }

            @Override
            public int code() {
                return 200;
            }
        });
    }
}
