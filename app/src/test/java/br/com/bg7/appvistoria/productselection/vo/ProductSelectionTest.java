package br.com.bg7.appvistoria.productselection.vo;

import junit.framework.Assert;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.com.bg7.appvistoria.projectselection.vo.Category;
import br.com.bg7.appvistoria.projectselection.vo.Product;
import br.com.bg7.appvistoria.projectselection.vo.ProductSelection;

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

        final Product product1 = new Product(1L, productType1, category1);
        final Product product2 = new Product(2L, productType1, category1);

        ArrayList<Product> productList = new ArrayList<Product>() {{
            add(product1);
            add(product2);
        }};

        List<ProductSelection> productSelectionList = ProductSelection.fromProducts(productList);

        Assert.assertEquals(1, productSelectionList.size());
        Assert.assertEquals("p1", productSelectionList.get(0).getProductType());

        HashMap<String, Integer> categoryCounts = productSelectionList.get(0).getCategoryCounts();
        Assert.assertEquals(1, categoryCounts.size());
        Assert.assertEquals(2, (int) categoryCounts.get("c1"));
    }

    @Test
    public void shouldCreateListWithTwoProductTypesAndTwoCategories() {
        String productType1 = "p1";
        String productType2 = "p2";
        Category category1 = new Category(1L, "c1");
        Category category2 = new Category(2L, "c2");

        final Product product1 = new Product(1L, productType1, category1);
        final Product product2 = new Product(2L, productType2, category2);
        final Product product3 = new Product(3L, productType2, category1);
        final Product product4 = new Product(4L, productType2, category1);

        ArrayList<Product> productList = new ArrayList<Product>() {{
            add(product1);
            add(product2);
            add(product3);
            add(product4);
        }};

        List<ProductSelection> productSelectionList = ProductSelection.fromProducts(productList);

        Assert.assertEquals(2, productSelectionList.size());
        Assert.assertEquals("p1", productSelectionList.get(0).getProductType());
        Assert.assertEquals("p2", productSelectionList.get(1).getProductType());

        HashMap<String, Integer> categoryCounts1 = productSelectionList.get(0).getCategoryCounts();
        Assert.assertEquals(1, categoryCounts1.size());
        Assert.assertEquals(1, (int) categoryCounts1.get("c1"));
        Assert.assertEquals(null, categoryCounts1.get("c2"));

        HashMap<String, Integer> categoryCounts2 = productSelectionList.get(1).getCategoryCounts();
        Assert.assertEquals(2, categoryCounts2.size());
        Assert.assertEquals(2, (int) categoryCounts2.get("c1"));
        Assert.assertEquals(1, (int) categoryCounts2.get("c2"));
    }
}
