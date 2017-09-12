package br.com.bg7.appvistoria.data.source.remote.stub;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Nullable;

import br.com.bg7.appvistoria.data.source.remote.ProductService;
import br.com.bg7.appvistoria.data.source.remote.http.HttpCallback;
import br.com.bg7.appvistoria.data.source.remote.http.HttpResponse;
import br.com.bg7.appvistoria.projectselection.vo.Project;

/**
 * Created by: elison
 * Date: 2017-09-05
 */
public class StubProductService implements ProductService {

    private Project project1 = new Project(1L, "Projeto 1");
    private Project project2 = new Project(2L, "Projeto 2");
    private Project project3 = new Project(3L, "Projeto 3");
    private Project project4 = new Project(4L, "Projeto 4");
    private Project project5 = new Project(5L, "Projeto 5");

    private List<Product> products = new ArrayList<>();

    @Override
    public void findByProjectAndAddress(Project project, String address, HttpCallback<List<Product>> callback) {

        List<Project>  projects = getDataProducts();
        products = new ArrayList<>();

        if(projects.contains(project)) {
            products = projects.get(projects.indexOf(project)).getProducts();
        }

        callback.onResponse(new HttpResponse<List<Product>>() {
            @Override
            public boolean isSuccessful() {
                return true;
            }

            @Nullable
            @Override
            public List<Product> body() {
                return products;
            }

            @Override
            public int code() {
                return 200;
            }
        });

    }

    private List<Project>  getDataProducts() {
        HashMap<Project, List<Product>> products = new HashMap<>();

        List<Project> list = new ArrayList<>();

        Category category1 = new Category(1L, "Carros e Motos");
        Category category2 = new Category(2L, "Caminhões e Ônibus");
        Category category3 = new Category(3L, "Enbarcações e Aeronaves");
        Category category4 = new Category(4L, "Imóveis");
        Category category5 = new Category(5L, "Industrial, Máquinas e Equipamentos");

        Product product1 = new Product(1L, "Carros", category1);
        Product product2 = new Product(2L, "Motos", category1);
        Product product3 = new Product(3L, "Partes e Peças", category1);
        Product product5 = new Product(5L, "Casas", category4);
        Product product6 = new Product(6L, "Caminhões", category2);
        Product product7 = new Product(7L, "Embarcações", category3);
        Product product8 = new Product(8L, "Industrial", category5);
        Product product9 = new Product(9L, "Apartamentos", category4);
        Product product10 = new Product(10L, "Aeroplano", category3);
        Product product11 = new Product(11L, "Maquinas", category5);

        project1.addProduct(product1);
        project1.addProduct(product3);
        project1.addProduct(product5);
        project2.addProduct(product2);
        project2.addProduct(product6);
        project3.addProduct(product7);
        project3.addProduct(product8);
        project3.addProduct(product9);
        project4.addProduct(product10);
        project4.addProduct(product11);

        list.add(project1);
        list.add(project2);
        list.add(project3);
        list.add(project4);
        list.add(project5);

        return list;


    }
}
