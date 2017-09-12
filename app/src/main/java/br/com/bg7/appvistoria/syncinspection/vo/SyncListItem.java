package br.com.bg7.appvistoria.syncinspection.vo;

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
}
