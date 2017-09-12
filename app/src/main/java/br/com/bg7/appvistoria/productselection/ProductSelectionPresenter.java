package br.com.bg7.appvistoria.productselection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.bg7.appvistoria.data.WorkOrder;
import br.com.bg7.appvistoria.data.source.local.WorkOrderRepository;
import br.com.bg7.appvistoria.data.source.remote.ProductService;
import br.com.bg7.appvistoria.data.source.remote.http.HttpCallback;
import br.com.bg7.appvistoria.data.source.remote.http.HttpResponse;
import br.com.bg7.appvistoria.productselection.vo.Category;
import br.com.bg7.appvistoria.productselection.vo.Product;
import br.com.bg7.appvistoria.productselection.vo.ProductSelection;
import br.com.bg7.appvistoria.projectselection.vo.Project;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by: elison
 * Date: 2017-09-05
 */
public class ProductSelectionPresenter implements ProductSelectionContract.Presenter {

    private final String EMPTY_SPACE = " ";
    private final String SEPARATOR = ", ";
    private ProductService productService;
    private final WorkOrderRepository workOrderRepository;

    private ProductSelectionContract.View productSelectionView;
    private Project project;
    private String address;
    private List<ProductSelection> productSelections;
    private HashMap<String, HashMap<String, Integer>> itemsSelected = new HashMap<>();

    ProductSelectionPresenter(Project project, String address, ProductService productService, WorkOrderRepository workOrderRepository, ProductSelectionContract.View productSelectionView) {
        this.project = checkNotNull(project);
        this.address = checkNotNull(address);

        this.productService = checkNotNull(productService);
        this.workOrderRepository = checkNotNull(workOrderRepository);
        this.productSelectionView = checkNotNull(productSelectionView);

        this.productSelectionView.setPresenter(this);
    }

    @Override
    public void start() {

        productService.findByProjectAndAddress(project, address, new HttpCallback<List<Product>>() {
            @Override
            public void onResponse(HttpResponse<List<Product>> httpResponse) {
                productSelections = ProductSelection.fromProducts(httpResponse.body());
                productSelectionView.showProducts(productSelections);
            }

            @Override
            public void onFailure(Throwable t) {
                productSelectionView.showConnectivityError();
            }
        });
    }

    @Override
    public boolean isProductSelected(Product product, Category category) {
        List<ProductSelectionItem> productSelectionItems = itemsSelected.get(category);
        if (productSelectionItems == null) return false;

        for (ProductSelectionItem item : productSelectionItems) {
            if (item.getProduct().getId().equals(product.getId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void chooseQuantity(Category category, Product product, int quantity) {

        List<ProductSelectionItem> products = itemsSelected.get(category);

        if (products == null) {
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
        itemsSelected.clear();
        productSelectionView.showProducts(productSelections);
    }

    @Override
    public void createWorkOrderClicked() {

        String summary = "";

        for (Map.Entry<Category, List<ProductSelectionItem>> entry : itemsSelected.entrySet()) {
            List<ProductSelectionItem> products = entry.getValue();

            summary = getSummaryByProductSelection(products);
        }

        WorkOrder workOrder = new WorkOrder(project.getDescription(), summary, address);

        List<WorkOrder> allOrderByAddress = workOrderRepository.findAllOrderByProjectAndAddress(workOrder.getName(), address);

        if (allOrderByAddress.size() == 0) {
            workOrderRepository.save(workOrder);
            productSelectionView.showWorkOrderScreen();
            return;
        }

        productSelectionView.showCannotDuplicateWorkorderError();
    }

    private String getSummaryByProductSelection(List<ProductSelectionItem> products) {
        String summary = "";

        for (ProductSelectionItem item : products) {
            summary += item.getQuantity() + EMPTY_SPACE + item.getProduct().getProductType() + SEPARATOR;
        }
        if (products.size() > 0) {
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
