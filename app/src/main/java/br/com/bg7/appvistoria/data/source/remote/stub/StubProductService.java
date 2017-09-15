package br.com.bg7.appvistoria.data.source.remote.stub;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import br.com.bg7.appvistoria.data.source.remote.ProductService;
import br.com.bg7.appvistoria.data.source.remote.http.HttpCallback;
import br.com.bg7.appvistoria.data.source.remote.http.HttpResponse;
import br.com.bg7.appvistoria.productselection.vo.Category;
import br.com.bg7.appvistoria.productselection.vo.Product;
import br.com.bg7.appvistoria.productselection.vo.ProductType;
import br.com.bg7.appvistoria.projectselection.vo.Project;

/**
 * Created by: elison
 * Date: 2017-09-05
 */
public class StubProductService implements ProductService {

    private static final ProductType CARROS_E_MOTOS = new ProductType(17L, "Carros & Motos");

    private static final Category CARROS = new Category("Carros", CARROS_E_MOTOS);
    private static final Category MOTOS = new Category("Motos", CARROS_E_MOTOS);

    private Product product1 = new Product(1L, CARROS);
    private Product product2 = new Product(2L, MOTOS);

    @Override
    public void findByProjectAndAddress(Project project, String address, HttpCallback<List<Product>> callback) {

        callback.onResponse(new HttpResponse<List<Product>>() {
            @Override
            public boolean isSuccessful() {
                return true;
            }

            @Nullable
            @Override
            public List<Product> body() {
                return new ArrayList<Product>() {{
                    add(product1);
                    add(product2);
                }};
            }

            @Override
            public int code() {
                return 200;
            }
        });

    }
}
