package br.com.bg7.appvistoria.productselection;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ExpandableListView;

import java.util.List;

import br.com.bg7.appvistoria.AlertDialog;
import br.com.bg7.appvistoria.ConfirmDialog;
import br.com.bg7.appvistoria.R;
import br.com.bg7.appvistoria.productselection.adapter.ProductSelectionAdapter;
import br.com.bg7.appvistoria.productselection.vo.ProductSelection;
import br.com.bg7.appvistoria.productselection.vo.ProductSelectionItem;
import br.com.bg7.appvistoria.projectselection.ProjectSelectionActivity;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by: elison
 * Date: 2017-09-04
 */
class ProductSelectionView extends ConstraintLayout implements ProductSelectionContract.View {

    ProductSelectionContract.Presenter productSelectionPresenter;
    ProductSelectionAdapter listAdapter;
    private ConfirmDialog confirmDialog;

    private ExpandableListView productList;
    private View buttons;
    private View cancelButton;
    private View confirmButton;

    public ProductSelectionView(Context context) {
        super(context);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.activity_product, this);
        productList = findViewById(R.id.product_list);
        buttons = findViewById(R.id.buttons);
        cancelButton = findViewById(R.id.cancel_button);
        confirmButton = findViewById(R.id.confirm_button);

        initializeListeners();
    }

    private void initializeListeners() {
        cancelButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                productSelectionPresenter.cancelClicked();
            }
        });

        confirmButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                productSelectionPresenter.createWorkOrderClicked();
            }
        });
    }

    @Override
    public void showConnectivityError() {
        AlertDialog dialog = new AlertDialog(getContext(), getContext().getString(R.string.could_not_load_data_error));
        dialog.show();
    }

    @Override
    public void showButtons() {
        buttons.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideButtons() {
        buttons.setVisibility(View.GONE);
    }

    @Override
    public void showProducts(List<ProductSelection> productSelectionList) {
        listAdapter = new ProductSelectionAdapter(getContext(), productSelectionList, productSelectionPresenter);
        productList.setAdapter(listAdapter);
    }

    @Override
    public void setPresenter(ProductSelectionContract.Presenter presenter) {
        productSelectionPresenter = checkNotNull(presenter);
    }

    @Override
    public void showProjectSelection() {
        Intent intent = new Intent(getContext(), ProjectSelectionActivity.class);
        getContext().startActivity(intent);
    }

    @Override
    public void showConfirmation() {
        String message = getContext().getString(R.string.confirm_create_workorder);
        confirmDialog = new ConfirmDialog(getContext(), message);
        View.OnClickListener confirmListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productSelectionPresenter.confirmCreateWorkOrderClicked();
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
    public void showWorkOrderScreen() {
        //TODO:  Adicionar caminho da nova tela de Inspeções com a referencia da inspecao
        Intent intent = new Intent(getContext(), ProjectSelectionActivity.class);
        getContext().startActivity(intent);

    }
}
