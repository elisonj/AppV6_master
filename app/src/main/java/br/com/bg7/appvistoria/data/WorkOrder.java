package br.com.bg7.appvistoria.data;

import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import br.com.bg7.appvistoria.BuildConfig;
import br.com.bg7.appvistoria.projectselection.vo.Location;
import br.com.bg7.appvistoria.projectselection.vo.Project;
import br.com.bg7.appvistoria.workorder.WorkOrderStatus;

/**
 * Created by: elison
 * Date: 2017-08-15
 */
@DatabaseTable(tableName = "workorders")
public class WorkOrder {

    private final static String ELLIPSIS = "...";
    private final static int ELLIPSIS_SIZE = ELLIPSIS.length();
    private final static String SHORT_SUMMARY_SEPARATOR = ",";
    private static final String SUMMARY_ITEM_SEPARATOR = ": ";
    private static final String SUMMARY_ENTRY_SEPARATOR = ", ";

    private String summary = "";

    private boolean summaryIsDirty = true;

    private int shortSummarySize = -1;

    private String shortSummary;

    @DatabaseField(generatedId = true)
    private Long id;

    @DatabaseField(canBeNull = false, columnName = PROJECT_ID_FIELD)
    private Long projectId;
    public static final String PROJECT_ID_FIELD = "projectId";

    @DatabaseField(canBeNull = false)
    private String projectDescription;

    @DatabaseField(index = true, canBeNull = false)
    private WorkOrderStatus status;

    @DatabaseField(canBeNull = false)
    private DateTime endAt;

    @DatabaseField(canBeNull = false, columnName = LOCATION_ID_FIELD)
    private Long locationId;
    public static final String LOCATION_ID_FIELD = "locationId";

    @DatabaseField(canBeNull = false)
    private String address;

    @ForeignCollectionField
    private Collection<Inspection> inspections = new ArrayList<>();

    @ForeignCollectionField(eager = true)
    private Collection<WorkOrderProduct> products = new ArrayList<>();

    @SuppressWarnings("unused")
    WorkOrder() {
        // used by ormlite
    }

    public WorkOrder(Project project, Location location, List<WorkOrderProduct> workOrderProducts) {
        this.projectId = project.getId();
        this.projectDescription = project.getDescription();

        this.locationId = location.getId();
        this.address = location.getAddress();

        for (WorkOrderProduct product : workOrderProducts) {
            addProduct(product);
        }

        this.status = WorkOrderStatus.NOT_STARTED;
        this.endAt = DateTime.now();
    }

    public void addProduct(WorkOrderProduct product) {
        products.add(product);
        summaryIsDirty = true;
    }

    public WorkOrderStatus getStatus() {
        return status;
    }

    public String getAddress() {
        return address;
    }

    public Long getProjectId() {
        return projectId;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public String getShortSummary(int maxSize) {
        if (maxSize != shortSummarySize || summaryIsDirty) {
            shortSummarySize = maxSize;
            shortSummary = ellipsizeShortSummary(summary, maxSize);
        }

        return shortSummary;
    }

    public String getSummary() {
        if (!summaryIsDirty) {
            return summary;
        }

        HashMap<String, Integer> summaryData = new HashMap<>();

        for (WorkOrderProduct product : products) {
            String category = product.getCategory().getName();

            if (!summaryData.containsKey(category)) {
                summaryData.put(category, 0);
            }

            summaryData.put(category, summaryData.get(category) + 1);
        }

        ArrayList<String> summaryEntries = new ArrayList<>();
        for (Map.Entry<String, Integer> summaryItem : summaryData.entrySet()) {
            summaryEntries.add(summaryItem.getKey() + SUMMARY_ITEM_SEPARATOR + summaryItem.getValue());
        }

        summary = Joiner.on(SUMMARY_ENTRY_SEPARATOR).join(summaryEntries);
        return summary;
    }

    public void start() {
        status = WorkOrderStatus.IN_PROGRESS;
    }

    public void finish() {
        status = WorkOrderStatus.COMPLETED;
    }

    public String getEndAt(Locale locale) {
        String pattern = DateTimeFormat.patternForStyle(BuildConfig.DATE_TIME_STYLE, locale);

        if (pattern.contains("yy") && !pattern.contains("yyyy")) {
            pattern = pattern.replace("yy", "yyyy");
        }

        DateTimeFormatter formatter = DateTimeFormat.forPattern(pattern);
        return formatter.print(endAt);
    }

    protected String ellipsizeShortSummary(String summary, int maxSize) {
        if (summary.length() <= maxSize) {
            return summary;
        }

        String text = summary.substring(0, maxSize - ELLIPSIS_SIZE + 1);

        if (!text.endsWith(SHORT_SUMMARY_SEPARATOR)) {
            text = summary.substring(0, maxSize - ELLIPSIS_SIZE);
        }
        text = text.substring(0, text.lastIndexOf(SHORT_SUMMARY_SEPARATOR));

        return text + ELLIPSIS;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkOrder workOrder = (WorkOrder) o;
        return shortSummarySize == workOrder.shortSummarySize &&
                Objects.equal(shortSummary, workOrder.shortSummary) &&
                Objects.equal(id, workOrder.id) &&
                Objects.equal(projectDescription, workOrder.projectDescription) &&
                Objects.equal(summary, workOrder.summary) &&
                status == workOrder.status &&
                Objects.equal(endAt, workOrder.endAt) &&
                Objects.equal(address, workOrder.address) &&
                Objects.equal(inspections, workOrder.inspections);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(shortSummarySize, shortSummary, id, projectDescription, summary, status, endAt, address, inspections);
    }
}
