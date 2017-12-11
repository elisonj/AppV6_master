package br.com.bg7.appvistoria.data;

import br.com.bg7.appvistoria.projectselection.vo.Location;
import br.com.bg7.appvistoria.projectselection.vo.Project;

/**
 * Created by: elison
 * Date: 2017-09-17
 */

public class TestableSummaryWorkOrder extends WorkOrder {

    public static final Project PROJECT = new Project(0L, "");
    public static final Location LOCATION = new Location(0L, "");

    private String summary;

    TestableSummaryWorkOrder(String summary) {
        super(PROJECT, LOCATION);
        this.summary = summary;
    }

    @Override
    protected String ellipsizeShortSummary(String summary, int maxSize) {
        return super.ellipsizeShortSummary(this.summary, maxSize);
    }
}
