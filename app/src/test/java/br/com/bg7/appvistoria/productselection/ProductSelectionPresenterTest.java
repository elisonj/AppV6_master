package br.com.bg7.appvistoria.productselection;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import br.com.bg7.appvistoria.data.source.local.fake.FakeWorkOrderRepository;
import br.com.bg7.appvistoria.data.source.remote.ProductService;
import br.com.bg7.appvistoria.data.source.remote.http.HttpCallback;
import br.com.bg7.appvistoria.data.source.remote.http.HttpResponse;
import br.com.bg7.appvistoria.productselection.vo.ProductSelection;
import br.com.bg7.appvistoria.projectselection.vo.Project;
import br.com.bg7.appvistoria.workorder.InProgressWorkOrder;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by: elison
 * Date: 2017-09-11
 */
public class ProductSelectionPresenterTest {

    private final int QUANTITY_SELECTED = 2;

    private Category category1 = new Category(1L, "Carros e Motos");
    private Category category2 = new Category(2L, "Caminhões e Ônibus");
    private Category category3 = new Category(3L, "Enbarcações e Aeronaves");
    private Product product1 = new Product(1L, "Carros", category1);

    private ArrayList<Product> allProducts = new ArrayList<Product>() {{
        add(new Product(1L, "Carros", category1));
        add(new Product(2L, "Motos", category1));
        add(new Product(3L, "Partes e Peças", category1));
        add(new Product(4L, "Caminhões", category2));
        add(new Product(5L, "Embarcações", category3));
        add(new Product(6L, "Aeroplano", category3));
    }};

    private Project project = new Project(1L, "Projeto 1");
    private String address = "Endereço 1";

    private FakeWorkOrderRepository workOrderRepository = new FakeWorkOrderRepository();

    @Mock
    ProductSelectionContract.View productView;

    @Mock
    ProductService productService;

    @Mock
    HttpResponse<List<Product>> productResponse;

    @Captor
    ArgumentCaptor<HttpCallback<List<Product>>> productCallBackCaptor;

    private ProductSelectionPresenter productSelectionPresenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        workOrderRepository.save(new InProgressWorkOrder("Projeto 1", "Resumo completo", ""));

        productSelectionPresenter = new ProductSelectionPresenter(project, address, productService, workOrderRepository, productView);

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
        new ProductSelectionPresenter(project, address, null, workOrderRepository, productView);
    }

    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullView() {
        new ProductSelectionPresenter(project, address, productService, workOrderRepository, null);
    }

    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullProject() {
        new ProductSelectionPresenter(null, address, productService, workOrderRepository, productView);
    }

    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullAddress() {
        new ProductSelectionPresenter(project, null, productService, workOrderRepository, productView);
    }

    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullRepository() {
        new ProductSelectionPresenter(project, address, productService, null, productView);
    }

    @Test
    public void shouldShowListProductsWhenStart() {

        verify(productService).findByProjectAndAddress(eq(project), eq(address), productCallBackCaptor.capture());
        productCallBackCaptor.getValue().onResponse(productResponse);

        verify(productView).showProducts(ArgumentMatchers.<List<ProductSelection>>any());
    }

    @Test
    public void shouldSHowNoConnectionErrorWhenIsNotSuccessful() {
        when(productResponse.isSuccessful()).thenReturn(false);

        verify(productService).findByProjectAndAddress(eq(project), eq(address), productCallBackCaptor.capture());
        productCallBackCaptor.getValue().onFailure(new Throwable());

        verify(productView).showConnectivityError();
    }

    @Test
    public void shouldShowSelectedQuantityWhenChooseQuantity() {
        productSelectionPresenter.chooseQuantity(category1, product1, QUANTITY_SELECTED);

        verify(productView).showSelectedQuantity(category1, product1, QUANTITY_SELECTED);
    }

    @Test
    public void shouldShowProjectSelectionWhenBackIsClicked() {
        productSelectionPresenter.backClicked();
        verify(productView).showProjectSelection(eq(project), eq(address));
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

    @Test
    public void shoulShowCannotDuplicateWorkorderErrorWhenProjectAndAddressExists() {
        productSelectionPresenter.createWorkOrderClicked();

        verify(productView).showCannotDuplicateWorkorderError();
    }
}
