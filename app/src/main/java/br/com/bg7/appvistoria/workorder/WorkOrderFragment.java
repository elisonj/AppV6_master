package br.com.bg7.appvistoria.workorder;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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

import br.com.bg7.appvistoria.ConfirmDialog;
import br.com.bg7.appvistoria.R;
import br.com.bg7.appvistoria.config.ConfigActivity;
import br.com.bg7.appvistoria.data.WorkOrder;

import static br.com.bg7.appvistoria.R.id.status;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by: elison
 * Date: 2017-08-15
 */
public class WorkOrderFragment extends Fragment implements  WorkOrderContract.View {

    WorkOrderContract.Presenter workOrderPresenter;
    private ListView listView;
    private LinearLayout emptyLayout;
    private FloatingActionButton floatingActionButton;
    private WorkOrderAdapter adapter;

    private static final String MAP_ADDRESS = "geo:0,0?q=";
    private static final int MAX_SHORT_SUMMARY_SIZE = 50;
    private static final int MAX_INFO_LABEL_SIZE = 12;
    private static final int MAX_INSPECTION_LABEL_SIZE = 22;

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
    private Boolean mapAvailable = null;
    private Typeface nunito = null;
    private Typeface roboto = null;
    private Typeface nunitoBold = null;

    ConfirmDialog confirmDialog;
    ConfirmDialog confirmDialogNewWorkOrder;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_workorder, container, false);
        listView = root.findViewById(R.id.listview);
        emptyLayout = root.findViewById(R.id.empty_layout);
        floatingActionButton = root.findViewById(R.id.floatingActionButton);
        confirmDialog = new ConfirmDialog(getContext(), getString(R.string.confirm_open_maps));
        confirmDialogNewWorkOrder = new ConfirmDialog(getContext(), getString(R.string.confirm_new_workorder));

        nunito = Typeface.createFromAsset(getActivity().getAssets(),"nunitoregular.ttf");
        nunitoBold = Typeface.createFromAsset(getActivity().getAssets(),"nunitobold.ttf");
        roboto = Typeface.createFromAsset(getActivity().getAssets(),"robotoregular.ttf");

        configureListener();

        return root;
    }

    private void configureListener() {
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                workOrderPresenter.newWorkOrderClicked();
            }
        });
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
        if(list.size() > 0) {
            emptyLayout.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);

            adapter = new WorkOrderAdapter(list, showMapButtons);
            listView.setAdapter(adapter);
        }
    }

    @Override
    public void highlightInfoButton(WorkOrder workOrder) {
        View view = getListItem(workOrder);
        ImageView moreInfo = view.findViewById(R.id.image_more_info);
        setImageHighlightWorkOrder(moreInfo, workOrder.getStatus());
    }

    @Override
    public void removeInfoButtonHighlight(WorkOrder workOrder) {
        View view = getListItem(workOrder);
        if (view != null) {
            ImageView moreInfo = view.findViewById(R.id.image_more_info);
            removeImageHighlightWorkOrder(moreInfo, workOrder.getStatus());
        }
    }

    @Override
    public void expandInfoPanel(WorkOrder workOrder) {
        View view = getListItem(workOrder);

        TextView shortSummary = view.findViewById(R.id.short_summary);
        TextView summary = view.findViewById(R.id.summary);
        TextView date = view.findViewById(R.id.date);
        TextView dateTitle = view.findViewById(R.id.date_title);
        TextView locationTitle = view.findViewById(R.id.location_title);
        LinearLayout locationLayout = view.findViewById(R.id.location_layout);

        summary.setText(workOrder.getSummary());
        shortSummary.setVisibility(View.GONE);
        summary.setVisibility(View.VISIBLE);

        date.setVisibility(View.VISIBLE);
        dateTitle.setVisibility(View.VISIBLE);
        locationTitle.setVisibility(View.VISIBLE);
        locationLayout.setVisibility(View.VISIBLE);
        getAdapter().setExpandedWorkOrder(workOrder);
    }

    @Override
    public void shrinkInfoPanel(WorkOrder workOrder) {
        View view = getListItem(workOrder);

        if(view != null) {
            TextView shortSummary = view.findViewById(R.id.short_summary);
            TextView summary = view.findViewById(R.id.summary);
            shortSummary.setVisibility(View.VISIBLE);
            summary.setVisibility(View.GONE);
            TextView date = view.findViewById(R.id.date);
            TextView dateTitle = view.findViewById(R.id.date_title);
            TextView locationTitle = view.findViewById(R.id.location_title);
            LinearLayout locationLayout = view.findViewById(R.id.location_layout);

            date.setVisibility(View.GONE);
            dateTitle.setVisibility(View.GONE);
            locationTitle.setVisibility(View.GONE);
            locationLayout.setVisibility(View.GONE);
        }
        getAdapter().setExpandedWorkOrder(null);
    }

    @Override
    public boolean isMapAvailable() {
        if (mapAvailable != null) {
            return mapAvailable;
        }

        final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(MAP_ADDRESS));
        intent.setPackage("com.google.android.apps.maps");
        mapAvailable = (intent.resolveActivity(getActivity().getPackageManager()) != null);

        return mapAvailable;
    }

    @Override
    public void showOpenMapConfirmation(final WorkOrder workOrder) {
        View.OnClickListener openMapConfirmListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                workOrderPresenter.confirmOpenMapClicked(workOrder);
            }
        };

        View.OnClickListener openMapCancelListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                workOrderPresenter.cancelOpenMapClicked();
            }
        };

        confirmDialog.show(openMapConfirmListener, openMapCancelListener);
    }

    @Override
    public void showNewWorkOrderConfirmation() {
        View.OnClickListener openNewWorkOrderConfirmListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                workOrderPresenter.confirmNewWorkOrderClicked();
            }
        };

        View.OnClickListener openMNewWorkOrderancelListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                workOrderPresenter.cancelNewWorkOrderClicked();
            }
        };

        confirmDialogNewWorkOrder.show(openNewWorkOrderConfirmListener, openMNewWorkOrderancelListener);
    }

    @Override
    public void hideNewWorkOrderConfirmation() {
        confirmDialogNewWorkOrder.hide();
    }

    @Override
    public void showNewWorkOrderScreen() {
        Intent intent = new Intent(getContext(), ConfigActivity.class);
        getContext().startActivity(intent);
    }

    @Override
    public void hideOpenMapConfirmation() {
        confirmDialog.hide();
    }

    @Override
    public void showInMap(String address) {
        final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(MAP_ADDRESS + address));
        startActivity(intent);
    }

    private WorkOrderAdapter getAdapter() {
        return (WorkOrderAdapter) listView.getAdapter();
    }

    private View getListItem(WorkOrder workOrder) {
        int index = -1;

        for (int i = 0; i < adapter.getCount(); i++) {
            if (workOrder.equals(adapter.getItem(i))) {
                index = i;
                break;
            }
        }
        return listView.getChildAt(index -
                listView.getFirstVisiblePosition());
    }


    private void setImageHighlightWorkOrder(ImageView imageMoreInfo, WorkOrderStatus status) {
        if (status == WorkOrderStatus.NOT_STARTED) {
            imageMoreInfo.setImageResource(IMAGE_HIDE_INFO_NOT_STARTED);
        }
        if (status == WorkOrderStatus.COMPLETED) {
            imageMoreInfo.setImageResource(IMAGE_HIDE_INFO_COMPLETED);
        }
        if (status == WorkOrderStatus.IN_PROGRESS) {
            imageMoreInfo.setImageResource(IMAGE_HIDE_INFO_IN_PROGRESS);
        }
    }

    private void removeImageHighlightWorkOrder(ImageView imageMoreInfo, WorkOrderStatus status) {
        if (status == WorkOrderStatus.NOT_STARTED) {
            imageMoreInfo.setImageResource(IMAGE_MORE_INFO_NOT_STARTED);
        }
        if (status == WorkOrderStatus.COMPLETED) {
            imageMoreInfo.setImageResource(IMAGE_MORE_INFO_COMPLETED);
        }
        if (status == WorkOrderStatus.IN_PROGRESS) {
            imageMoreInfo.setImageResource(IMAGE_MORE_INFO_IN_PROGRESS);
        }
    }

    private class WorkOrderAdapter extends BaseAdapter {

         private boolean showMapButtons;
         private WorkOrder expandedWorkOrder = null;
         private View.OnClickListener cancelButton;

         private List<WorkOrder> list = new ArrayList<>();

         WorkOrderAdapter(List<WorkOrder> list, boolean showMapButtons) {
             this.list = list;
             this.showMapButtons = showMapButtons;
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
                 holder.dateTitle = convertView.findViewById(R.id.date_title);
                 holder.locationTitle  = convertView.findViewById(R.id.location_title);
                 holder.locationLayout = convertView.findViewById(R.id.location_layout);


                 holder.inspectionButtonText = convertView.findViewById(R.id.inspection_button_text);
                 holder.infoButtonText = convertView.findViewById(R.id.info_button_text);
                 holder.inspectionButtonTopText = convertView.findViewById(R.id.inspection_button_top_text);
                 holder.infoButtonTopText = convertView.findViewById(R.id.info_button_top_text);
                 holder.inspectionButtonText.setTypeface(nunito);
                 holder.infoButtonText.setTypeface(nunito);
                 holder.inspectionButtonTopText.setTypeface(nunito);
                 holder.infoButtonTopText.setTypeface(nunito);
                 holder.status.setTypeface(nunitoBold);
                 holder.name.setTypeface(nunito);
                 holder.shortSummary.setTypeface(roboto);
                 holder.summary.setTypeface(roboto);


                 convertView.setTag(holder);
             }

             if (holder == null) {
                 holder = (Holder) convertView.getTag();
             }

             populateWidget(holder, position);

             return convertView;
         }

        private void populateWidget(final Holder holder, final int position){

             WorkOrder item = getItem(position);
             if (item == null) {
                return;
             }

             resetHolder(holder);

             holder.name.setText(item.getName());
             holder.shortSummary.setText(item.getShortSummary(MAX_SHORT_SUMMARY_SIZE));
             holder.date.setText(item.getEndAt(((LocalizationActivity)getActivity()).getLocale()));
             holder.status.setText(item.getStatus().toString());
             holder.local.setText(item.getAddress());

             if (showMapButtons) holder.buttonMaps.setVisibility(View.VISIBLE);

             if (item.getStatus().toString().length() >= MAX_INFO_LABEL_SIZE) {
                 holder.moreInfoText.setVisibility(View.GONE);
             }

             if (item.getStatus().toString().length() >= MAX_INSPECTION_LABEL_SIZE) {
                 holder.inspectionsText.setVisibility(View.GONE);
             }
             configureListeners(holder, position);

             if (item.getStatus() == WorkOrderStatus.NOT_STARTED) {
                 holder.imageMoreInfo.setImageResource(IMAGE_MORE_INFO_NOT_STARTED);
                 holder.imageInspections.setImageResource(IMAGE_WORKORDER_NOT_STARTED);
                 holder.item.setBackgroundResource(BACKGROUND_NOT_STARTED);
                 holder.status.setTextColor(0xFF919aa0);
             }
             if (item.getStatus() == WorkOrderStatus.COMPLETED) {
                 holder.imageMoreInfo.setImageResource(IMAGE_MORE_INFO_COMPLETED);
                 holder.imageInspections.setImageResource(IMAGE_WORKORDER_COMPLETED);
                 holder.item.setBackgroundResource(BACKGROUND_COMPLETED);
                 holder.status.setTextColor(0xFF35ac53);
             }
             if (item.getStatus() == WorkOrderStatus.IN_PROGRESS) {
                 holder.imageMoreInfo.setImageResource(IMAGE_MORE_INFO_IN_PROGRESS);
                 holder.imageInspections.setImageResource(IMAGE_WORKORDER_IN_PROGRESS);
                 holder.item.setBackgroundResource(BACKGROUND_IN_PROGRESS);
                 holder.status.setTextColor(0xFF0552a1);
             }
             if (item.equals(expandedWorkOrder)) {
                 holder.summary.setText(item.getSummary());
                 holder.summary.setVisibility(View.VISIBLE);
                 holder.date.setVisibility(View.VISIBLE);
                 holder.dateTitle.setVisibility(View.VISIBLE);
                 holder.locationTitle.setVisibility(View.VISIBLE);
                 holder.locationLayout.setVisibility(View.VISIBLE);

                 setImageHighlightWorkOrder(holder.imageMoreInfo, item.getStatus());
             }
         }

        private void resetHolder(Holder holder) {
            holder.imageMoreInfo.setImageResource(IMAGE_MORE_INFO_NOT_STARTED);
            holder.imageInspections.setImageResource(IMAGE_WORKORDER_NOT_STARTED);

            holder.moreInfoText.setVisibility(View.VISIBLE);
            holder.inspectionsText.setVisibility(View.VISIBLE);
            holder.buttonMaps.setVisibility(View.GONE);
            holder.dateTitle.setVisibility(View.GONE);
            holder.locationLayout.setVisibility(View.GONE);
            holder.locationTitle.setVisibility(View.GONE);
            holder.date.setVisibility(View.GONE);
            holder.summary.setVisibility(View.GONE);
            holder.shortSummary.setVisibility(View.VISIBLE);
            holder.moreInfo.setBackgroundColor(Color.TRANSPARENT);
        }


        void configureListeners(Holder holder, final int position) {

             holder.moreInfo.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {

                     WorkOrder item = getItem(position);
                     if (item.equals(expandedWorkOrder)) {
                         workOrderPresenter.hideInfoClicked(expandedWorkOrder);
                         return;
                     }

                     if (expandedWorkOrder != null) {
                         workOrderPresenter.hideInfoClicked(expandedWorkOrder);
                     }

                     if (!item.equals(expandedWorkOrder)) {
                         workOrderPresenter.moreInfoClicked(list.get(position));
                     }
                 }
             });

            if (getItem(position).getAddress() == null) {
                return;
            }

            holder.buttonMaps.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    workOrderPresenter.openMapClicked(list.get(position));
                }
            });
         }

         class Holder {
             TextView name;
             TextView shortSummary;
             TextView summary;
             TextView status;
             TextView date;
             TextView local;
             TextView dateTitle;
             TextView locationTitle;
             TextView inspectionButtonText;
             TextView infoButtonText;
             TextView inspectionButtonTopText;
             TextView infoButtonTopText;
             LinearLayout inspections;
             LinearLayout inspectionsText;
             LinearLayout moreInfo;
             LinearLayout moreInfoText;
             LinearLayout locationLayout;
             LinearLayout item;
             ImageView buttonMaps;
             ImageView imageMoreInfo;
             ImageView imageInspections;
         }

    }
}
