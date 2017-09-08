package br.com.bg7.appvistoria.product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.bg7.appvistoria.data.WorkOrder;
import br.com.bg7.appvistoria.data.source.local.WorkOrderRepository;
import br.com.bg7.appvistoria.data.source.remote.ProductService;
import br.com.bg7.appvistoria.data.source.remote.http.HttpCallback;
import br.com.bg7.appvistoria.data.source.remote.http.HttpResponse;
import br.com.bg7.appvistoria.projectselection.vo.Category;
import br.com.bg7.appvistoria.projectselection.vo.Product;
import br.com.bg7.appvistoria.projectselection.vo.ProductSelection;
import br.com.bg7.appvistoria.projectselection.vo.Project;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by: elison
 * Date: 2017-09-05
 */
public class ProductSelectionPresenter  implements  ProductSelectionContract.Presenter {

    private final String EMPTY_SPACE = " ";
    private final String SEPARATOR = ", ";
    private ProductService productService;
    private final WorkOrderRepository workOrderRepository;

    private ProductSelectionContract.View productSelectionView;
    private Project project;
    private String address;
    private List<ProductSelection> productSelections = new ArrayList<>();
    HashMap<Category, List<ProductSelectionItem>> itemsSelected = new HashMap<>();

    ProductSelectionPresenter(ProductService productService, ProductSelectionContract.View productSelectionView, Project project, String address, WorkOrderRepository workOrderRepository ) {
        this.productSelectionView = checkNotNull(productSelectionView);
        this.productService = checkNotNull(productService);
        this.project = project;
        this.address = address;
        this.workOrderRepository = workOrderRepository;
        this.productSelectionView.setPresenter(this);
    }

    @Override
    public void start() {

        productService.findByProjectAndAddress(project, address,  new HttpCallback<List<Product>>() {
            @Override
            public void onResponse(HttpResponse<List<Product>> httpResponse) {
                if(httpResponse == null || httpResponse.body() == null) return;

                itemsSelected.clear();

                List<Product> products = httpResponse.body();

                if(products == null) return;

                productSelections = new ArrayList<>();

                for(Product product: products) {

                    HashMap<Product, Integer> hashMap = new HashMap<>();
                    hashMap.put(product, 1);

                    ProductSelection productSelection = new ProductSelection(product.getCategory(), hashMap);
                    productSelections.add(productSelection);
                }

                productSelectionView.showProducts(productSelections);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    @Override
    public boolean isProductSelected(Product product, Category category) {
        List<ProductSelectionItem> productSelectionItems = itemsSelected.get(category);
        if(productSelectionItems == null) return false;

        for(ProductSelectionItem item: productSelectionItems) {
            if (item.getProduct().getId().equals(product.getId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void chooseQuantity(Category category, Product product, int quantity) {

        List<ProductSelectionItem> products = itemsSelected.get(category);

        if(products == null) {
            products = new ArrayList<>();
        }
        ProductSelectionItem productSelectionItem = new ProductSelectionItem(product, quantity);

        productSelectionItem.setQuantity(quantity);
        products.add(productSelectionItem);

        itemsSelected.put(category, products);

        productSelectionView.showSelectedQuantity(category, product, quantity);
    }

    @Override
    public void cancelClicked() {
        productSelectionView.showProducts(productSelections);
    }

    @Override
    public void createWorkOrderClicked() {

        String summary = "";
        WorkOrder workOrder = new WorkOrder(project.getDescription(), summary, address);

        for(Map.Entry<Category, List<ProductSelectionItem>> entry : itemsSelected.entrySet()) {
            Category category = entry.getKey();
            List<ProductSelectionItem> products = entry.getValue();

            summary = getSummaryByProductSelection(products);
        }

        workOrder.setSummary(summary);

        workOrderRepository.save(workOrder);

        productSelectionView.showWorkOrderScreen();
    }

    private String getSummaryByProductSelection(List<ProductSelectionItem> products) {
        String summary = "";

        for(ProductSelectionItem item : products) {
            summary += item.getQuantity() + EMPTY_SPACE + item.getProduct().getType() + SEPARATOR;
        }
        if(products.size() > 0) {
            summary = summary.substring(0, summary.length() - 2);
        }

        return summary;
    }

    @Override
    public void cancelCreateWorkOrderClicked() {
        productSelectionView.hideConfirmation();
    }

    @Override
    public void confirmCreateWorkOrderClicked() {
        productSelectionView.showConfirmation();
    }

    @Override
    public void backClicked() {
        productSelectionView.showProjectSelection(project, address);
    }
}
