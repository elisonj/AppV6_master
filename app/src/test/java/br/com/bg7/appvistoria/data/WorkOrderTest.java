package br.com.bg7.appvistoria.data;

import junit.framework.Assert;

import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Locale;

import br.com.bg7.appvistoria.workorder.InProgressWorkOrder;

/**
 * Created by: luciolucio
 * Date: 2017-08-23
 */

public class WorkOrderTest {

    @Before
    public void setUp() {
        DateTime date = new DateTime(2017, 8, 22, 0, 0, 0);
        DateTimeUtils.setCurrentMillisFixed(date.getMillis());
    }

    @Test
    public void shouldShowLocalizedDates() {
        WorkOrder wo = new InProgressWorkOrder("", "");

        String date = wo.getEndAt(new Locale("pt", "BR"));
        Assert.assertEquals("22/08/2017", date);

        date = wo.getEndAt(new Locale("en", "US"));
        Assert.assertEquals("8/22/2017", date);

        date = wo.getEndAt(new Locale("en", "GB"));
        Assert.assertEquals("22/08/2017", date);
    }

    @Test
    public void shouldCropShortSummaries() {

        ArrayList<SummaryTestCase> testCases = new ArrayList<>();
        testCases.add(new SummaryTestCase(
                "Carros: 50, motos: 30, caminhões: 20",
                "Carros: 50, motos: 30, caminhões: 20"));
        testCases.add(new SummaryTestCase(
                "Carros: 500, motos: 300, caminhões: 230",
                "Carros: 500, motos: 300, caminhões: 230"));
        testCases.add(new SummaryTestCase(
                "Carros: 500, motos: 300, caminhões: 2305",
                "Carros: 500, motos: 300, caminhões: 2305"));
        testCases.add(new SummaryTestCase(
                "Carros: 500, motos: 300, caminhões: 23057",
                "Carros: 500, motos: 300..."));
        // TODO: Corrigir este caso (1)
        testCases.add(new SummaryTestCase( // Este retorna 42 caracteres
                "Carros: 500, motos: 300, caminhões: 230, vans: 13",
                "Carros: 500, motos: 300..."));
        // TODO: Corrigir este caso (2)
        testCases.add(new SummaryTestCase( // Este retorna 41 caracteres
                "Carros: 500, motos: 300, caminhões: 23, vans: 13",
                "Carros: 500, motos: 300..."));
        testCases.add(new SummaryTestCase(
                "Carros: 500, motos: 300, caminhões: 2, vans: 13",
                "Carros: 500, motos: 300, caminhões: 2..."));
        testCases.add(new SummaryTestCase(
                "Carros: 500, motos: 300, caminhões: 2305, vans: 13",
                "Carros: 500, motos: 300..."));
        testCases.add(new SummaryTestCase(
                "Carros: 500, motos: 300, caminhões: 23057, vans: 13",
                "Carros: 500, motos: 300..."));
        testCases.add(new SummaryTestCase(
                "Carros: 50, motos: 30, caminhões: 20, vans: 13, empilhadeiras: 5, trator: 1",
                "Carros: 50, motos: 30, caminhões: 20..."));
        testCases.add(new SummaryTestCase(
                "Motos: 300, caminhões: 200, trator: 14567",
                "Motos: 300, caminhões: 200..."));
        testCases.add(new SummaryTestCase(
                "Motos: 30023, trator: 14567, caminhões: 200",
                "Motos: 30023, trator: 14567..."));
        testCases.add(new SummaryTestCase(
                "Motos: 30023, trator: 14567, vans: 1333, caminhões: 200",
                "Motos: 30023, trator: 14567, vans: 1333..."));

        for (SummaryTestCase testCase: testCases) {
            testSummary(testCase.actual, testCase.expected);
        }
    }

    private void testSummary(String actual, String expected) {
        int maxSummarySize = 40;

        WorkOrder workOrder = new WorkOrder("Projeto", actual);
        Assert.assertEquals(expected, workOrder.getShortSummary(maxSummarySize));
    }

    private class SummaryTestCase {
        String actual;
        String expected;

        SummaryTestCase(String actual, String expected) {
            this.actual = actual;
            this.expected = expected;
        }
    }
}
