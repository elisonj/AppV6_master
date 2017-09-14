package br.com.bg7.appvistoria.productselection.vo;

import junit.framework.Assert;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import br.com.bg7.appvistoria.productselection.ProductSelectionHeader;

/**
 * Created by: luciolucio
 * Date: 2017-09-12
 */

public class ProductSelectionTest {
    @Test
    public void shouldCreateEmptyList() {
        ArrayList<Product> productList = new ArrayList<>();
        List<ProductSelection> productSelectionList = ProductSelection.fromProducts(productList);

        Assert.assertEquals(0, productSelectionList.size());
    }

    @Test
    public void shouldCreateListWithOneProductTypeAndTwoProductsSameCategory() {
        String productType1 = "p1";
        Category category1 = new Category(1L, "c1");

        final Product product1 = new Product(1L, 17L, productType1, category1);
        final Product product2 = new Product(2L, 18L, productType1, category1);

        ArrayList<Product> productList = new ArrayList<Product>() {{
            add(product1);
            add(product2);
        }};

        List<ProductSelection> productSelectionList = ProductSelection.fromProducts(productList);
        Assert.assertEquals(1, productSelectionList.size());
        Assert.assertEquals(1, productSelectionList.get(0).getItems().size());

        ProductSelection selection = productSelectionList.get(0);
        ProductSelectionHeader header = selection.getHeader();
        ProductSelectionItem item = selection.getItems().get(0);

        Assert.assertEquals("p1", header.getTitle());
        Assert.assertEquals("c1", item.getTitle());
        Assert.assertEquals(2, item.getCount());
    }

    @Test
    public void shouldCreateListWithTwoProductTypesAndTwoCategories() {
        String productType1 = "p1";
        String productType2 = "p2";
        Category category1 = new Category(1L, "c1");
        Category category2 = new Category(2L, "c2");

        final Product product1 = new Product(1L, 17L, productType1, category1);
        final Product product2 = new Product(2L, 18L, productType2, category2);
        final Product product3 = new Product(3L, 18L, productType2, category1);
        final Product product4 = new Product(4L, 18L, productType2, category1);

        ArrayList<Product> productList = new ArrayList<Product>() {{
            add(product1);
            add(product2);
            add(product3);
            add(product4);
        }};

        List<ProductSelection> productSelectionList = ProductSelection.fromProducts(productList);
        Assert.assertEquals(2, productSelectionList.size());
        Assert.assertEquals(1, productSelectionList.get(0).getItems().size());
        Assert.assertEquals(2, productSelectionList.get(1).getItems().size());

        ProductSelection selection1 = productSelectionList.get(0);
        ProductSelectionHeader header1 = selection1.getHeader();
        ProductSelectionItem item11 = selection1.getItems().get(0);

        ProductSelection selection2 = productSelectionList.get(1);
        ProductSelectionHeader header2 = selection2.getHeader();
        ProductSelectionItem item21 = selection2.getItems().get(0);
        ProductSelectionItem item22 = selection2.getItems().get(1);

        Assert.assertEquals("p1", header1.getTitle());
        Assert.assertEquals("c1", item11.getTitle());
        Assert.assertEquals(1, item11.getCount());

        Assert.assertEquals("p2", header2.getTitle());
        Assert.assertEquals("c1", item21.getTitle());
        Assert.assertEquals(2, item21.getCount());
        Assert.assertEquals("c2", item22.getTitle());
        Assert.assertEquals(1, item22.getCount());
    }
}
