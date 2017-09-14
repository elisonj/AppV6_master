package br.com.bg7.appvistoria.data.source.remote.stub;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Nullable;

import br.com.bg7.appvistoria.data.source.remote.ProductService;
import br.com.bg7.appvistoria.data.source.remote.http.HttpCallback;
import br.com.bg7.appvistoria.data.source.remote.http.HttpResponse;
import br.com.bg7.appvistoria.productselection.vo.Category;
import br.com.bg7.appvistoria.productselection.vo.Product;
import br.com.bg7.appvistoria.projectselection.vo.Project;

/**
 * Created by: elison
 * Date: 2017-09-05
 */
public class StubProductService implements ProductService {

    private Category carros = new Category(1L, "Carros");
    private Category motos = new Category(2L, "Motos");

    private Product product1 = new Product(1L, 17L, "Carros & Motos", carros);
    private Product product2 = new Product(2L, 17L, "Carros & Motos", motos);

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
