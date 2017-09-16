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
import br.com.bg7.appvistoria.projectselection.vo.Location;
import br.com.bg7.appvistoria.projectselection.vo.Project;

/**
 * Created by: elison
 * Date: 2017-09-05
 */
public class StubProductService implements ProductService {

    private static final ProductType CARROS_E_MOTOS = new ProductType(10L, "Carros & Motos");
    private static final ProductType CAMINHOES_E_ONIBUS = new ProductType(11L, "Caminhões & Ônibus");
    private static final ProductType IMOVEIS = new ProductType(13L, "Imóveis");

    private static final Category CARROS = new Category("Carros", CARROS_E_MOTOS);
    private static final Category MOTOS = new Category("Motos", CARROS_E_MOTOS);

    private static final Category CAMINHOES = new Category("Caminhões", CAMINHOES_E_ONIBUS);
    private static final Category ONIBUS = new Category("Ônibus", CAMINHOES_E_ONIBUS);
    private static final Category VANS = new Category("Vans", CAMINHOES_E_ONIBUS);

    private static final Category RESIDENCIAL = new Category("Residencial", IMOVEIS);
    private static final Category COMERCIAL = new Category("Comercial", IMOVEIS);

    @Override
    public void findByProjectAndLocation(Project project, Location location, HttpCallback<List<Product>> callback) {

        callback.onResponse(new HttpResponse<List<Product>>() {
            @Override
            public boolean isSuccessful() {
                return true;
            }

            @Nullable
            @Override
            public List<Product> body() {
                return new ArrayList<Product>() {{
                    add(new Product(1L, CARROS));
                    add(new Product(2L, CARROS));
                    add(new Product(3L, CARROS));
                    add(new Product(4L, CARROS));
                    add(new Product(5L, MOTOS));
                    add(new Product(6L, CAMINHOES));
                    add(new Product(7L, CAMINHOES));
                    add(new Product(8L, VANS));
                    add(new Product(9L, ONIBUS));
                    add(new Product(10L, ONIBUS));
                    add(new Product(11L, COMERCIAL));
                    add(new Product(12L, COMERCIAL));
                    add(new Product(13L, COMERCIAL));
                    add(new Product(14L, COMERCIAL));
                    add(new Product(15L, COMERCIAL));
                    add(new Product(16L, COMERCIAL));
                    add(new Product(17L, RESIDENCIAL));
                    add(new Product(18L, RESIDENCIAL));
                    add(new Product(19L, RESIDENCIAL));
                    add(new Product(20L, RESIDENCIAL));
                    add(new Product(21L, RESIDENCIAL));
                }};
            }

            @Override
            public int code() {
                return 200;
            }
        });

    }
}
