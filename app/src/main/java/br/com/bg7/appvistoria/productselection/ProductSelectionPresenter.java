package br.com.bg7.appvistoria.productselection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import br.com.bg7.appvistoria.data.WorkOrder;
import br.com.bg7.appvistoria.data.WorkOrderProduct;
import br.com.bg7.appvistoria.data.source.local.WorkOrderRepository;
import br.com.bg7.appvistoria.data.source.remote.ProductService;
import br.com.bg7.appvistoria.data.source.remote.http.HttpCallback;
import br.com.bg7.appvistoria.data.source.remote.http.HttpResponse;
import br.com.bg7.appvistoria.productselection.vo.ProductSelectionItem;
import br.com.bg7.appvistoria.projectselection.vo.Location;
import br.com.bg7.appvistoria.projectselection.vo.Project;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by: elison
 * Date: 2017-09-05
 */
public class ProductSelectionPresenter implements ProductSelectionContract.Presenter {

    private static final Logger LOG = LoggerFactory.getLogger(ProductSelectionPresenter.class);

    private ProductService productService;
    private final WorkOrderRepository workOrderRepository;

    private ProductSelectionContract.View productSelectionView;
    private Project project;
    private Location location;
    private List<WorkOrderProduct> products;
    private HashSet<ProductSelectionItem> selectedItems = new HashSet<>();

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

        productService.findByProjectAndLocation(project, location, new HttpCallback<List<WorkOrderProduct>>() {
            @Override
            public void onResponse(HttpResponse<List<WorkOrderProduct>> httpResponse) {
                products = httpResponse.body();
                productSelectionView.showProducts(products);
            }
            @Override
            public void onFailure(Throwable t) {
                LOG.error("Falha ao buscar produtos por projeto e localidade", t);
                productSelectionView.showConnectivityError();
            }
        });
    }

    @Override
    public void quantitySelected(ProductSelectionItem item, Integer quantity) {
        item.select(quantity);
        selectedItems.add(item);

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
        WorkOrder workOrder = new WorkOrder(project, location);

        for (ProductSelectionItem item : selectedItems) {
            workOrder.addProducts(item.getSelectedProducts(workOrder));
        }

        workOrderRepository.save(workOrder);

        // TODO: Adicionar na existente

        productSelectionView.hideConfirmation();
        productSelectionView.showWorkOrderScreen();
    }

    @Override
    public void backClicked() {
        productSelectionView.showProjectSelection();
    }
}
