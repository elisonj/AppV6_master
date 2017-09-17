package br.com.bg7.appvistoria.productselection;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import br.com.bg7.appvistoria.data.WorkOrderCategory;
import br.com.bg7.appvistoria.data.WorkOrderProduct;
import br.com.bg7.appvistoria.data.WorkOrderProductType;
import br.com.bg7.appvistoria.data.source.local.fake.FakeWorkOrderRepository;
import br.com.bg7.appvistoria.data.source.remote.ProductService;
import br.com.bg7.appvistoria.data.source.remote.http.HttpCallback;
import br.com.bg7.appvistoria.data.source.remote.http.HttpResponse;
import br.com.bg7.appvistoria.projectselection.vo.Location;
import br.com.bg7.appvistoria.projectselection.vo.Project;
import br.com.bg7.appvistoria.workorder.InProgressWorkOrder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by: elison
 * Date: 2017-09-11
 */
@Ignore("Necessario corrigir o presenter primeiro")
public class ProductSelectionPresenterTest {

    private static final WorkOrderProductType CARROS_E_MOTOS = new WorkOrderProductType(17L, "Carros & Motos");
    private static final WorkOrderCategory CARROS = new WorkOrderCategory("Carros", CARROS_E_MOTOS);

    private ArrayList<WorkOrderProduct> allProducts = new ArrayList<WorkOrderProduct>() {{
        add(new WorkOrderProduct(CARROS));
    }};

    private Project project = new Project(1L, "Projeto 1");
    private Location location = new Location(1L, "Endereço 1");

    private FakeWorkOrderRepository workOrderRepository = new FakeWorkOrderRepository();

    @Mock
    ProductSelectionContract.View productView;

    @Mock
    ProductService productService;

    @Mock
    HttpResponse<List<WorkOrderProduct>> productResponse;

    @Captor
    ArgumentCaptor<HttpCallback<List<WorkOrderProduct>>> productCallBackCaptor;

    private ProductSelectionPresenter productSelectionPresenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        workOrderRepository.save(new InProgressWorkOrder("Projeto 1", "Resumo completo", ""));

        productSelectionPresenter = new ProductSelectionPresenter(project, location, productService, workOrderRepository, productView);

        when(productResponse.body()).thenReturn(allProducts);

        productSelectionPresenter.start();
    }

    @Test
    public void shouldInitializePresenter()
    {
        verify(productView).setPresenter(productSelectionPresenter);
    }


    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullProductService() {
        new ProductSelectionPresenter(project, location, null, workOrderRepository, productView);
    }

    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullView() {
        new ProductSelectionPresenter(project, location, productService, workOrderRepository, null);
    }

    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullProject() {
        new ProductSelectionPresenter(null, location, productService, workOrderRepository, productView);
    }

    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullLocation() {
        new ProductSelectionPresenter(project, null, productService, workOrderRepository, productView);
    }

    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullRepository() {
        new ProductSelectionPresenter(project, location, productService, null, productView);
    }

    @Test
    public void shouldShowListProductsWhenStart() {

        verify(productService).findByProjectAndLocation(eq(project), eq(location), productCallBackCaptor.capture());
        productCallBackCaptor.getValue().onResponse(productResponse);

        verify(productView).showProducts(any(List.class));
    }

    @Test
    public void shouldSHowNoConnectionErrorWhenIsNotSuccessful() {
        when(productResponse.isSuccessful()).thenReturn(false);

        verify(productService).findByProjectAndLocation(eq(project), eq(location), productCallBackCaptor.capture());
        productCallBackCaptor.getValue().onFailure(new Throwable());

        verify(productView).showConnectivityError();
    }

    @Test
    public void shouldShowProjectSelectionWhenBackIsClicked() {
        productSelectionPresenter.backClicked();
        verify(productView).showProjectSelection();
    }

    @Test
    public  void shouldShowConfirmationWhenConfirmCreateWorkOrder () {
        productSelectionPresenter.confirmCreateWorkOrderClicked();
        verify(productView).showConfirmation();
    }
    @Test
    public  void shouldHideConfirmationWhenCancelCreateWorkOrder () {
        productSelectionPresenter.cancelCreateWorkOrderClicked();
        verify(productView).hideConfirmation();
    }

    @Test
    public void shoulShowWorkOrderScreenWhenCreateWorkOrder() {
        workOrderRepository.clear();
        productSelectionPresenter.createWorkOrderClicked();
        verify(productView).showWorkOrderScreen();
    }
}
