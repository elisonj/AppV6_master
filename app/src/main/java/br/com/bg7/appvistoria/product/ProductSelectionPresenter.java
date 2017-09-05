package br.com.bg7.appvistoria.product;

import br.com.bg7.appvistoria.data.source.remote.ProductService;
import br.com.bg7.appvistoria.projectselection.vo.Category;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by: elison
 * Date: 2017-09-05
 */
public class ProductSelectionPresenter  implements  ProductSelectionContract.Presenter {

    private ProductService productService;
    private ProductSelectionContract.View productSelectionView;
    private Long projectId;
    private String address;

    ProductSelectionPresenter(ProductService productService, ProductSelectionContract.View productSelectionView, Long projectId, String address ) {
        this.productSelectionView = checkNotNull(productSelectionView);
        this.productService = checkNotNull(productService);
        this.projectId = projectId;
        this.address = address;
        this.productSelectionView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void chooseQuantity(Category category, int quantity) {

    }
}
