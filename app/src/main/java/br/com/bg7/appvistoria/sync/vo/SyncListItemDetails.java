package br.com.bg7.appvistoria.sync.vo;

import com.google.common.base.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SyncListItemDetails that = (SyncListItemDetails) o;
        return Objects.equal(id, that.id) &&
                Objects.equal(description, that.description) &&
                Objects.equal(project, that.project) &&
                Objects.equal(percentage, that.percentage);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, description, project, percentage);
    }
}
