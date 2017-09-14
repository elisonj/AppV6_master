package br.com.bg7.appvistoria.productselection;

import java.util.HashMap;
import java.util.List;

import br.com.bg7.appvistoria.data.WorkOrder;
import br.com.bg7.appvistoria.data.source.local.WorkOrderRepository;
import br.com.bg7.appvistoria.data.source.remote.ProductService;
import br.com.bg7.appvistoria.data.source.remote.http.HttpCallback;
import br.com.bg7.appvistoria.data.source.remote.http.HttpResponse;
import br.com.bg7.appvistoria.productselection.vo.Product;
import br.com.bg7.appvistoria.productselection.vo.ProductSelection;
import br.com.bg7.appvistoria.productselection.vo.ProductSelectionItem;
import br.com.bg7.appvistoria.projectselection.vo.Project;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by: elison
 * Date: 2017-09-05
 */
public class ProductSelectionPresenter implements ProductSelectionContract.Presenter {

    private ProductService productService;
    private final WorkOrderRepository workOrderRepository;

    private ProductSelectionContract.View productSelectionView;
    private Project project;
    private String address;
    private List<ProductSelection> productSelections;
    private HashMap<ProductSelectionItem, Integer> selectedItems = new HashMap<>();

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
    public boolean isItemSelected(ProductSelectionItem item) {
        return false;
    }

    @Override
    public void chooseQuantity(ProductSelectionItem item, int quantity) {
        selectedItems.put(item, quantity);

        productSelectionView.showSelectedQuantity(item, quantity);
    }

    @Override
    public void cancelClicked() {
        selectedItems.clear();
        productSelectionView.showProducts(productSelections);
    }

    @Override
    public void createWorkOrderClicked() {
        productSelectionView.showConfirmation();
    }

    @Override
    public void cancelCreateWorkOrderClicked() {
        productSelectionView.hideConfirmation();
    }

    @Override
    public void confirmCreateWorkOrderClicked() {
        // TODO: Criar de fato uma WorkOrder com os dados selecionados
        WorkOrder workOrder = new WorkOrder(project.getDescription(), address);

        List<WorkOrder> allOrderByAddress = workOrderRepository.findAllOrderByProjectAndAddress(workOrder.getName(), address);

        if (allOrderByAddress.size() == 0) {
            workOrderRepository.save(workOrder);
            productSelectionView.showWorkOrderScreen();
            return;
        }

        productSelectionView.showCannotDuplicateWorkorderError();
    }

    @Override
    public void backClicked() {
        productSelectionView.showProjectSelection(project, address);
    }
}
