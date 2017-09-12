package br.com.bg7.appvistoria.projectselection.vo;

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

    @Override
    public boolean equals(Object obj) {
        return (id.doubleValue() == ((Project) obj).getId().doubleValue());
    }

    public static List<Project> fromProjectResponse(br.com.bg7.appvistoria.data.source.remote.dto.Project projectDto) {

        List<Project> projects = new ArrayList<>();

        for (Element element : projectDto.getElements()) {
            Project project = new Project((long) element.getId(), element.getDescription());
            projects.add(project);
        }

        return projects;
    }
}
