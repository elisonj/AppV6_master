package br.com.bg7.appvistoria.data;

import junit.framework.Assert;

import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import br.com.bg7.appvistoria.workorder.InProgressWorkOrder;

/**
 * Created by: luciolucio
 * Date: 2017-08-23
 */

public class WorkOrderTest {

    private static final WorkOrderProductType PRODUCT_TYPE = new WorkOrderProductType(10L, "PRODUCT_TYPE");
    private static final WorkOrderCategory MOTOS = new WorkOrderCategory("Motos", PRODUCT_TYPE);
    private static final WorkOrderCategory CARROS = new WorkOrderCategory("Carros", PRODUCT_TYPE);

    @Before
    public void setUp() {
        DateTime date = new DateTime(2017, 8, 22, 0, 0, 0);
        DateTimeUtils.setCurrentMillisFixed(date.getMillis());
    }

    @Test
    public void shouldShowLocalizedDates() {
        WorkOrder wo = new InProgressWorkOrder("", "", "");

        String date = wo.getEndAt(new Locale("pt", "BR"));
        Assert.assertEquals("22/08/2017", date);

        date = wo.getEndAt(new Locale("en", "US"));
        Assert.assertEquals("8/22/2017", date);

        date = wo.getEndAt(new Locale("en", "GB"));
        Assert.assertEquals("22/08/2017", date);
    }

    @Test
    public void shouldCropShortSummaries() {

        int maxSummarySize = 40;

        HashMap<String, String> testCases = new HashMap<>();
        testCases.put(
                "Carros: 50, motos: 30, caminhões: 20",
                "Carros: 50, motos: 30, caminhões: 20");
        testCases.put(
                "Carros: 500, motos: 300, caminhões: 230",
                "Carros: 500, motos: 300, caminhões: 230");
        testCases.put(
                "Carros: 500, motos: 300, caminhões: 2305",
                "Carros: 500, motos: 300, caminhões: 2305");
        testCases.put(
                "Carros: 500, motos: 300, caminhões: 23057",
                "Carros: 500, motos: 300...");
        testCases.put(
                "Carros: 500, motos: 300, caminhões: 230, vans: 13",
                "Carros: 500, motos: 300...");
        testCases.put(
                "Carros: 500, motos: 300, caminhões: 23, vans: 13",
                "Carros: 500, motos: 300...");
        testCases.put(
                "Carros: 500, motos: 300, caminhões: 2, vans: 13",
                "Carros: 500, motos: 300, caminhões: 2...");
        testCases.put(
                "Carros: 500, motos: 300, caminhões: 2305, vans: 13",
                "Carros: 500, motos: 300...");
        testCases.put(
                "Carros: 500, motos: 300, caminhões: 23057, vans: 13",
                "Carros: 500, motos: 300...");
        testCases.put(
                "Carros: 50, motos: 30, caminhões: 20, vans: 13, empilhadeiras: 5, trator: 1",
                "Carros: 50, motos: 30, caminhões: 20...");
        testCases.put(
                "Motos: 300, caminhões: 200, trator: 14567",
                "Motos: 300, caminhões: 200...");
        testCases.put(
                "Motos: 30023, trator: 14567, caminhões: 200",
                "Motos: 30023, trator: 14567...");
        testCases.put(
                "Motos: 30023, trator: 14567, vans: 1333, caminhões: 200",
                "Motos: 30023, trator: 14567...");

        for (Map.Entry<String, String> testCase : testCases.entrySet()) {
            String fullSummary = testCase.getKey();
            String expectedShortSummary = testCase.getValue();

            WorkOrder workOrder = new TestableWorkOrder("Projeto", "", fullSummary);
            Assert.assertEquals(expectedShortSummary, workOrder.getShortSummary(maxSummarySize));
        }
    }

    @Test
    public void shouldCreateSummaries() {
        ArrayList<WorkOrderSummaryTestCase> testCases = new ArrayList<WorkOrderSummaryTestCase>() {{
            add(WorkOrderSummaryTestCase
                    .expectedSummary("Carros: 1, Motos: 2")
                    .withProducts(CARROS, 1)
                    .withProducts(MOTOS, 2));
        }};

        for (WorkOrderSummaryTestCase testCase : testCases) {
            testCase.run();
        }
    }
}
