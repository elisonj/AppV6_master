package br.com.bg7.appvistoria.data.source.remote.retrofit;

import java.util.List;

import br.com.bg7.appvistoria.auth.Auth;
import br.com.bg7.appvistoria.data.source.remote.ProductService;
import br.com.bg7.appvistoria.data.source.remote.http.HttpCallback;
import br.com.bg7.appvistoria.projectselection.vo.Product;
import br.com.bg7.appvistoria.projectselection.vo.Project;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by: elison
 * Date: 2017-09-04
 */
public class RetrofitProductService implements br.com.bg7.appvistoria.data.source.remote.ProductService {

    private final static String KEY_TOKEN = "Bearer ";

    private final ProductService productService;

    public RetrofitProductService(String baseUrl) {

        this.productService = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ProductService.class);
    }

    @Override
    public void findByProjectAndAddress(Project project, String address, final HttpCallback<List<Product>> callback) {
        String token = KEY_TOKEN + Auth.token();

    }

}
