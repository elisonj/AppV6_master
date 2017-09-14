package br.com.bg7.appvistoria.sync.vo;

import com.google.common.base.Objects;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by: elison
 * Date: 2017-09-12
 */
public class SyncListItem {
    private SyncListStatus status;
    private Integer count;
    private List<SyncListItemDetails> inspections = new ArrayList<>();

    public SyncListItem(SyncListStatus status) {
        this.status = status;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<SyncListItemDetails> getInspections() {
        return inspections;
    }

    public SyncListStatus getStatus() {
        return status;
    }

    public Integer getCount() {
        return count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SyncListItem that = (SyncListItem) o;
        return status == that.status &&
                Objects.equal(count, that.count) &&
                Objects.equal(inspections, that.inspections);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(status, count, inspections);
    }
}
