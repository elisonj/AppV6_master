package br.com.bg7.appvistoria.data;

import com.google.common.base.Objects;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

import br.com.bg7.appvistoria.BuildConfig;
import br.com.bg7.appvistoria.productselection.vo.ProductSelectionItem;
import br.com.bg7.appvistoria.workorder.WorkOrderStatus;

/**
 * Created by: elison
 * Date: 2017-08-15
 */
@DatabaseTable(tableName = "workorders")
public class WorkOrder {

    private final static String ELLIPSIS = "...";
    private final static int ELLIPSIS_SIZE = ELLIPSIS.length();
    private final static String SEPARATOR = ",";
    // TODO: Voltar isso para privado
    @DatabaseField(canBeNull = false)
    String summary = "";
    boolean summaryIsDirty = true;
    private int shortSummarySize = -1;
    private String shortSummary;
    @DatabaseField(generatedId = true)
    private Long id;
    @DatabaseField(canBeNull = false)
    private String name;
    @DatabaseField(index = true, canBeNull = false)
    private WorkOrderStatus status;

    @DatabaseField(canBeNull = false)
    private DateTime endAt;

    @DatabaseField(canBeNull = false)
    private String address;

    @ForeignCollectionField
    private Collection<Inspection> inspections = new ArrayList<>();

    @ForeignCollectionField
    private Collection<WorkOrderProduct> products = new ArrayList<>();

    /**
     * Default constructor used by ormlite
     */
    @SuppressWarnings("unused")
    // TODO: Verificar se precisamos mesmo ter esse método público - WorkOrder
    public WorkOrder() {
    }

    public WorkOrder(String name, String address) {
        this.name = name;
        this.address = address;
        this.status = WorkOrderStatus.NOT_STARTED;
        this.endAt = DateTime.now();
    }

    public void addProduct(WorkOrderProduct product) {
        products.add(product);
        summaryIsDirty = true;
    }

    public String getName() {
        return name;
    }

    public String getShortSummary(int maxSize) {
        if (maxSize != shortSummarySize || summaryIsDirty) {
            shortSummarySize = maxSize;
            shortSummary = ellipsizeShortSummary(maxSize);
        }

        return shortSummary;
    }

    public String getSummary() {
        return "";
    }

    public WorkOrderStatus getStatus() {
        return status;
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

    public String getAddress() {
        return address;
    }

    private String ellipsizeShortSummary(int maxSize) {
        if (summary.length() <= maxSize) {
            return summary;
        }

        String text = summary.substring(0, maxSize - ELLIPSIS_SIZE + 1);

        if (!text.endsWith(SEPARATOR)) {
            text = summary.substring(0, maxSize - ELLIPSIS_SIZE);
        }
        text = text.substring(0, text.lastIndexOf(SEPARATOR));

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
                Objects.equal(name, workOrder.name) &&
                Objects.equal(summary, workOrder.summary) &&
                status == workOrder.status &&
                Objects.equal(endAt, workOrder.endAt) &&
                Objects.equal(address, workOrder.address) &&
                Objects.equal(inspections, workOrder.inspections);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(shortSummarySize, shortSummary, id, name, summary, status, endAt, address, inspections);
    }
}
