package br.com.bg7.appvistoria.data.source.remote.stub;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import br.com.bg7.appvistoria.data.WorkOrderCategory;
import br.com.bg7.appvistoria.data.WorkOrderProduct;
import br.com.bg7.appvistoria.data.WorkOrderProductType;
import br.com.bg7.appvistoria.data.source.remote.ProductService;
import br.com.bg7.appvistoria.data.source.remote.http.HttpCallback;
import br.com.bg7.appvistoria.data.source.remote.http.HttpResponse;
import br.com.bg7.appvistoria.projectselection.vo.Location;
import br.com.bg7.appvistoria.projectselection.vo.Project;

/**
 * Created by: elison
 * Date: 2017-09-05
 */
public class StubProductService implements ProductService {

    private static final WorkOrderProductType CARROS_E_MOTOS = new WorkOrderProductType(10L, "Carros & Motos");
    private static final WorkOrderProductType CAMINHOES_E_ONIBUS = new WorkOrderProductType(11L, "Caminhões & Ônibus");
    private static final WorkOrderProductType IMOVEIS = new WorkOrderProductType(13L, "Imóveis");

    private static final WorkOrderCategory CARROS = new WorkOrderCategory("Carros", CARROS_E_MOTOS);
    private static final WorkOrderCategory MOTOS = new WorkOrderCategory("Motos", CARROS_E_MOTOS);

    private static final WorkOrderCategory CAMINHOES = new WorkOrderCategory("Caminhões", CAMINHOES_E_ONIBUS);
    private static final WorkOrderCategory ONIBUS = new WorkOrderCategory("Ônibus", CAMINHOES_E_ONIBUS);
    private static final WorkOrderCategory VANS = new WorkOrderCategory("Vans", CAMINHOES_E_ONIBUS);

    private static final WorkOrderCategory RESIDENCIAL = new WorkOrderCategory("Residencial", IMOVEIS);
    private static final WorkOrderCategory COMERCIAL = new WorkOrderCategory("Comercial", IMOVEIS);

    @Override
    public void findByProjectAndLocation(Project project, Location location, HttpCallback<List<WorkOrderProduct>> callback) {

        callback.onResponse(new HttpResponse<List<WorkOrderProduct>>() {
            @Override
            public boolean isSuccessful() {
                return true;
            }

            @Nullable
            @Override
            public List<WorkOrderProduct> body() {
                return new ArrayList<WorkOrderProduct>() {{
                    add(new WorkOrderProduct(CARROS));
                    add(new WorkOrderProduct(CARROS));
                    add(new WorkOrderProduct(CARROS));
                    add(new WorkOrderProduct(CARROS));
                    add(new WorkOrderProduct(MOTOS));
                    add(new WorkOrderProduct(CAMINHOES));
                    add(new WorkOrderProduct(CAMINHOES));
                    add(new WorkOrderProduct(VANS));
                    add(new WorkOrderProduct(ONIBUS));
                    add(new WorkOrderProduct(ONIBUS));
                    add(new WorkOrderProduct(COMERCIAL));
                    add(new WorkOrderProduct(COMERCIAL));
                    add(new WorkOrderProduct(COMERCIAL));
                    add(new WorkOrderProduct(COMERCIAL));
                    add(new WorkOrderProduct(COMERCIAL));
                    add(new WorkOrderProduct(COMERCIAL));
                    add(new WorkOrderProduct(RESIDENCIAL));
                    add(new WorkOrderProduct(RESIDENCIAL));
                    add(new WorkOrderProduct(RESIDENCIAL));
                    add(new WorkOrderProduct(RESIDENCIAL));
                    add(new WorkOrderProduct(RESIDENCIAL));
                }};
            }

            @Override
            public int code() {
                return 200;
            }
        });

    }
}
