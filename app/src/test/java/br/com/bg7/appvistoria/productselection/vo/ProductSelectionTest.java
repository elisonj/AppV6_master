package br.com.bg7.appvistoria.productselection.vo;

import junit.framework.Assert;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by: luciolucio
 * Date: 2017-09-12
 */

public class ProductSelectionTest {

    private static final ProductType CARROS_E_MOTOS = new ProductType(17L, "Carros & Motos");
    private static final ProductType IMOVEIS = new ProductType(18L, "Imoveis");

    private static final Category CARROS = new Category("Carros", CARROS_E_MOTOS);
    private static final Category MOTOS = new Category("Motos", CARROS_E_MOTOS);

    private static final Category RESIDENCIAL = new Category("Residencial", IMOVEIS);

    @Test
    public void shouldCreateEmptyList() {
        ArrayList<Product> productList = new ArrayList<>();
        List<ProductSelection> productSelectionList = ProductSelection.fromProducts(null, productList);

        Assert.assertEquals(0, productSelectionList.size());
    }

    @Test
    public void shouldCreateListWithOneProductTypeAndTwoProductsSameCategory() {
        final Product product1 = new Product(1L, CARROS);
        final Product product2 = new Product(2L, CARROS);

        ArrayList<Product> productList = new ArrayList<Product>() {{
            add(product1);
            add(product2);
        }};

        List<ProductSelection> productSelectionList = ProductSelection.fromProducts(null, productList);
        Assert.assertEquals(1, productSelectionList.size());
        Assert.assertEquals(1, productSelectionList.get(0).getItems().size());

        ProductSelection selection = productSelectionList.get(0);
        ProductSelectionHeader header = selection.getHeader();
        ProductSelectionItem item = selection.getItems().get(0);

        Assert.assertEquals("Carros & Motos", header.getTitle());
        Assert.assertEquals("Carros", item.getCategory());
        Assert.assertEquals(2, item.getCount());
    }

    @Test
    public void shouldCreateListWithTwoProductTypesAndTwoCategories() {
        final Product product1 = new Product(1L, RESIDENCIAL);
        final Product product2 = new Product(2L, MOTOS);
        final Product product3 = new Product(3L, CARROS);
        final Product product4 = new Product(4L, CARROS);

        ArrayList<Product> productList = new ArrayList<Product>() {{
            add(product1);
            add(product2);
            add(product3);
            add(product4);
        }};

        List<ProductSelection> productSelectionList = ProductSelection.fromProducts(null, productList);
        Assert.assertEquals(2, productSelectionList.size());
        Assert.assertEquals(2, productSelectionList.get(0).getItems().size());
        Assert.assertEquals(1, productSelectionList.get(1).getItems().size());

        ProductSelection selection1 = productSelectionList.get(0);
        ProductSelectionHeader header1 = selection1.getHeader();
        ProductSelectionItem item11 = selection1.getItems().get(0);
        ProductSelectionItem item12 = selection1.getItems().get(1);

        ProductSelection selection2 = productSelectionList.get(1);
        ProductSelectionHeader header2 = selection2.getHeader();
        ProductSelectionItem item21 = selection2.getItems().get(0);

        Assert.assertEquals("Imoveis", header2.getTitle());
        Assert.assertEquals("Residencial", item21.getCategory());
        Assert.assertEquals(1, item21.getCount());

        Assert.assertEquals("Carros & Motos", header1.getTitle());
        Assert.assertEquals("Carros", item11.getCategory());
        Assert.assertEquals(2, item11.getCount());
        Assert.assertEquals("Motos", item12.getCategory());
        Assert.assertEquals(1, item12.getCount());
    }
}
