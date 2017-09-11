package br.com.bg7.appvistoria.product;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.weiwangcn.betterspinner.library.BetterSpinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.bg7.appvistoria.AlertDialog;
import br.com.bg7.appvistoria.ConfirmDialog;
import br.com.bg7.appvistoria.R;
import br.com.bg7.appvistoria.projectselection.ProjectSelectionActivity;
import br.com.bg7.appvistoria.projectselection.vo.Category;
import br.com.bg7.appvistoria.projectselection.vo.Product;
import br.com.bg7.appvistoria.projectselection.vo.ProductSelection;
import br.com.bg7.appvistoria.projectselection.vo.Project;

import static br.com.bg7.appvistoria.product.ProductActivity.KEY_ADDRESS;
import static br.com.bg7.appvistoria.product.ProductActivity.KEY_PROJECT;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by: elison
 * Date: 2017-09-04
 */
class ProductView  extends ConstraintLayout implements  ProductSelectionContract.View {

    private static final String KEY_COLON = ": ";
    private static final String EMPTY_SPACE = " ";

    ProductSelectionContract.Presenter productSelectionPresenter;
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<Category> listDataHeader = new ArrayList<>();
    private ConfirmDialog confirmDialog;

    HashMap<Category, List<ProductSelectionItem>> listDataChild = new HashMap<>();

    private HashMap<Long, Drawable> drawableCategories = new HashMap<>();
    private View linearBotton;
    private View linearCancel;
    private View linearConfirm;

    public ProductView(Context context) {
        super(context);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.activity_product, this);
        expListView = findViewById(R.id.listview);
        linearBotton = findViewById(R.id.linear_botton);
        linearCancel = findViewById(R.id.linear_cancel);
        linearConfirm = findViewById(R.id.linear_confirm);

        populateCategoriesDrawable();
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

    private void populateCategoriesDrawable() {
        drawableCategories.put(1L, getContext().getDrawable(R.drawable.category_car));
        drawableCategories.put(2L, getContext().getDrawable(R.drawable.category_trucks));
        drawableCategories.put(3L, getContext().getDrawable(R.drawable.category_planes));
        drawableCategories.put(4L, getContext().getDrawable(R.drawable.category_build));
        drawableCategories.put(5L, getContext().getDrawable(R.drawable.category_heavy_machines));
    }

    @Override
    public void showConnectivityError() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        boolean isConnect = connectivityManager.getActiveNetworkInfo() != null
                && connectivityManager.getActiveNetworkInfo().isAvailable()
                && connectivityManager.getActiveNetworkInfo().isConnected();

