package br.com.bg7.appvistoria.workorder;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_workorder, container, false);

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

    }
}
