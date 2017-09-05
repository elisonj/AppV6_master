package br.com.bg7.appvistoria.data.source.remote.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by: elison
 * Date: 2017-09-04
 */
public class Project {
    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("start")
    @Expose
    private Integer start;
    @SerializedName("limit")
    @Expose
    private Integer limit;
    @SerializedName("elements")
    @Expose
    private List<Element> elements = null;

    public List<Element> getElements() {
        return elements;
    }

    public static List<br.com.bg7.appvistoria.projectselection.vo.Project> fromProjectResponse(Project projectDto) {

        List<br.com.bg7.appvistoria.projectselection.vo.Project> listProjects = new ArrayList<>();
        if(projectDto == null) return listProjects;

        for (Element element: projectDto.getElements()) {
            br.com.bg7.appvistoria.projectselection.vo.Project project = new br.com.bg7.appvistoria.projectselection.vo.Project((long)element.getId(), element.getDescription());
            listProjects.add(project);
        }
        return listProjects;
    }
}
