package br.com.bg7.appvistoria.data.source.remote.stub;

import java.util.ArrayList;
import java.util.List;

import br.com.bg7.appvistoria.data.source.remote.ProjectService;
import br.com.bg7.appvistoria.projectselection.vo.Category;
import br.com.bg7.appvistoria.projectselection.vo.Product;
import br.com.bg7.appvistoria.projectselection.vo.Project;

/**
 * Created by: elison
 * Date: 2017-08-31
 */
public class StubProjectService implements ProjectService {
    @Override
    public List<Project> findByIdOrDescription(String idOrDescription) {

        List<Project> listReturn = new ArrayList<>();
        List<Project> list = getData();

        for(Project project: list) {
            if(String.valueOf(project.getId()).equals(idOrDescription)) {
                 listReturn.add(project);
                continue;
            }
            if(project.getDescription().equals(idOrDescription)) {
                listReturn.add(project);
            }
        }

        return listReturn;
    }

    @Override
    public List<String> findAddressesForProject(Project project) {
        return null;
    }

    private List<Project> getData() {
        List<Project> list = new ArrayList<>();

        Project project1 = new Project(1L, "Projeto 1");
        Project project2 = new Project(2L, "Projeto 2");
        Project project3 = new Project(3L, "Projeto 3");
        Project project4 = new Project(4L, "Projeto 4");
        Project project5 = new Project(5L, "Projeto 5");

        Category category1 = new Category(1L, "Carros e Motos");
        Category category2 = new Category(2L, "Caminhões e Ônibus");
        Category category3 = new Category(3L, "Enbarcações e Aeronaves");
        Category category4 = new Category(4L, "Imóveis");
        Category category5 = new Category(5L, "Industrial, Máquinas e Equipamentos");

        Product product1 = new Product(1L, "Uno", category1);
        Product product2 = new Product(2L, "Pálio", category1);
        Product product3 = new Product(3L, "Gol", category1);
        Product product4 = new Product(4L, "Suzuki", category1);
        Product product5 = new Product(5L, "4 quartos", category4);
        Product product6 = new Product(6L, "Scania", category2);
        Product product7 = new Product(7L, "Iate", category3);
        Product product8 = new Product(8L, "Plaina industrial", category5);


        Product product9 = new Product(9L, "Vectra", category1);
        Product product10 = new Product(10L, "Corsa", category1);
        Product product11 = new Product(11L, "Saveiro", category1);
        Product product12 = new Product(12L, "Yamaha", category1);
        Product product13 = new Product(13L, "Apartamento 2 quartos", category4);
        Product product14 = new Product(14L, "Mercedes", category2);
        Product product15 = new Product(15L, "Aeroplano", category3);
        Product product16 = new Product(16L, "Serra circular", category5);

        project1.addProduct(product1);
        project1.addProduct(product3);
        project1.addProduct(product5);
        project2.addProduct(product2);
        project2.addProduct(product4);
        project2.addProduct(product6);
        project3.addProduct(product7);
        project3.addProduct(product8);
        project3.addProduct(product9);
        project4.addProduct(product10);
        project4.addProduct(product11);
        project4.addProduct(product12);
        project5.addProduct(product13);
        project5.addProduct(product14);
        project5.addProduct(product15);
        project1.addProduct(product16);

        list.add(project1);
        list.add(project2);
        list.add(project3);
        list.add(project4);
        list.add(project5);

        return list;

    }

}