        if(!isConnect) {
            AlertDialog dialog = new AlertDialog(getContext(), getContext().getString(R.string.cannot_create_workorder));
            dialog.show();
        }
    }

    @Override
    public void showProducts(List<ProductSelection> productSelectionList) {
        listDataHeader.clear();
        listDataChild.clear();

        linearBotton.setVisibility(View.GONE);

        listAdapter = new ExpandableListAdapter(getContext(), productSelectionList);
        expListView.setAdapter(listAdapter);
    }

    @Override
    public void setPresenter(ProductSelectionContract.Presenter presenter) {
        productSelectionPresenter = checkNotNull(presenter);
    }

    @Override
    public void showSelectedQuantity(Category category, Product product, int quantity) {
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

    private class ExpandableListAdapter extends BaseExpandableListAdapter {

        private Context context;

        ExpandableListAdapter(Context context, List<ProductSelection>  productSelections ) {
            this.context = context;

            listDataHeader = new ArrayList<>();
            listDataChild = new HashMap<>();

            extractProductQuantityToAdapter(productSelections);
        }

        @Override
        public ProductSelectionItem getChild(int groupPosition, int childPosition) {
            List<ProductSelectionItem> productSelectionItems = listDataChild.get(listDataHeader.get(groupPosition));
            return productSelectionItems.get(childPosition);
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public View getChildView(final int groupPosition, final int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {

            final ProductSelectionItem childText = getChild(groupPosition, childPosition);
            final boolean isSelected = productSelectionPresenter.isProductSelected(childText.getProduct(), getGroup(groupPosition));

            if (convertView == null) {
                convertView = inflate(context, R.layout.product_list_item, null);
            }

            final LinearLayout linearMain = convertView.findViewById(R.id.linear_main);
            linearMain.setBackgroundColor(getContext().getColor(R.color.item_default));
            final TextView product = convertView.findViewById(R.id.product);
            final TextView quantity = convertView.findViewById(R.id.quantity);
            product.setTextColor(getContext().getColor(R.color.item_font_default));
            quantity.setTextColor(getContext().getColor(R.color.item_font_default));
            final LinearLayout linearQuantity = convertView.findViewById(R.id.linear_quantity);
            final ImageView arrowItem = convertView.findViewById(R.id.arrow_item);
            arrowItem.setImageDrawable(getResources().getDrawable(R.drawable.arrow_open_list, null));
            final BetterSpinner spinner = convertView.findViewById(R.id.spinner);


            String[] initialSpinnerValues = getInitialSpinnerValues(childText);
            ArrayAdapter<String> items = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, initialSpinnerValues);
            spinner.setAdapter(items);

            spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    String spinnerItem = adapterView.getAdapter().getItem(position).toString();

                    int firtsSpace = spinnerItem.indexOf(EMPTY_SPACE);
                    String quantitySelected = spinnerItem.substring(0, firtsSpace);

                    formatSelectedChild(quantitySelected, childText, linearMain, product, quantity, arrowItem);
                    selectProduct(quantitySelected, groupPosition, childText);
                    linearQuantity.setVisibility(View.GONE);
                    arrowItem.setImageDrawable(getResources().getDrawable(R.drawable.arrow_open_white, null));
                }
            });


            linearMain.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {

                    int openArrow = R.drawable.arrow_open_list;
                    int closeArrow = R.drawable.arrow_close_list;

                    if(isSelected) {
                        openArrow = R.drawable.arrow_open_white;
                        closeArrow = R.drawable.arrow_close_white;
                    }

                    if(linearQuantity.isShown()) {
                        linearQuantity.setVisibility(View.GONE);
                        arrowItem.setImageDrawable(getResources().getDrawable(openArrow, null));
                        return;
                    }
                    linearQuantity.setVisibility(View.VISIBLE);
                    arrowItem.setImageDrawable(getResources().getDrawable(closeArrow, null));
                }
            });

            String title = childText.getProduct().getType() + KEY_COLON;
            product.setText(title);

            if(isSelected) {
                formatSelectedChild(String.valueOf(childText.getQuantity()), childText, linearMain, product, quantity, arrowItem);
                showSelectedWhiteArrows(arrowItem, linearQuantity);

                return convertView;
            }

            if(childText.getQuantity() > 1) {
                String available = childText.getQuantity() + EMPTY_SPACE + getContext().getString(R.string.available_items);
                quantity.setText(available);
                return convertView;
            }

            String available = childText.getQuantity() + EMPTY_SPACE + getContext().getString(R.string.available_item);
            quantity.setText(available);
            return convertView;
        }

        void showSelectedWhiteArrows(ImageView arrowItem, LinearLayout linearQuantity) {
            if(linearQuantity.isShown()) {
                arrowItem.setImageDrawable(getResources().getDrawable(R.drawable.arrow_close_white, null));
                return;
            }
            arrowItem.setImageDrawable(getResources().getDrawable(R.drawable.arrow_open_white, null));
        }

        private void extractProductQuantityToAdapter(List<ProductSelection> productSelections) {

            for(ProductSelection  productSelection: productSelections) {

                if(!listDataHeader.contains(productSelection.getCategory())) {
                    listDataHeader.add(productSelection.getCategory());
                }

                List<ProductSelectionItem> list = listDataChild.get(productSelection.getCategory());

                if(list == null) list =  new ArrayList<>();

                for(Map.Entry<Product, Integer> entry : productSelection.getProducts().entrySet()) {
                    ProductSelectionItem item = new ProductSelectionItem(entry.getKey(), entry.getValue());
                    list.add(item);
                }

                listDataChild.put(productSelection.getCategory(), list);
            }
        }

        @NonNull
        private String formatSelectedChild(String quantitySelected, ProductSelectionItem childText, LinearLayout linearMain, TextView product, TextView quantity, ImageView arrowItem) {
            linearMain.setBackgroundColor(getResources().getColor(R.color.item_orange, null));
            product.setTextColor(getResources().getColor(R.color.white, null));

            String format = String.format(
                    getResources().getString(R.string.active_item_selected),
                    quantitySelected,
                    String.valueOf(childText.getQuantity()));

            quantity.setText(format);
            quantity.setTextColor(getResources().getColor(R.color.white, null));
            return quantitySelected;
        }

        private void selectProduct(String quantitySelected, int groupPosition, ProductSelectionItem childText) {
            productSelectionPresenter.chooseQuantity(getGroup(groupPosition), childText.getProduct(), Integer.parseInt(quantitySelected));
        }

        private String[] getInitialSpinnerValues(ProductSelectionItem childText) {
            String[] items = new String[childText.getQuantity()];


            for(int cont = 1; cont <= childText.getQuantity(); cont++) {
                if(cont == 1 ) {
                    items[cont - 1] = String.valueOf(cont) + EMPTY_SPACE + getContext().getString(R.string.active_item);
                    continue;
                }
                items[cont - 1] = String.valueOf(cont) + EMPTY_SPACE + getContext().getString(R.string.actives_item);
            }
            return items;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return listDataChild.get(listDataHeader.get(groupPosition))
                    .size();
        }

        @Override
        public Category getGroup(int groupPosition) {
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
            Category headerTitle =  getGroup(groupPosition);
            if (convertView == null) {
                convertView = inflate(context, R.layout.product_list_category, null);
            }

            TextView category = convertView.findViewById(R.id.category);
            ImageView imageCategory = convertView.findViewById(R.id.image_category);

            imageCategory.setImageDrawable(drawableCategories.get((long)groupPosition+1));

            category.setTypeface(null, Typeface.BOLD);
            category.setText(headerTitle.getName());

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
