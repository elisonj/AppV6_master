package br.com.bg7.appvistoria.sync.vo;

import com.google.common.base.Objects;

import java.util.ArrayList;
import java.util.List;

import br.com.bg7.appvistoria.data.Inspection;

/**
 * Created by: elison
 * Date: 2017-09-12
 */
public class SyncList {

    private List<SyncListItem> syncListItem = new ArrayList<>();

    public static SyncList fromInspections(List<Inspection> list) {

        if(list == null || list.size() == 0) return null;

        SyncList syncList = new SyncList();

        int countNotStarted = 0;
        int countInProgress = 0;
        int countError = 0;
        int countCompleted = 0;

        SyncListItem listItemNotStarted = new SyncListItem(SyncListStatus.NOT_STARTED);
        SyncListItem listItemInProgress = new SyncListItem(SyncListStatus.IN_PROGRESS);
        SyncListItem listItemError = new SyncListItem(SyncListStatus.ERROR);
        SyncListItem listItemCompleted = new SyncListItem(SyncListStatus.COMPLETED);

        for(Inspection inspection: list) {

            SyncListItemDetails detail = new SyncListItemDetails();
            detail.setId(inspection.getId());
            detail.setProject(inspection.getProjectDescription());
            detail.setDescription(inspection.getDescription());

            if(inspection.getSyncStatus() == null) {
                listItemNotStarted.getInspections().add(detail);
                continue;
            }

            switch (inspection.getSyncStatus()) {
                case READY:
                    listItemNotStarted.getInspections().add(detail);
                    break;
                case INSPECTION_BEING_SYNCED:
                    detail.setPercentage(inspection.getPercentageCompleted());
                    listItemInProgress.getInspections().add(detail);
                    break;
                case FAILED:
                    listItemError.getInspections().add(detail);
                    break;
                case DONE:
                    listItemCompleted.getInspections().add(detail);
                    break;
            }
        }

        listItemNotStarted.setCount(listItemNotStarted.getInspections().size());
        listItemInProgress.setCount(listItemInProgress.getInspections().size());
        listItemError.setCount(listItemError.getInspections().size());
        listItemCompleted.setCount(listItemCompleted.getInspections().size());

        syncList.syncListItem.add(listItemNotStarted);
        syncList.syncListItem.add(listItemInProgress);
        syncList.syncListItem.add(listItemCompleted);
        syncList.syncListItem.add(listItemError);

        return syncList;
    }

    public List<SyncListItem> getSyncListItens() {
        return syncListItem;
    }

    public void setSyncListItem(List<SyncListItem> syncListItem) {
        this.syncListItem = syncListItem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SyncList syncList = (SyncList) o;
        return Objects.equal(syncListItem, syncList.syncListItem);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(syncListItem);
    }
}
