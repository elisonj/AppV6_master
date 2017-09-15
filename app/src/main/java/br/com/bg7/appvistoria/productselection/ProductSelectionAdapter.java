package br.com.bg7.appvistoria.productselection;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.weiwangcn.betterspinner.library.BetterSpinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.com.bg7.appvistoria.R;
import br.com.bg7.appvistoria.productselection.vo.ProductSelection;
import br.com.bg7.appvistoria.productselection.vo.ProductSelectionHeader;
import br.com.bg7.appvistoria.productselection.vo.ProductSelectionItem;

/**
 * Created by: luciolucio
 * Date: 2017-09-13
 */
class ProductSelectionAdapter extends BaseExpandableListAdapter {

    private ArrayList<ProductSelectionHeader> headers = new ArrayList<>();
    private HashMap<ProductSelectionHeader, List<ProductSelectionItem>> items;

    private static final String COLON_SEPARATOR = ": ";
    private static final String EMPTY_SPACE = " ";

    private Context context;
    private ProductSelectionContract.Presenter presenter;

    ProductSelectionAdapter(Context context, List<ProductSelection> productSelections, ProductSelectionContract.Presenter presenter) {
        this.context = context;
        this.presenter = presenter;

        populateData(productSelections);
    }

    private void populateData(List<ProductSelection> productSelections) {

        headers = new ArrayList<>();
        items = new HashMap<>();

        for (ProductSelection productSelection : productSelections) {
            headers.add(productSelection.getHeader());
            items.put(productSelection.getHeader(), productSelection.getItems());
        }
    }

    @Override
    public ProductSelectionItem getChild(int groupPosition, int childPosition) {
        return items.get(headers.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final ProductSelectionItem item = getChild(groupPosition, childPosition);
        final boolean isSelected = presenter.isItemSelected(item);

        if (convertView == null) {
            convertView = View.inflate(context, R.layout.product_selection_item, null);
        }

        final LinearLayout linearMain = convertView.findViewById(R.id.linear_main);
        linearMain.setBackgroundColor(context.getColor(R.color.item_default));
        final TextView product = convertView.findViewById(R.id.product);
        final TextView quantity = convertView.findViewById(R.id.quantity);
        product.setTextColor(context.getColor(R.color.item_font_default));
        quantity.setTextColor(context.getColor(R.color.item_font_default));
        final LinearLayout linearQuantity = convertView.findViewById(R.id.linear_quantity);
        final ImageView arrowItem = convertView.findViewById(R.id.arrow_item);
        arrowItem.setImageDrawable(context.getResources().getDrawable(R.drawable.arrow_open_list, null));
        final BetterSpinner spinner = convertView.findViewById(R.id.spinner);


        String[] initialSpinnerValues = getInitialSpinnerValues(item);
        ArrayAdapter<String> items = new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, initialSpinnerValues);
        spinner.setAdapter(items);

        spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String spinnerItem = adapterView.getAdapter().getItem(position).toString();

                int firtsSpace = spinnerItem.indexOf(EMPTY_SPACE);
                String quantitySelected = spinnerItem.substring(0, firtsSpace);

                formatSelectedChild(quantitySelected, item, linearMain, product, quantity, arrowItem);
                selectProduct(item, quantitySelected);
                linearQuantity.setVisibility(View.GONE);
                arrowItem.setImageDrawable(context.getResources().getDrawable(R.drawable.arrow_open_white, null));
            }
        });


        linearMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int openArrow = R.drawable.arrow_open_list;
                int closeArrow = R.drawable.arrow_close_list;

                if (isSelected) {
                    openArrow = R.drawable.arrow_open_white;
                    closeArrow = R.drawable.arrow_close_white;
                }

                if (linearQuantity.isShown()) {
                    linearQuantity.setVisibility(View.GONE);
                    arrowItem.setImageDrawable(context.getResources().getDrawable(openArrow, null));
                    return;
                }
                linearQuantity.setVisibility(View.VISIBLE);
                arrowItem.setImageDrawable(context.getResources().getDrawable(closeArrow, null));
            }
        });

        String title = item.getTitle() + COLON_SEPARATOR;
        product.setText(title);

        if (isSelected) {
            formatSelectedChild(String.valueOf(item.getCount()), item, linearMain, product, quantity, arrowItem);
            showSelectedWhiteArrows(arrowItem, linearQuantity);

            return convertView;
        }

        if (item.getCount() > 1) {
            String available = item.getCount() + EMPTY_SPACE + context.getString(R.string.available_items);
            quantity.setText(available);
            return convertView;
        }

        String available = item.getCount() + EMPTY_SPACE + context.getString(R.string.available_item);
        quantity.setText(available);
        return convertView;
    }

    private void showSelectedWhiteArrows(ImageView arrowItem, LinearLayout linearQuantity) {
        if (linearQuantity.isShown()) {
            arrowItem.setImageDrawable(context.getResources().getDrawable(R.drawable.arrow_close_white, null));
            return;
        }
        arrowItem.setImageDrawable(context.getResources().getDrawable(R.drawable.arrow_open_white, null));
    }

    @NonNull
    private String formatSelectedChild(String quantitySelected, ProductSelectionItem item, LinearLayout linearMain, TextView product, TextView quantity, ImageView arrowItem) {
        linearMain.setBackgroundColor(context.getResources().getColor(R.color.item_orange, null));
        product.setTextColor(context.getResources().getColor(R.color.white, null));

        String format = String.format(
                context.getResources().getString(R.string.active_item_selected),
                quantitySelected,
                String.valueOf(item.getCount()));

        quantity.setText(format);
        quantity.setTextColor(context.getResources().getColor(R.color.white, null));
        return quantitySelected;
    }

    private void selectProduct(ProductSelectionItem item, String quantitySelected) {
        presenter.chooseQuantity(item, Integer.parseInt(quantitySelected));
    }

    private String[] getInitialSpinnerValues(ProductSelectionItem item) {
        String[] items = new String[item.getCount()];


        for (int cont = 1; cont <= item.getCount(); cont++) {
            if (cont == 1) {
                items[cont - 1] = String.valueOf(cont) + EMPTY_SPACE + context.getString(R.string.active_item);
                continue;
            }
            items[cont - 1] = String.valueOf(cont) + EMPTY_SPACE + context.getString(R.string.actives_item);
        }
        return items;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.items.get(this.headers.get(groupPosition)).size();
    }

    @Override
    public ProductSelectionHeader getGroup(int groupPosition) {
        return headers.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return headers.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        ProductSelectionHeader header = getGroup(groupPosition);
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.product_selection_header, null);
        }

        TextView category = convertView.findViewById(R.id.category);
        ImageView imageCategory = convertView.findViewById(R.id.image_category);

        imageCategory.setImageDrawable(header.getDrawable(context));

        category.setTypeface(null, Typeface.BOLD);
        category.setText(header.getTitle());

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
