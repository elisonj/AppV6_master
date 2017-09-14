package br.com.bg7.appvistoria.syncinspection.vo;

/**
 * Created by: elison
 * Date: 2017-09-12
 */
public class SyncListItemDetails {
    private Long id;
    private String description;
    private String project;
    private Integer percentage;

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setPercentage(Integer percentage) {
        this.percentage = percentage;
    }

    public Integer getPercentage() {
        return percentage;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getDescription() {
        return description;
    }

    public String getProject() {
        return project;
    }
}
