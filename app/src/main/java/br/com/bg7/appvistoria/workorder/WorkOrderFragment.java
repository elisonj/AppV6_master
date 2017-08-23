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

import com.akexorcist.localizationactivity.LocalizationActivity;

import java.util.ArrayList;
import java.util.List;

import br.com.bg7.appvistoria.BuildConfig;
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

        listView = root.findViewById(R.id.listview);

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        workOrderPresenter.start();
    }

    @Override
    public void setPresenter(WorkOrderContract.Presenter presenter) {
        workOrderPresenter = checkNotNull(presenter);
    }

    @Override
    public void showList(List<WorkOrder> list, boolean showMapButtons) {
        adapter = new WorkOrderAdapter(list);
        listView.setAdapter(adapter);
    }

    @Override
    public void highlightInfoButton(WorkOrder workOrder) {
        View v = getListItem(workOrder);
        LinearLayout moreInfo = v.findViewById(R.id.more_info);
        moreInfo.setBackgroundColor(Color.GRAY);
    }

    @Override
    public void removeInfoButtonHighlight(WorkOrder workOrder) {
        View v = getListItem(workOrder);
        if(v != null) {
            LinearLayout moreInfo = v.findViewById(R.id.more_info);
            moreInfo.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    @Override
    public void expandInfoPanel(WorkOrder workOrder) {
        View v = getListItem(workOrder);

        TextView shortSummary = v.findViewById(R.id.short_summary);
        TextView summary = v.findViewById(R.id.summary);
        summary.setText(workOrder.getSummary());
        shortSummary.setVisibility(View.GONE);
        summary.setVisibility(View.VISIBLE);
        getAdapter().setExpandedWorkOrder(workOrder);
    }

    @Override
    public void shrinkInfoPanel(WorkOrder workOrder) {
        View v = getListItem(workOrder);

        if(v != null) {
            TextView shortSummary = v.findViewById(R.id.short_summary);
            TextView summary = v.findViewById(R.id.summary);
            shortSummary.setVisibility(View.VISIBLE);
            summary.setVisibility(View.GONE);
        }

        getAdapter().setExpandedWorkOrder(null);
    }

    private WorkOrderAdapter getAdapter() {
        return (WorkOrderAdapter) listView.getAdapter();
    }

    private View getListItem(WorkOrder workOrder) {
        int index = -1;

        for(int i = 0; i < adapter.getCount(); i++) {
            if(workOrder.equals(adapter.getItem(i))) {
                index = i;
                break;
            }
        }
        return listView.getChildAt(index -
                listView.getFirstVisiblePosition());
    }

    private class WorkOrderAdapter extends BaseAdapter {

         private WorkOrder expandedWorkOrder = null;

         private List<WorkOrder> list = new ArrayList<>();

         WorkOrderAdapter(List<WorkOrder> list) {
             this.list = list;
         }

         void setExpandedWorkOrder(WorkOrder workOrder) {
             this.expandedWorkOrder = workOrder;
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

                 convertView = getLayoutInflater().inflate(R.layout.workorder_item, parent, false);
                 holder.name = convertView.findViewById(R.id.name);
                 holder.shortSummary = convertView.findViewById(R.id.short_summary);
                 holder.summary = convertView.findViewById(R.id.summary);
                 holder.status = convertView.findViewById(R.id.status);
                 holder.date = convertView.findViewById(R.id.date);
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

             if(item.equals(expandedWorkOrder)) {
                 holder.summary.setText(item.getSummary());
                 holder.summary.setVisibility(View.VISIBLE);
                 holder.moreInfo.setBackgroundColor(Color.GRAY);
             }
             holder.name.setText(item.getName());
             holder.shortSummary.setText(item.getShortSummary(BuildConfig.MAX_SIZE_SHORT_SUMMARY));

             holder.date.setText(item.getEndAt(((LocalizationActivity)getActivity()).getLocale()));

             holder.status.setText(item.getStatus().toString());
             configureListeners(holder, position);
         }

         void configureListeners(Holder holder, final int position) {
             holder.moreInfo.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {

                     WorkOrder item = getItem(position);
                     if(item.equals(expandedWorkOrder)) {
                         workOrderPresenter.hideInfoClicked(expandedWorkOrder);
                         return;
                     }

                     if(expandedWorkOrder != null) {
                         workOrderPresenter.hideInfoClicked(expandedWorkOrder);
                     }

                     if(!item.equals(expandedWorkOrder)) {
                         workOrderPresenter.moreInfoClicked(list.get(position));
                     }
                 }
             });
         }

         class Holder {
             TextView name;
             TextView shortSummary;
             TextView summary;
             TextView status;
             TextView date;
             LinearLayout inspections;
             LinearLayout moreInfo;
         }

    }
}
