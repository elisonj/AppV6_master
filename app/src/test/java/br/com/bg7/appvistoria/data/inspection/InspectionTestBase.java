package br.com.bg7.appvistoria.data.inspection;

import org.junit.Before;

import br.com.bg7.appvistoria.data.Inspection;

/**
 * Created by: luciolucio
 * Date: 2017-08-17
 */

class InspectionTestBase {

    Inspection inspection;

    @Before
    public void setUp() {
        inspection = new Inspection();
    }
}
