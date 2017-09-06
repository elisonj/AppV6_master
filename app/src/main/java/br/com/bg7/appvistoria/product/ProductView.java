package br.com.bg7.appvistoria.product;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.bg7.appvistoria.R;
import br.com.bg7.appvistoria.projectselection.vo.Category;
import br.com.bg7.appvistoria.projectselection.vo.Product;
import br.com.bg7.appvistoria.projectselection.vo.ProductSelection;

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
    HashMap<Category, List<KeyValueItemList>> listDataChild = new HashMap<>();
    private HashMap<Long, Drawable> drawableCategories = new HashMap<>();

    public ProductView(Context context) {
        super(context);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.activity_product, this);
        expListView = findViewById(R.id.listview);

        populateCategoriesDrawable();

    }

    private void populateCategoriesDrawable() {
        drawableCategories.put(1L, getContext().getDrawable(R.drawable.category_car));
        drawableCategories.put(2L, getContext().getDrawable(R.drawable.category_trucks));
        drawableCategories.put(3L, getContext().getDrawable(R.drawable.category_planes));
        drawableCategories.put(4L, getContext().getDrawable(R.drawable.category_build));
        drawableCategories.put(5L, getContext().getDrawable(R.drawable.category_heavy_machines));
    }

    @Override
    public void showProducts(List<ProductSelection> productSelectionList) {
        listDataHeader.clear();
        listDataChild.clear();;

        listAdapter = new ExpandableListAdapter(getContext(), productSelectionList);
        expListView.setAdapter(listAdapter);
    }

    @Override
    public void setPresenter(ProductSelectionContract.Presenter presenter) {
        productSelectionPresenter = checkNotNull(presenter);
    }

    private class ExpandableListAdapter extends BaseExpandableListAdapter {

        private Context context;

        ExpandableListAdapter(Context context, List<ProductSelection>  productSelections ) {
            this.context = context;

            listDataHeader = new ArrayList<Category>();
            listDataChild = new HashMap<Category, List<KeyValueItemList>>();

            extractProductSelectionsToAdapter(productSelections);
        }

        private void extractProductSelectionsToAdapter(List<ProductSelection> productSelections) {

            for(ProductSelection  productSelection: productSelections) {
                listDataHeader.add(productSelection.getCategory());

                List<KeyValueItemList> list = new ArrayList<>();

                for(Map.Entry<Product, Integer> entry : productSelection.getProducts().entrySet()) {
                    KeyValueItemList item = new KeyValueItemList(entry.getKey(), entry.getValue());
                    list.add(item);
                }

                listDataChild.put(productSelection.getCategory(), list);
            }

        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            List<KeyValueItemList> keyValueItemLists = listDataChild.get(listDataHeader.get(groupPosition));
            return keyValueItemLists.get(childPosition);
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public View getChildView(int groupPosition, final int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {

            final KeyValueItemList childText = (KeyValueItemList) getChild(groupPosition, childPosition);

            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this.context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.product_list_item, null);
            }

            TextView txtListChild = convertView.findViewById(R.id.product);
            TextView quantity = convertView.findViewById(R.id.quantity);

            String title = childText.getProduct().getType() + KEY_COLON;

            if(childText.getQuantity() > 1) {
                txtListChild.setText(title);
                String available = childText.getQuantity() + EMPTY_SPACE + getContext().getString(R.string.available_items);
                quantity.setText(available);
                return convertView;
            }

            txtListChild.setText(title);
            String available = childText.getQuantity() + EMPTY_SPACE + getContext().getString(R.string.available_item);
            quantity.setText(available);
            return convertView;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return listDataChild.get(listDataHeader.get(groupPosition))
                    .size();
        }

        @Override
        public Object getGroup(int groupPosition) {
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
            Category headerTitle = (Category) getGroup(groupPosition);
            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this.context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.product_list_category, null);
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


    private class KeyValueItemList {
        private Product product;
        private Integer quantity;

        KeyValueItemList(Product product, Integer quantity) {
            this.product = product;
            this.quantity = quantity;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public Product getProduct() {
            return product;
        }
    }

}
