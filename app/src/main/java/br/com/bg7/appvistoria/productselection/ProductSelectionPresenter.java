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
import br.com.bg7.appvistoria.projectselection.vo.Location;
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
    private Location location;
    private List<Product> products;
    private HashMap<ProductSelectionItem, Integer> selectedItems = new HashMap<>();

    ProductSelectionPresenter(Project project, Location location, ProductService productService, WorkOrderRepository workOrderRepository, ProductSelectionContract.View productSelectionView) {
        this.project = checkNotNull(project);
        this.location = checkNotNull(location);

        this.productService = checkNotNull(productService);
        this.workOrderRepository = checkNotNull(workOrderRepository);
        this.productSelectionView = checkNotNull(productSelectionView);

        this.productSelectionView.setPresenter(this);
    }

    @Override
    public void start() {

        productService.findByProjectAndLocation(project, location, new HttpCallback<List<Product>>() {
            @Override
            public void onResponse(HttpResponse<List<Product>> httpResponse) {
                products = httpResponse.body();
                productSelectionView.showProducts(products);
            }
            @Override
            public void onFailure(Throwable t) {
                productSelectionView.showConnectivityError();
            }
        });
    }

    @Override
    public void chooseQuantity(ProductSelectionItem item, int quantity) {
        selectedItems.put(item, quantity);

        productSelectionView.showButtons();
    }

    @Override
    public void cancelClicked() {
        selectedItems.clear();
        productSelectionView.hideButtons();
        productSelectionView.showProducts(products);
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
        WorkOrder workOrder = new WorkOrder(project.getDescription(), location.getAddress());

        List<WorkOrder> existingWorkOrders = workOrderRepository.findAllByProjectAndAddress(workOrder.getName(), location.getAddress());

        productSelectionView.hideConfirmation();

        if (existingWorkOrders.size() != 0) {
            // TODO: Perguntar ao usuario se quer adicionar na WO existente
            return;
        }

        workOrderRepository.save(workOrder);
        productSelectionView.showWorkOrderScreen();
    }

    @Override
    public void backClicked() {
        productSelectionView.showProjectSelection();
    }
}
