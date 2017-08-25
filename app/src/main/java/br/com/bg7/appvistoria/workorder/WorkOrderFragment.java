package br.com.bg7.appvistoria.workorder;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.akexorcist.localizationactivity.LocalizationActivity;

import java.util.ArrayList;
import java.util.List;

import br.com.bg7.appvistoria.R;
import br.com.bg7.appvistoria.data.WorkOrder;

import static br.com.bg7.appvistoria.R.id.status;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by: elison
 * Date: 2017-08-15
 *
 * TODO: Reaplicar os refactoring perdidos no merge (ver PR https://bitbucket.org/tagnclick/0885-appvistoria/pull-requests/56/visitas-status/diff)
 */
public class WorkOrderFragment extends Fragment implements  WorkOrderContract.View {

    WorkOrderContract.Presenter workOrderPresenter;
    private ListView listView;
    private WorkOrderAdapter adapter;

    private static final String MAP_ADDRESS = "geo:0,0?q=";
    private static final int MAX_SIZE_SUMARY = 43;
    private static final int MAX_SIZE_TEXT_INFO = 12;
    private static final int MAX_SIZE_TEXT_INSPECTION = 22;

    private static final int IMAGE_MORE_INFO_COMPLETED = R.drawable.ic_info_completed;
    private static final int IMAGE_MORE_INFO_IN_PROGRESS = R.drawable.ic_info_in_progress;
    private static final int IMAGE_MORE_INFO_NOT_STARTED = R.drawable.ic_info_not_started;
    private static final int IMAGE_HIDE_INFO_COMPLETED = R.drawable.ic_hide_info_completed;
    private static final int IMAGE_HIDE_INFO_IN_PROGRESS = R.drawable.ic_hide_info_in_progress;
    private static final int IMAGE_HIDE_INFO_NOT_STARTED = R.drawable.ic_hide_info_not_started;
    private static final int IMAGE_WORKORDER_NOT_STARTED = R.drawable.ic_workorder_not_started;
    private static final int IMAGE_WORKORDER_COMPLETED = R.drawable.ic_workorder_completed;
    private static final int IMAGE_WORKORDER_IN_PROGRESS = R.drawable.ic_workorder_in_progress;

    private static final int BACKGROUND_COMPLETED = R.drawable.background_workorder_completed;
    private static final int BACKGROUND_IN_PROGRESS = R.drawable.background_workorder_in_progress;
    private static final int BACKGROUND_NOT_STARTED = R.drawable.background_workorder_not_started;
    private boolean cacheAvaliable = false;

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
    }

    @Override
    public void setPresenter(WorkOrderContract.Presenter presenter) {
        workOrderPresenter = checkNotNull(presenter);
    }

    @Override
    public void showList(List<WorkOrder> list, boolean showMapButtons) {
        adapter = new WorkOrderAdapter(this, list, showMapButtons);
        listView.setAdapter(adapter);
    }

    @Override
    public void highlightInfoButton(WorkOrder workOrder) {
        View v = getItemListView(workOrder);
        ImageView moreInfo = v.findViewById(R.id.image_more_info);
        setImageHighlightWorkOrder(moreInfo, workOrder.getStatus());
    }

    @Override
    public void removeInfoButtonHighlight(WorkOrder workOrder) {
        View v = getItemListView(workOrder);
        if(v != null) {
            ImageView moreInfo = v.findViewById(R.id.image_more_info);
            removeImageHighlightWorkOrder(moreInfo, workOrder.getStatus());
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

    @Override
    public boolean isMapAvailable() {
        if(cacheAvaliable) {
            return true;
        }
        final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(MAP_ADDRESS));
        intent.setPackage("com.google.android.apps.maps");
        cacheAvaliable = (intent.resolveActivity(getActivity().getPackageManager()) != null);
        return cacheAvaliable;
    }

    @Override
    public void showInMap(String address) {
        final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(MAP_ADDRESS + address));
        startActivity(intent);
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


    private void setImageHighlightWorkOrder(ImageView imageMoreInfo, WorkOrderStatus status) {
        if(status == WorkOrderStatus.NOT_STARTED) {
            imageMoreInfo.setImageResource(IMAGE_HIDE_INFO_NOT_STARTED);
        }
        if(status == WorkOrderStatus.COMPLETED) {
            imageMoreInfo.setImageResource(IMAGE_HIDE_INFO_COMPLETED);
        }
        if(status == WorkOrderStatus.IN_PROGRESS) {
            imageMoreInfo.setImageResource(IMAGE_HIDE_INFO_IN_PROGRESS);
        }
    }

    private void removeImageHighlightWorkOrder(ImageView imageMoreInfo, WorkOrderStatus status) {
        if(status == WorkOrderStatus.NOT_STARTED) {
            imageMoreInfo.setImageResource(IMAGE_MORE_INFO_NOT_STARTED);
        }
        if(status == WorkOrderStatus.COMPLETED) {
            imageMoreInfo.setImageResource(IMAGE_MORE_INFO_COMPLETED);
        }
        if(status == WorkOrderStatus.IN_PROGRESS) {
            imageMoreInfo.setImageResource(IMAGE_MORE_INFO_IN_PROGRESS);
        }
    }

    private class WorkOrderAdapter extends BaseAdapter {

         private boolean showMapButtons;

         private WorkOrder highlightWorkOrder = null;

         private LayoutInflater inflater=null;
         private List<WorkOrder> list = new ArrayList<>();

         WorkOrderAdapter(WorkOrderFragment fragment, List<WorkOrder> list, boolean showMapButtons) {
             this.list = list;
             this.inflater = fragment.getLayoutInflater();
             this.showMapButtons = showMapButtons;
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
                 holder.item = convertView.findViewById(R.id.item);
                 holder.name = convertView.findViewById(R.id.name);
                 holder.shortSummary = convertView.findViewById(R.id.short_summary);
                 holder.summary = convertView.findViewById(R.id.summary);
                 holder.status = convertView.findViewById(status);
                 holder.date = convertView.findViewById(R.id.date);
                 holder.local = convertView.findViewById(R.id.local);
                 holder.inspections = convertView.findViewById(R.id.inspections);
                 holder.moreInfo = convertView.findViewById(R.id.more_info);
                 holder.inspectionsText = convertView.findViewById(R.id.inspections_text);
                 holder.moreInfoText = convertView.findViewById(R.id.more_info_text);
                 holder.buttonMaps = convertView.findViewById(R.id.bt_maps);
                 holder.imageInspections = convertView.findViewById(R.id.image_inspections);
                 holder.imageMoreInfo = convertView.findViewById(R.id.image_more_info);
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

             resetHolder(holder);

             holder.name.setText(item.getName());
             holder.shortSummary.setText(item.getShortSummary(MAX_SIZE_SUMARY));
             holder.date.setText(item.getEndAt(((LocalizationActivity)getActivity()).getLocale()));
             holder.status.setText(item.getStatus().toString());
             holder.local.setText(item.getAddress());

             if(showMapButtons) holder.buttonMaps.setVisibility(View.VISIBLE);

             if(item.getStatus().toString().length() >= MAX_SIZE_TEXT_INFO) {
                 holder.moreInfoText.setVisibility(View.GONE);
             }

             if(item.getStatus().toString().length() >= MAX_SIZE_TEXT_INSPECTION) {
                 holder.inspectionsText.setVisibility(View.GONE);
             }
             configureListeners(holder, position);

             if(item.getStatus() == WorkOrderStatus.NOT_STARTED) {
                 holder.imageMoreInfo.setImageResource(IMAGE_MORE_INFO_NOT_STARTED);
                 holder.imageInspections.setImageResource(IMAGE_WORKORDER_NOT_STARTED);
                 holder.item.setBackgroundResource(BACKGROUND_NOT_STARTED);
             }
             if(item.getStatus() == WorkOrderStatus.COMPLETED) {
                 holder.imageMoreInfo.setImageResource(IMAGE_MORE_INFO_COMPLETED);
                 holder.imageInspections.setImageResource(IMAGE_WORKORDER_COMPLETED);
                 holder.item.setBackgroundResource(BACKGROUND_COMPLETED);
             }
             if(item.getStatus() == WorkOrderStatus.IN_PROGRESS) {
                 holder.imageMoreInfo.setImageResource(IMAGE_MORE_INFO_IN_PROGRESS);
                 holder.imageInspections.setImageResource(IMAGE_WORKORDER_IN_PROGRESS);
                 holder.item.setBackgroundResource(BACKGROUND_IN_PROGRESS);
             }
             if(item.equals(highlightWorkOrder)) {
                 holder.summary.setText(item.getSummary());
                 holder.summary.setVisibility(View.VISIBLE);
                 setImageHighlightWorkOrder(holder.imageMoreInfo, item.getStatus());
             }
         }

        private void resetHolder(Holder holder) {
            holder.imageMoreInfo.setImageResource(IMAGE_MORE_INFO_NOT_STARTED);
            holder.imageInspections.setImageResource(IMAGE_WORKORDER_NOT_STARTED);

            holder.moreInfoText.setVisibility(View.VISIBLE);
            holder.inspectionsText.setVisibility(View.VISIBLE);
            holder.buttonMaps.setVisibility(View.GONE);
            holder.summary.setVisibility(View.GONE);
            holder.moreInfo.setBackgroundColor(Color.TRANSPARENT);
        }


        void configureListeners(Holder holder, final int position) {
             holder.moreInfo.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {

                     WorkOrder item = getItem(position);
                     if(item.equals(highlightWorkOrder)) {
                         workOrderPresenter.hideInfoClicked(highlightWorkOrder);
                         return;
                     }

                     if(highlightWorkOrder != null) {
                         workOrderPresenter.hideInfoClicked(highlightWorkOrder);
                     }

                     if(!item.equals(highlightWorkOrder)) {
                         workOrderPresenter.moreInfoClicked(list.get(position));
                     }
                 }
             });

            if(getItem(position).getAddress() == null) {
                return;
            }

            if(showMapButtons) {
                holder.buttonMaps.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        workOrderPresenter.openMapClicked(list.get(position));
                    }
                });
            }
         }

         class Holder {
             TextView name;
             TextView shortSummary;
             TextView summary;
             TextView status;
             TextView date;
             TextView local;
             LinearLayout inspections;
             LinearLayout inspectionsText;
             LinearLayout moreInfo;
             LinearLayout moreInfoText;
             LinearLayout item;
             ImageView buttonMaps;
             ImageView imageMoreInfo;
             ImageView imageInspections;
         }

    }
}
