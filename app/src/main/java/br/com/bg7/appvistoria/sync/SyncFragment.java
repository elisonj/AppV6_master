package br.com.bg7.appvistoria.sync;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.bg7.appvistoria.R;
import br.com.bg7.appvistoria.sync.vo.SyncList;
import br.com.bg7.appvistoria.sync.vo.SyncListItem;
import br.com.bg7.appvistoria.sync.vo.SyncListItemDetails;

import static android.view.View.inflate;

/**
 * Created by: elison
 * Date: 2017-09-12
 */
public class SyncFragment extends Fragment implements SyncContract.View {

    private static final String PERCENT = "%";
    private static final String EMPTY_SPACE = " ";
    private SyncContract.Presenter syncPresenter;
    private ExpandableListView expListView;
    private LinearLayout linearEmpty;

    private static final int POSITION_NOT_STARTED = 0;
    private static final int POSITION_IN_PROGRESS = 1;
    private static final int POSITION_COMPLETED = 2;
    private static final int POSITION_ERROR = 3;

    private static final int NOTIFICATION_TIME = 2500;

    private View linearTryAgain;
    private View linearNotification;
    private View linearSuccess;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_sync, container, false);

        initializeViewElements(root);
        initializeListeners();

        return root;
    }

    private void initializeListeners() {
        linearTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExpandableListAdapter expandableListAdapter = getExpandableListAdapter();
                List<SyncListItemDetails> inspections = expandableListAdapter.getGroup(POSITION_ERROR).getInspections();

                for(SyncListItemDetails inspection: inspections) {
                    syncPresenter.retryClicked(inspection.getId());
                }

                linearNotification.setVisibility(View.GONE);
                linearTryAgain.setVisibility(View.GONE);
            }
        });
    }

    private void initializeViewElements(View root) {
        expListView = root.findViewById(R.id.listview);
        linearEmpty = root.findViewById(R.id.linear_empty);
        linearTryAgain = root.findViewById(R.id.linear_try_again);
        linearNotification = root.findViewById(R.id.linear_notification);
        linearSuccess = root.findViewById(R.id.linear_success);
    }

    @Override
    public void onResume() {
        super.onResume();
        syncPresenter.start();
    }

    @Override
    public void setPresenter(SyncContract.Presenter presenter) {
        syncPresenter = presenter;
    }

    @Override
    public void showInspections(SyncList syncList) {
        if(syncList  == null) {
            linearEmpty.setVisibility(View.VISIBLE);
            return;
        }
        linearEmpty.setVisibility(View.GONE);
        expListView.setVisibility(View.VISIBLE);
        expListView.setAdapter(new ExpandableListAdapter(getContext(), syncList.getSyncListItens()));
    }

    @Override
    public void showUnderNotStarted(Long inspectionId) {
        ExpandableListAdapter expandableListAdapter = getExpandableListAdapter();

        SyncListItemDetails item = expandableListAdapter.getSyncListItemDetailsById(inspectionId);

        List<SyncListItem> listDataHeader = expandableListAdapter.getListDataHeader();
        listDataHeader.get(POSITION_NOT_STARTED).getInspections().add(item);
        listDataHeader.get(POSITION_NOT_STARTED).setCount(listDataHeader.get(POSITION_NOT_STARTED).getCount() + 1);

        expListView.setAdapter(new ExpandableListAdapter(getContext(), listDataHeader));
    }

    @Override
    public void showUnderInProgress(Long inspectionId) {
        ExpandableListAdapter expandableListAdapter = getExpandableListAdapter();

        SyncListItemDetails item = expandableListAdapter.getSyncListItemDetailsById(inspectionId);

        List<SyncListItem> listDataHeader = expandableListAdapter.getListDataHeader();
        listDataHeader.get(POSITION_IN_PROGRESS).getInspections().add(item);
        listDataHeader.get(POSITION_IN_PROGRESS).setCount(listDataHeader.get(POSITION_IN_PROGRESS).getCount() + 1);

        expListView.setAdapter(new ExpandableListAdapter(getContext(), listDataHeader));
    }

    @Override
    public void showUnderCompleted(Long inspectionId) {
        ExpandableListAdapter expandableListAdapter = getExpandableListAdapter();

        SyncListItemDetails item = expandableListAdapter.getSyncListItemDetailsById(inspectionId);


        List<SyncListItem> listDataHeader = expandableListAdapter.getListDataHeader();
        listDataHeader.get(POSITION_COMPLETED).getInspections().add(item);
        listDataHeader.get(POSITION_COMPLETED).setCount(listDataHeader.get(POSITION_COMPLETED).getCount() + 1);

        expListView.setAdapter(new ExpandableListAdapter(getContext(), listDataHeader));
    }

    @Override
    public void showPercentage(Integer percentage, Long inspectionId) {
        ExpandableListAdapter expandableListAdapter = getExpandableListAdapter();

        SyncListItemDetails item = expandableListAdapter.getSyncListItemDetailsById(inspectionId);
        item.setPercentage(percentage);

        List<SyncListItem> listDataHeader = expandableListAdapter.getListDataHeader();
        listDataHeader.get(POSITION_IN_PROGRESS).getInspections().add(item);
        listDataHeader.get(POSITION_IN_PROGRESS).setCount(listDataHeader.get(POSITION_IN_PROGRESS).getCount() + 1);

        expListView.setAdapter(new ExpandableListAdapter(getContext(), listDataHeader));
    }

    @Override
    public void showUnderError(Long inspectionId) {
        ExpandableListAdapter expandableListAdapter = getExpandableListAdapter();

        SyncListItemDetails item = expandableListAdapter.getSyncListItemDetailsById(inspectionId);

        List<SyncListItem> listDataHeader = expandableListAdapter.getListDataHeader();
        listDataHeader.get(POSITION_ERROR).getInspections().add(item);
        listDataHeader.get(POSITION_ERROR).setCount(listDataHeader.get(POSITION_ERROR).getCount() + 1);

        expListView.setAdapter(new ExpandableListAdapter(getContext(), listDataHeader));
    }

    @Override
    public void showSyncSuccessMessage() {
        linearNotification.setVisibility(View.VISIBLE);
        linearSuccess.setVisibility(View.VISIBLE);
        linearSuccess.postDelayed(new Runnable() {
            public void run() {
                linearNotification.setVisibility(View.GONE);
                linearSuccess.setVisibility(View.GONE);
            }
        }, NOTIFICATION_TIME);
    }

    @Override
    public void showSyncErrorMessage() {
        linearNotification.setVisibility(View.VISIBLE);
        linearTryAgain.setVisibility(View.VISIBLE);

        linearNotification.postDelayed(new Runnable() {
            public void run() {
                linearNotification.setVisibility(View.GONE);
                linearTryAgain.setVisibility(View.GONE);
            }
        }, NOTIFICATION_TIME);
    }

    private ExpandableListAdapter getExpandableListAdapter() {
        return (ExpandableListAdapter)expListView.getExpandableListAdapter();
    }


    private class ExpandableListAdapter extends BaseExpandableListAdapter {

        private Context context;
        List<SyncListItem> listDataHeader = new ArrayList<>();

        ExpandableListAdapter(Context context, List<SyncListItem> list ) {
            this.context = context;
            this.listDataHeader = list;
        }

        public List<SyncListItem> getListDataHeader() {
            return listDataHeader;
        }

        SyncListItemDetails getSyncListItemDetailsById(Long id) {
            SyncListItemDetails detail = null;

            for(int cont = 0; cont < getGroupCount(); cont++) {
                detail =  getDetailItemByHeader(cont, id);

                if(detail != null) break;

            }
            return detail;
        }

        private SyncListItemDetails getDetailItemByHeader(int groupPosition, Long id) {

            for(int pos = 0; pos < getChildrenCount(groupPosition); pos++) {
                if(getChild(groupPosition, pos).getId().equals(id)) {

                    SyncListItemDetails item = getChild(groupPosition, pos);
                    listDataHeader.get(groupPosition).getInspections().remove(pos);
                    listDataHeader.get(groupPosition).setCount(listDataHeader.get(groupPosition).getCount() - 1);
                    notifyDataSetInvalidated();
                    notifyDataSetChanged();
                    return item;
                }
            }
            return null;
        }

        @Override
        public SyncListItemDetails getChild(int groupPosition, int childPosition) {
            List<SyncListItemDetails> productSelectionItems = listDataHeader.get(groupPosition).getInspections();
            return productSelectionItems.get(childPosition);
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public View getChildView(final int groupPosition, final int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {

            final SyncListItemDetails childText = getChild(groupPosition, childPosition);

            if (convertView == null) {
                convertView = inflate(context, R.layout.sync_list_item, null);
            }

            final LinearLayout linearMain = convertView.findViewById(R.id.linear_main);
            final TextView title = convertView.findViewById(R.id.title);
            final TextView project = convertView.findViewById(R.id.project);
            final TextView status = convertView.findViewById(R.id.status);
            final ImageView action = convertView.findViewById(R.id.icon_item);
            final LinearLayout linearProperties = convertView.findViewById(R.id.linear_properties);
            linearProperties.setVisibility(View.VISIBLE);

            status.setText(getString(R.string.sync_item));

            Drawable background = getResources().getDrawable(R.drawable.background_sync_item_in_progress, null);
            Drawable actionIcon = getResources().getDrawable(R.drawable.ic_sync_item_blue, null);
            action.setVisibility(View.VISIBLE);
            action.setImageDrawable(actionIcon);
            action.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    syncPresenter.syncClicked(childText.getId());
                }
            });

            if(childText.getPercentage() != null && childText.getPercentage() > 0) {
                String percent = childText.getPercentage()+PERCENT;
                status.setText(percent);
            }

            switch(getGroup(groupPosition).getStatus()) {
                case COMPLETED:
                    background = getResources().getDrawable(R.drawable.background_sync_item_completed, null);
                    linearProperties.setVisibility(View.GONE);
                    action.setVisibility(View.GONE);
                    break;
                case NOT_STARTED:
                    actionIcon = getResources().getDrawable(R.drawable.ic_sync_item, null);
                    action.setImageDrawable(actionIcon);
                    background = getResources().getDrawable(R.drawable.background_sync_item_not_started, null);
                    action.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            syncPresenter.syncClicked(childText.getId());
                        }
                    });
                    break;
                case ERROR:
                    background = getResources().getDrawable(R.drawable.background_sync_item_error, null);
                    actionIcon = getResources().getDrawable(R.drawable.ic_sync_item_red, null);
                    action.setImageDrawable(actionIcon);
                    action.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            syncPresenter.retryClicked(childText.getId());
                        }
                    });

                    break;
            }

            linearMain.setBackground(background);

            title.setText(childText.getDescription());
            project.setText(childText.getProject());

            return convertView;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return listDataHeader.get(groupPosition).getInspections().size();
        }

        @Override
        public SyncListItem getGroup(int groupPosition) {
            return listDataHeader.get(groupPosition);
        }

        @Override
        public int getGroupCount() {
            return listDataHeader.size();
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {
            SyncListItem headerTitle =  getGroup(groupPosition);
            if (convertView == null) {
                convertView = inflate(context, R.layout.sync_list_category, null);
            }

            TextView category = convertView.findViewById(R.id.category);

            category.setTypeface(null, Typeface.BOLD);

            String categoryName = getString(R.string.inspection_being_sync);

            switch(headerTitle.getStatus()) {
                case COMPLETED:
                    categoryName = getString(R.string.inspection_synced);
                    break;
                case NOT_STARTED:
                    categoryName = getString(R.string.inspection_to_sync);
                    break;
                case ERROR:
                    categoryName = getString(R.string.inspection_error);
            }

            categoryName = String.format(categoryName, headerTitle.getCount());
            category.setText(categoryName);


            return convertView;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }



}
