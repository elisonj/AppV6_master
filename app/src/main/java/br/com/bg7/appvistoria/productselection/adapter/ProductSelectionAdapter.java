package br.com.bg7.appvistoria.productselection.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.com.bg7.appvistoria.R;
import br.com.bg7.appvistoria.productselection.ProductSelectionContract;
import br.com.bg7.appvistoria.productselection.vo.ProductSelection;
import br.com.bg7.appvistoria.productselection.vo.ProductSelectionHeader;
import br.com.bg7.appvistoria.productselection.vo.ProductSelectionItem;
import br.com.bg7.appvistoria.productselection.vo.ProductSelectionItemQuantity;
import me.srodrigo.androidhintspinner.HintSpinner;

/**
 * Created by: luciolucio
 * Date: 2017-09-13
 */
public class ProductSelectionAdapter extends BaseExpandableListAdapter {

    private static final String COLON_SEPARATOR = ": ";
    private static final String EMPTY_SPACE = " ";
    private ArrayList<ProductSelectionHeader> headers = new ArrayList<>();
    private HashMap<ProductSelectionHeader, List<ProductSelectionItem>> items;
    private Context context;
    private ProductSelectionContract.Presenter presenter;

    public ProductSelectionAdapter(Context context, List<ProductSelection> productSelections, ProductSelectionContract.Presenter presenter) {
        this.context = context;
        this.presenter = presenter;

        headers = new ArrayList<>();
        items = new HashMap<>();

        for (ProductSelection productSelection : productSelections) {
            headers.add(productSelection.getHeader());
            items.put(productSelection.getHeader(), productSelection.getItems());
        }
    }

    @Override
    public int getGroupCount() {
        return headers.size();
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
    public ProductSelectionItem getChild(int groupPosition, int childPosition) {
        return items.get(headers.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        ProductSelectionHeader header = getGroup(groupPosition);
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.product_selection_header, null);
        }

        TextView productTypeText = convertView.findViewById(R.id.product_type_text);
        productTypeText.setTypeface(null, Typeface.BOLD);
        productTypeText.setText(header.getTitle());

        ImageView productTypeImage = convertView.findViewById(R.id.product_type_image);
        productTypeImage.setImageDrawable(header.getDrawable(context));

        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final ProductSelectionItem item = getChild(groupPosition, childPosition);

        if (convertView == null) {
            convertView = View.inflate(context, R.layout.product_selection_item, null);
        }

        final LinearLayout productTypeHeader = convertView.findViewById(R.id.product_type_header);
        productTypeHeader.setBackgroundColor(context.getColor(R.color.item_default));

        final TextView productType = convertView.findViewById(R.id.product_type);
        productType.setTextColor(context.getColor(R.color.item_font_default));

        final TextView productTypeQuantty = convertView.findViewById(R.id.product_type_quantity);
        productTypeQuantty.setTextColor(context.getColor(R.color.item_font_default));

        final ImageView arrowItem = convertView.findViewById(R.id.arrow);
        arrowItem.setImageDrawable(context.getResources().getDrawable(R.drawable.arrow_open_list, null));

        final LinearLayout quantitySelectionItem = convertView.findViewById(R.id.quantity_selection_item);
        final Spinner spinner = convertView.findViewById(R.id.spinner);
        HintSpinner<ProductSelectionItemQuantity> hintSpinner = new HintSpinner<>(
                spinner,
                new ProductSelectionItemQuantityAdapter(context, item),
                new HintSpinner.Callback<ProductSelectionItemQuantity>() {
                    @Override
                    public void onItemSelected(int position, ProductSelectionItemQuantity itemAtPosition) {
                        Integer quantitySelected = itemAtPosition.getQuantity();
                        itemAtPosition.select(quantitySelected);

                        formatSelectedChild(quantitySelected, item, productTypeHeader, productType, productTypeQuantty);
                        presenter.chooseQuantity(item, quantitySelected);
                        quantitySelectionItem.setVisibility(View.GONE);
                        arrowItem.setImageDrawable(context.getResources().getDrawable(R.drawable.arrow_open_white, null));
                    }
                }
        );
        hintSpinner.init();
        hintSpinner.selectHint();

        productTypeHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int openArrow = R.drawable.arrow_open_list;
                int closeArrow = R.drawable.arrow_close_list;

                if (item.isSelected()) {
                    openArrow = R.drawable.arrow_open_white;
                    closeArrow = R.drawable.arrow_close_white;
                }

                if (quantitySelectionItem.isShown()) {
                    quantitySelectionItem.setVisibility(View.GONE);
                    arrowItem.setImageDrawable(context.getResources().getDrawable(openArrow, null));
                    return;
                }
                quantitySelectionItem.setVisibility(View.VISIBLE);
                arrowItem.setImageDrawable(context.getResources().getDrawable(closeArrow, null));
            }
        });

        String title = item.getCategory() + COLON_SEPARATOR;
        productType.setText(title);

        if (item.isSelected()) {
            formatSelectedChild(item.getSelectedQuantity(), item, productTypeHeader, productType, productTypeQuantty);
            showSelectedWhiteArrows(arrowItem, quantitySelectionItem);

            return convertView;
        }

        if (item.getCount() > 1) {
            String available = item.getCount() + EMPTY_SPACE + context.getString(R.string.available_items);
            productTypeQuantty.setText(available);
            return convertView;
        }

        String available = item.getCount() + EMPTY_SPACE + context.getString(R.string.available_item);
        productTypeQuantty.setText(available);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private void showSelectedWhiteArrows(ImageView arrowItem, LinearLayout linearQuantity) {
        if (linearQuantity.isShown()) {
            arrowItem.setImageDrawable(context.getResources().getDrawable(R.drawable.arrow_close_white, null));
            return;
        }
        arrowItem.setImageDrawable(context.getResources().getDrawable(R.drawable.arrow_open_white, null));
    }

    private Integer formatSelectedChild(Integer quantitySelected, ProductSelectionItem item, LinearLayout productTypeHeader, TextView product, TextView quantity) {
        productTypeHeader.setBackgroundColor(context.getColor(R.color.item_orange));
        product.setTextColor(context.getColor(R.color.white));

        String format = context.getString(R.string.product_selection_summary_format,
                quantitySelected,
                item.getCount());

        quantity.setText(format);
        quantity.setTextColor(context.getColor(R.color.white));

        return quantitySelected;
    }
}
