package br.com.bg7.appvistoria.projectselection.vo;

import com.google.common.base.Objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.bg7.appvistoria.data.source.remote.dto.Element;

/**
 * Created by: elison
 * Date: 2017-08-31
 */
public class Project implements Serializable {
    private Long id;
    private String description;
    private ArrayList<Location> locations = new ArrayList<>();

    public Project(Long id, String description) {
        this.id = id;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void addLocation(Location location) {
        locations.add(location);
    }

    public List<Location> getLocations() {
        return locations;
    }

    public static List<Project> fromProjectResponse(br.com.bg7.appvistoria.data.source.remote.dto.Project projectDto) {

        List<Project> projects = new ArrayList<>();

        for (Element element : projectDto.getElements()) {
            Project project = new Project((long) element.getId(), element.getDescription());
            projects.add(project);
        }

        return projects;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return Objects.equal(id, project.id) &&
                Objects.equal(description, project.description);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, description);
    }
}
