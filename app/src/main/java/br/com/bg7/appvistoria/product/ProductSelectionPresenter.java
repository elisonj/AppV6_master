package br.com.bg7.appvistoria.product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    private ProductService productService;
    private ProductSelectionContract.View productSelectionView;
    private Project project;
    private String address;

    ProductSelectionPresenter(ProductService productService, ProductSelectionContract.View productSelectionView, Project project, String address ) {
        this.productSelectionView = checkNotNull(productSelectionView);
        this.productService = checkNotNull(productService);
        this.project = project;
        this.address = address;
        this.productSelectionView.setPresenter(this);
    }

    @Override
    public void start() {

        productService.findByProjectAndAddress(project, address,  new HttpCallback<List<Product>>() {
            @Override
            public void onResponse(HttpResponse<List<Product>> httpResponse) {
                if(httpResponse == null || httpResponse.body() == null) return;

                List<Product> products = httpResponse.body();

                List<ProductSelection> productSelections = new ArrayList<ProductSelection>();

                for(Product product: products) {

                    HashMap<Product, Integer> hashMap = new HashMap<Product, Integer>();
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
    public void chooseQuantity(Category category, int quantity) {

    }
}
