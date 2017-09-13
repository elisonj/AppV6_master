package br.com.bg7.appvistoria.syncinspection;

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
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.bg7.appvistoria.R;
import br.com.bg7.appvistoria.syncinspection.vo.SyncList;
import br.com.bg7.appvistoria.syncinspection.vo.SyncListItem;
import br.com.bg7.appvistoria.syncinspection.vo.SyncListItemDetails;

import static android.view.View.inflate;

/**
 * Created by: elison
 * Date: 2017-09-12
 */
public class SyncFragment extends Fragment implements SyncContract.View {

    private static final String EMPTY_SPACE = " ";
    private SyncContract.Presenter syncPresenter;
    private ExpandableListView expListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_sync, container, false);

        initializeViewElements(root);
        initializeListeners();

        return root;
    }

    private void initializeListeners() {

    }

    private void initializeViewElements(View root) {
        expListView = root.findViewById(R.id.listview);
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
        if(syncList.getSyncListItens().size() == 0) {
            //TODO: Exibir tela de nenhum elemento
            return;
        }
        expListView.setAdapter(new ExpandableListAdapter(getContext(), syncList.getSyncListItens()));
    }

    private class ExpandableListAdapter extends BaseExpandableListAdapter {

        private Context context;
        List<SyncListItem> listDataHeader = new ArrayList<>();

        ExpandableListAdapter(Context context, List<SyncListItem> list ) {
            this.context = context;
            this.listDataHeader = list;
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

            Drawable background = getResources().getDrawable(R.drawable.background_sync_item_in_progress, null);

            switch(getGroup(groupPosition).getStatus()) {
                case COMPLETED:
                    background = getResources().getDrawable(R.drawable.background_sync_item_completed, null);
                    break;
                case NOT_STARTED:
                    background = getResources().getDrawable(R.drawable.background_sync_item_not_started, null);
                    break;
                case ERROR:
                    background = getResources().getDrawable(R.drawable.background_sync_item_error, null);
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
