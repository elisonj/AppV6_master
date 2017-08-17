package br.com.bg7.appvistoria.workorder;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.bg7.appvistoria.R;
import br.com.bg7.appvistoria.data.WorkOrder;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by: elison
 * Date: 2017-08-15
 */
public class WorkOrderFragment extends Fragment implements  WorkOrderContract.View {

    WorkOrderContract.Presenter workOrderPresenter;
    private ListView listView;
    private WorkOrderAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_workorder, container, false);
        initializeViewElements(root);

        return root;
    }

    private void initializeViewElements(View root) {
        listView = root.findViewById(R.id.listview);
    }

    @Override
    public void onResume() {
        super.onResume();
        workOrderPresenter.start();
        workOrderPresenter.search("");
    }

    @Override
    public void setPresenter(WorkOrderContract.Presenter presenter) {
        workOrderPresenter = checkNotNull(presenter);
    }

    @Override
    public void showList(List<WorkOrder> list, boolean showMapButtons) {
        adapter = new WorkOrderAdapter(this, list);
        listView.setAdapter(adapter);
    }

    @Override
    public void highlightInfoButton(WorkOrder workOrder) {
        View v = getItemListView(workOrder);
        LinearLayout moreInfo = v.findViewById(R.id.more_info);
        moreInfo.setBackgroundColor(Color.GRAY);
    }

    @Override
    public void removeInfoButtonHighlight(WorkOrder workOrder) {
        View v = getItemListView(workOrder);
        if(v != null) {
            LinearLayout moreInfo = v.findViewById(R.id.more_info);
            moreInfo.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    @Override
    public void expandInfoPanel(WorkOrder workOrder) {
        showSummary(workOrder);
    }

    @Override
    public void shrinkInfoPanel(WorkOrder workOrder) {
        hideSummary(workOrder);
    }

    private WorkOrderAdapter getAdapter() {
        return (WorkOrderAdapter)listView.getAdapter();
    }

    private void hideSummary(WorkOrder workOrder){
        View v = getItemListView(workOrder);

        if(v != null) {
            TextView shortSummary = v.findViewById(R.id.short_summary);
            TextView summary = v.findViewById(R.id.summary);
            shortSummary.setVisibility(View.VISIBLE);
            summary.setVisibility(View.GONE);
        }
            getAdapter().setHighlightWorkOrder(null);

    }

    private void showSummary(WorkOrder workOrder){
       View v = getItemListView(workOrder);

        TextView shortSummary = v.findViewById(R.id.short_summary);
        TextView summary = v.findViewById(R.id.summary);
        summary.setText(workOrder.getSummary());
        shortSummary.setVisibility(View.GONE);
        summary.setVisibility(View.VISIBLE);
        getAdapter().setHighlightWorkOrder(workOrder);
    }

    private View getItemListView(WorkOrder workOrder) {
        int index = -1;

        for(int i=0; i < adapter.getCount(); i++) {
            if(workOrder.equals(adapter.getItem(i))) {
                index = i;
                break;
            }
        }
        return listView.getChildAt(index -
                listView.getFirstVisiblePosition());
    }

    private class WorkOrderAdapter extends BaseAdapter {

         private WorkOrder highlightWorkOrder = null;

         private LayoutInflater inflater=null;
         private List<WorkOrder> list = new ArrayList<>();

         WorkOrderAdapter(WorkOrderFragment fragment, List<WorkOrder> list) {
             this.list = list;
             this.inflater = fragment.getLayoutInflater();
         }

         void setHighlightWorkOrder(WorkOrder workOrder) {
             this.highlightWorkOrder = workOrder;
         }

        @Override
         public int getCount() {
             return list.size();
         }

         @Override
         public WorkOrder getItem(int index) {
             return list.get(index);
         }

         @Override
         public long getItemId(int index) {
             return index;
         }

         @Override
         public View getView(int position, View convertView, ViewGroup parent) {
             Holder holder = null;

             if (convertView == null) {
                holder = new Holder();

                 convertView = inflater.inflate(R.layout.workorder_item, parent, false);
                 holder.name = convertView.findViewById(R.id.name);
                 holder.shortSummary = convertView.findViewById(R.id.short_summary);
                 holder.summary = convertView.findViewById(R.id.summary);
                 holder.status = convertView.findViewById(R.id.status);
                 holder.inspections = convertView.findViewById(R.id.inspections);
                 holder.moreInfo = convertView.findViewById(R.id.more_info);
                 convertView.setTag(holder);
             }

             if(holder == null) {
                 holder = (Holder) convertView.getTag();
             }

             populateWidget(holder, position);

             return convertView;
         }

         private void populateWidget(final Holder holder, final int position){

             WorkOrder item = getItem(position);
             if(item == null) {
                return;
             }
             holder.summary.setVisibility(View.GONE);
             holder.moreInfo.setBackgroundColor(Color.TRANSPARENT);

             if(item.equals(highlightWorkOrder)) {
                 holder.summary.setText(item.getSummary());
                 holder.summary.setVisibility(View.VISIBLE);
                 holder.moreInfo.setBackgroundColor(Color.GRAY);
             }
             holder.name.setText(item.getName());
             holder.shortSummary.setText(item.getShortSummary());
             holder.status.setText(item.getStatus().toString());

             configureListeners(holder, position);

         }

         void configureListeners(Holder holder, final int position) {
             holder.moreInfo.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {

                     if(highlightWorkOrder != null) {
                         workOrderPresenter.hideInfoClicked(highlightWorkOrder);
                     }

                     workOrderPresenter.moreInfoClicked(list.get(position));
                 }
             });
         }

         class Holder {
             TextView name;
             TextView shortSummary;
             TextView summary;
             TextView status;
             LinearLayout inspections;
             LinearLayout moreInfo;
         }

    }
}
