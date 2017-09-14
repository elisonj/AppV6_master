package br.com.bg7.appvistoria.productselection;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ExpandableListView;

import java.util.HashMap;
import java.util.List;

import br.com.bg7.appvistoria.AlertDialog;
import br.com.bg7.appvistoria.ConfirmDialog;
import br.com.bg7.appvistoria.R;
import br.com.bg7.appvistoria.productselection.vo.ProductSelectionItem;
import br.com.bg7.appvistoria.projectselection.ProjectSelectionActivity;
import br.com.bg7.appvistoria.productselection.vo.ProductSelection;
import br.com.bg7.appvistoria.projectselection.vo.Project;

import static br.com.bg7.appvistoria.productselection.ProductSelectionActivity.KEY_ADDRESS;
import static br.com.bg7.appvistoria.productselection.ProductSelectionActivity.KEY_PROJECT;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by: elison
 * Date: 2017-09-04
 */
class ProductSelectionView extends ConstraintLayout implements ProductSelectionContract.View {

    ProductSelectionContract.Presenter productSelectionPresenter;
    ProductSelectionAdapter listAdapter;
    ExpandableListView expListView;
    private ConfirmDialog confirmDialog;

    private View linearBotton;
    private View linearCancel;
    private View linearConfirm;

    public ProductSelectionView(Context context) {
        super(context);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.activity_product, this);
        expListView = findViewById(R.id.listview);
        linearBotton = findViewById(R.id.linear_botton);
        linearCancel = findViewById(R.id.linear_cancel);
        linearConfirm = findViewById(R.id.linear_confirm);

        initializeListeners();
    }

    private void initializeListeners() {
        linearCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                productSelectionPresenter.cancelClicked();
            }
        });

        linearConfirm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                productSelectionPresenter.confirmCreateWorkOrderClicked();
            }
        });
    }

    @Override
    public void showConnectivityError() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        boolean isConnect = connectivityManager.getActiveNetworkInfo() != null
                && connectivityManager.getActiveNetworkInfo().isAvailable()
                && connectivityManager.getActiveNetworkInfo().isConnected();

        if (!isConnect) {
            AlertDialog dialog = new AlertDialog(getContext(), getContext().getString(R.string.cannot_create_workorder));
            dialog.show();
        }
    }

    @Override
    public void showProducts(List<ProductSelection> productSelectionList) {
        linearBotton.setVisibility(View.GONE);

        listAdapter = new ProductSelectionAdapter(getContext(), productSelectionList, productSelectionPresenter);
        expListView.setAdapter(listAdapter);
    }

    @Override
    public void setPresenter(ProductSelectionContract.Presenter presenter) {
        productSelectionPresenter = checkNotNull(presenter);
    }

    @Override
    public void showSelectedQuantity(ProductSelectionItem item, int quantity) {
        linearBotton.setVisibility(View.VISIBLE);
    }

    @Override
    public void showProjectSelection(Project project, String address) {
        Intent intent = new Intent(getContext(), ProjectSelectionActivity.class);
        intent.putExtra(KEY_PROJECT, project);
        intent.putExtra(KEY_ADDRESS, address);
        getContext().startActivity(intent);
    }

    @Override
    public void showConfirmation() {
        String message = getContext().getString(R.string.confirm_create_workorder);
        confirmDialog = new ConfirmDialog(getContext(), message);
        View.OnClickListener confirmListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productSelectionPresenter.createWorkOrderClicked();
            }
        };

        View.OnClickListener cancelListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productSelectionPresenter.cancelCreateWorkOrderClicked();
            }
        };
        confirmDialog.show(confirmListener, cancelListener);
    }

    @Override
    public void hideConfirmation() {
        confirmDialog.hide();
    }

    @Override
    public void showCannotDuplicateWorkorderError() {
        confirmDialog.hide();
        showError(getContext().getString(R.string.cannot_duplicate_workorder));
    }

    @Override
    public void showWorkOrderScreen() {
        confirmDialog.hide();

        //TODO:  Adicionar caminho da nova tela de Inspeções com a referencia da inspecao
        Intent intent = new Intent(getContext(), ProjectSelectionActivity.class);
        getContext().startActivity(intent);

    }

    private void showError(String message) {
        AlertDialog dialog = new AlertDialog(getContext(), message);
        dialog.show();
    }
}
