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
import me.srodrigo.androidhintspinner.HintAdapter;
import me.srodrigo.androidhintspinner.HintSpinner;

/**
 * Created by: luciolucio
 * Date: 2017-09-13
 */
public class ProductSelectionAdapter extends BaseExpandableListAdapter {

    private ArrayList<ProductSelectionHeader> productTypes = new ArrayList<>();
    private HashMap<ProductSelectionHeader, List<ProductSelectionItem>> categories;
    private Context context;
    private ProductSelectionContract.Presenter presenter;
    private HashMap<ProductSelectionItem, ViewItems> viewItems = new HashMap<>();

    public ProductSelectionAdapter(Context context, List<ProductSelection> productSelections, ProductSelectionContract.Presenter presenter) {
        this.context = context;
        this.presenter = presenter;

        productTypes = new ArrayList<>();
        categories = new HashMap<>();

        for (ProductSelection productSelection : productSelections) {
            productTypes.add(productSelection.getProductType());
            categories.put(productSelection.getProductType(), productSelection.getCategories());
        }
    }

    @Override
    public int getGroupCount() {
        return productTypes.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.categories.get(this.productTypes.get(groupPosition)).size();
    }

    @Override
    public ProductSelectionHeader getGroup(int groupPosition) {
        return productTypes.get(groupPosition);
    }

    @Override
    public ProductSelectionItem getChild(int groupPosition, int childPosition) {
        return categories.get(productTypes.get(groupPosition)).get(childPosition);
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
            convertView = View.inflate(context, R.layout.product_selection_product_type, null);
        }

        TextView productTypeText = convertView.findViewById(R.id.product_type_text);
        productTypeText.setTypeface(null, Typeface.BOLD);
        productTypeText.setText(header.getTitle());

        ImageView productTypeImage = convertView.findViewById(R.id.product_type_image);
        productTypeImage.setImageDrawable(header.getImage(context));

        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final ProductSelectionItem item = getChild(groupPosition, childPosition);

        ViewItems viewItems = getViewItems(convertView, item);
        LinearLayout categoryHeader = viewItems.categoryHeader;
        final LinearLayout quantitySelectionItem = viewItems.quantitySelectionItem;
        final ImageView arrowItem = viewItems.arrowItem;
        TextView category = viewItems.category;
        TextView categoryQuantity = viewItems.categoryQuantity;
        convertView = viewItems.convertView;

        configureCategoryOnClick(item, categoryHeader, viewItems);

        String categoryHeaderText = context.getString(R.string.product_selection_category_header_format, item.getCategory());
        category.setText(categoryHeaderText);

        if (item.isSelected()) {
            formatSelectedChild(item.getSelectedQuantity(), item, categoryHeader, category, categoryQuantity);
            showSelectedWhiteArrows(arrowItem, quantitySelectionItem);

            return convertView;
        }

        formatAvailableCategories(item, categoryQuantity);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private void showSelectedWhiteArrows(ImageView arrowItem, LinearLayout linearQuantity) {
        if (linearQuantity.isShown()) {
            arrowItem.setImageDrawable(context.getResources().getDrawable(R.drawable.arrow_close_white, null));
        }

        if (!linearQuantity.isShown()) {
            arrowItem.setImageDrawable(context.getResources().getDrawable(R.drawable.arrow_open_white, null));
        }
    }

    private void formatAvailableCategories(ProductSelectionItem item, TextView categoryQuantity) {
        String availableItems = context.getString(R.string.product_selection_available_items_single_format, item.getCount());

        if (item.getCount() > 1) {
            availableItems = context.getString(R.string.product_selection_available_items_multiple_format, item.getCount());
        }

        categoryQuantity.setText(availableItems);
    }

    private void configureCategoryOnClick(final ProductSelectionItem item, LinearLayout categoryHeader, final ViewItems viewItems) {
        categoryHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int openArrow = R.drawable.arrow_open_list;
                int closeArrow = R.drawable.arrow_close_list;

                if (item.isSelected()) {
                    openArrow = R.drawable.arrow_open_white;
                    closeArrow = R.drawable.arrow_close_white;
                }

                if (viewItems.quantitySelectionItem.isShown()) {
                    viewItems.quantitySelectionItem.setVisibility(View.GONE);
                    viewItems.arrowItem.setImageDrawable(context.getResources().getDrawable(openArrow, null));
                    return;
                }

                viewItems.quantitySelectionItem.setVisibility(View.VISIBLE);
                viewItems.arrowItem.setImageDrawable(context.getResources().getDrawable(closeArrow, null));
            }
        });
    }

    private ViewItems getViewItems(View convertView, ProductSelectionItem item) {
        if (viewItems.containsKey(item)) {
            return viewItems.get(item);
        }

        viewItems.put(item, new ViewItems(convertView, item).invoke());
        return viewItems.get(item);
    }

    private Integer formatSelectedChild(Integer quantitySelected, ProductSelectionItem item, LinearLayout categoryHeader, TextView product, TextView quantity) {
        categoryHeader.setBackgroundColor(context.getColor(R.color.item_orange));
        product.setTextColor(context.getColor(R.color.white));

        String format = context.getString(R.string.product_selection_summary_format,
                quantitySelected,
                item.getCount());

        quantity.setText(format);
        quantity.setTextColor(context.getColor(R.color.white));

        return quantitySelected;
    }

    private class ViewItems {
        private final ProductSelectionItem item;
        private View convertView;
        private LinearLayout categoryHeader;
        private TextView category;
        private TextView categoryQuantity;
        private ImageView arrowItem;
        private LinearLayout quantitySelectionItem;
        private HintSpinner<ProductSelectionItemQuantity> spinner;

        ViewItems(View convertView, ProductSelectionItem item) {
            this.convertView = convertView;
            this.item = item;
        }

        ViewItems invoke() {
            if (convertView == null) {
                convertView = View.inflate(context, R.layout.product_selection_category, null);
            }

            categoryHeader = convertView.findViewById(R.id.category_header);
            categoryHeader.setBackgroundColor(context.getColor(R.color.item_default));

            category = convertView.findViewById(R.id.category);
            category.setTextColor(context.getColor(R.color.item_font_default));

            categoryQuantity = convertView.findViewById(R.id.category_quantity);
            categoryQuantity.setTextColor(context.getColor(R.color.item_font_default));

            arrowItem = convertView.findViewById(R.id.arrow);
            arrowItem.setImageDrawable(context.getResources().getDrawable(R.drawable.arrow_open_list, null));

            quantitySelectionItem = convertView.findViewById(R.id.quantity_selection_item);

            configureSpinner();

            return this;
        }

        private void configureSpinner() {
            final Spinner spinner = convertView.findViewById(R.id.spinner);

            this.spinner = new HintSpinner<>(
                    spinner,
                    new HintAdapter<>(context, context.getString(R.string.product_selection_select_quantity), item.getQuantities()),
                    new HintSpinner.Callback<ProductSelectionItemQuantity>() {
                        @Override
                        public void onItemSelected(int position, ProductSelectionItemQuantity itemAtPosition) {
                            Integer quantitySelected = itemAtPosition.getQuantity();
                            itemAtPosition.select(quantitySelected);

                            formatSelectedChild(quantitySelected, item, categoryHeader, category, categoryQuantity);
                            presenter.chooseQuantity(item, quantitySelected);
                            quantitySelectionItem.setVisibility(View.GONE);
                            arrowItem.setImageDrawable(context.getResources().getDrawable(R.drawable.arrow_open_white, null));
                        }
                    }
            );
            this.spinner.init();
            this.spinner.selectHint();
        }
    }
}
