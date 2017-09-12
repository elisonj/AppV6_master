package br.com.bg7.appvistoria.syncinspection;

import android.content.Context;
import android.graphics.Typeface;
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
import java.util.HashMap;
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
    List<SyncListItem> listDataHeader = new ArrayList<>();
    HashMap<SyncListItem, List<SyncListItemDetails>> listDataChild = new HashMap<>();
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
        listDataHeader = syncList.getSyncListItens();

        expListView.setAdapter(new ExpandableListAdapter(getContext(), listDataHeader));

    }





    private class ExpandableListAdapter extends BaseExpandableListAdapter {

        private Context context;

        ExpandableListAdapter(Context context, List<SyncListItem> inspections ) {
            this.context = context;
        }

        @Override
        public SyncListItemDetails getChild(int groupPosition, int childPosition) {
            List<SyncListItemDetails> productSelectionItems = listDataChild.get(listDataHeader.get(groupPosition));
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
         //   final boolean isSelected = productSelectionPresenter.isProductSelected(childText.getProduct(), getGroup(groupPosition));

            if (convertView == null) {
                convertView = inflate(context, R.layout.sync_list_item, null);
            }

            final LinearLayout linearMain = convertView.findViewById(R.id.linear_main);
            final TextView title = convertView.findViewById(R.id.title);
            final TextView project = convertView.findViewById(R.id.project);

            title.setText(childText.getDescription());


            return convertView;
        }



        @Override
        public int getChildrenCount(int groupPosition) {
            return listDataChild.get(listDataHeader.get(groupPosition))
                    .size();
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
            category.setText(headerTitle.getStatus().toString());

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
