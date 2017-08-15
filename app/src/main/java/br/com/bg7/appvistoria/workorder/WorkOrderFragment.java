package br.com.bg7.appvistoria.workorder;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
        WorkOrderAdapter adapter = new WorkOrderAdapter(this, list);
        listView.setAdapter(adapter);
    }

     private class WorkOrderAdapter extends BaseAdapter {

         private LayoutInflater inflater=null;
         private List<WorkOrder> list = new ArrayList<>();

         WorkOrderAdapter(WorkOrderFragment fragment, List<WorkOrder> list) {
             this.list = list;
             this.inflater = (LayoutInflater)fragment.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

             if(convertView != null) {
                 holder = (Holder) convertView.getTag();
             }

             if(holder == null) {
                 convertView = inflater.inflate(R.layout.workorder_item, parent, false);
                 holder = new Holder();
                 holder.name = convertView.findViewById(R.id.name);
                 holder.shortSumary = convertView.findViewById(R.id.short_sumary);
                 holder.status = convertView.findViewById(R.id.status);
                 convertView.setTag(holder);
             }
             populateWidget(holder, position);

             return convertView;
         }

         private void populateWidget(final Holder holder, final int position){
             try {

                 WorkOrder item = getItem(position);

                 if(item != null) {
                     holder.name.setText(item.getName());
                     holder.shortSumary.setText(item.getShortSumary());
                     holder.status.setText(item.getStatus().toString());
                 }

             } catch (Exception e) {
                 e.printStackTrace();
             }
         }

         class Holder {
             TextView name;
             TextView shortSumary;
             TextView status;
         }

    }


}
