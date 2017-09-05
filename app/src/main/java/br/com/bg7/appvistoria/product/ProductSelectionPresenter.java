package br.com.bg7.appvistoria.product;

import br.com.bg7.appvistoria.data.source.remote.ProductService;

/**
 * Created by: elison
 * Date: 2017-09-05
 */
public class ProductSelectionPresenter  implements  ProductSelectionContract.Presenter {

    private ProductService productService;
    private ProductSelectionContract.View productSelectionView;

    ProductSelectionPresenter(ProductService productService, ProductSelectionContract.View productSelectionView ) {
        this.productSelectionView = productSelectionView;
        this.productService = productService;
    }

    @Override
    public void start() {

    }
}
