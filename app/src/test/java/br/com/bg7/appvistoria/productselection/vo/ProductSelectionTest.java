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

    private static final WorkOrderProductType CARROS_E_MOTOS = new WorkOrderProductType(17L, "Carros & Motos");
    private static final WorkOrderProductType IMOVEIS = new WorkOrderProductType(18L, "Imoveis");

    private static final WorkOrderCategory CARROS = new WorkOrderCategory("Carros", CARROS_E_MOTOS);
    private static final WorkOrderCategory MOTOS = new WorkOrderCategory("Motos", CARROS_E_MOTOS);

    private static final WorkOrderCategory RESIDENCIAL = new WorkOrderCategory("Residencial", IMOVEIS);

    @Test
    public void shouldCreateEmptyList() {
        ArrayList<WorkOrderProduct> productList = new ArrayList<>();
        List<ProductSelection> productSelectionList = ProductSelection.fromProducts(null, productList);

        Assert.assertEquals(0, productSelectionList.size());
    }

    @Test
    public void shouldCreateListWithOneProductTypeAndTwoProductsSameCategory() {
        final WorkOrderProduct product1 = new WorkOrderProduct(1L, CARROS);
        final WorkOrderProduct product2 = new WorkOrderProduct(2L, CARROS);

        ArrayList<WorkOrderProduct> productList = new ArrayList<WorkOrderProduct>() {{
            add(product1);
            add(product2);
        }};

        List<ProductSelection> productSelectionList = ProductSelection.fromProducts(null, productList);
        Assert.assertEquals(1, productSelectionList.size());
        Assert.assertEquals(1, productSelectionList.get(0).getCategories().size());

        ProductSelection selection = productSelectionList.get(0);
        ProductSelectionHeader header = selection.getProductType();
        ProductSelectionItem item = selection.getCategories().get(0);

        Assert.assertEquals("Carros & Motos", header.getTitle());
        Assert.assertEquals("Carros", item.getCategory());
        Assert.assertEquals(2, item.getCount());
    }

    @Test
    public void shouldCreateListWithTwoProductTypesAndTwoCategories() {
        final WorkOrderProduct product1 = new WorkOrderProduct(1L, RESIDENCIAL);
        final WorkOrderProduct product2 = new WorkOrderProduct(2L, MOTOS);
        final WorkOrderProduct product3 = new WorkOrderProduct(3L, CARROS);
        final WorkOrderProduct product4 = new WorkOrderProduct(4L, CARROS);

        ArrayList<WorkOrderProduct> productList = new ArrayList<WorkOrderProduct>() {{
            add(product1);
            add(product2);
            add(product3);
            add(product4);
        }};

        List<ProductSelection> productSelectionList = ProductSelection.fromProducts(null, productList);
        Assert.assertEquals(2, productSelectionList.size());
        Assert.assertEquals(2, productSelectionList.get(0).getCategories().size());
        Assert.assertEquals(1, productSelectionList.get(1).getCategories().size());

        ProductSelection selection1 = productSelectionList.get(0);
        ProductSelectionHeader header1 = selection1.getProductType();
        ProductSelectionItem item11 = selection1.getCategories().get(0);
        ProductSelectionItem item12 = selection1.getCategories().get(1);

        ProductSelection selection2 = productSelectionList.get(1);
        ProductSelectionHeader header2 = selection2.getProductType();
        ProductSelectionItem item21 = selection2.getCategories().get(0);

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
