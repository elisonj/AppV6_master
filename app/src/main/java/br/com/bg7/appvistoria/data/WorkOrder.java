package br.com.bg7.appvistoria.data;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

import br.com.bg7.appvistoria.workorder.WorkOrderStatus;

/**
 * Created by: elison
 * Date: 2017-08-15
 */
@DatabaseTable(tableName = "workorders")
public class WorkOrder {

    public static final int MAX_SIZE_SHORT_SUMMARY = 54;

    private String dateFormatPtBr = "dd/MM/yyyy";
    private String dateFormatEnUs = "yyyy-MM-dd";

    @DatabaseField(generatedId = true)
    private Long id;

    @DatabaseField(canBeNull = false)
    private String name;

    @DatabaseField(canBeNull = false)
    private String summary;

    @DatabaseField(canBeNull = false)
    private String shortSummary;

    @DatabaseField(index = true, canBeNull = false)
    private WorkOrderStatus status;

    @DatabaseField(canBeNull = false)
    private DateTime endAt;

    @DatabaseField(canBeNull = false)
    private String address;

    @DatabaseField(canBeNull = false)
    private Long externalId;

    @ForeignCollectionField
    private Collection<Inspection> inspections = new ArrayList<>();

    /**
     * Default constructor used by ormlite
     */
    @SuppressWarnings("unused")
    public WorkOrder() {}

    public WorkOrder(String name,String summary, WorkOrderStatus status) {
        this.name = name;
        this.summary = summary;
        this.shortSummary = ellipsizeShortSummary(summary);
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public String getShortSummary() {
        return shortSummary;
    }

    public String getSummary() {
        return summary;
    }

    public WorkOrderStatus getStatus() {
        return status;
    }

    public String getEndAt(Locale locale) {
        if(endAt == null) {
            endAt = new DateTime();
        }
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormatPtBr, locale);
        if(locale.equals(Locale.ENGLISH)) {
            formatter = new SimpleDateFormat(dateFormatEnUs, locale);
        }
        return formatter.format(endAt.toDate());
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    private String ellipsizeShortSummary(String summary) {

        String text = summary.substring(0, MAX_SIZE_SHORT_SUMMARY);
        text = text.substring(0, text.lastIndexOf(" "));

        String numeric = text.substring(text.lastIndexOf(" "));
        if(StringUtils.isNumeric(numeric.trim())) {
            text = text.substring(0, text.lastIndexOf(" "));
        }

        if(text.endsWith(",")) {
            text = text.substring(0, text.length()-1);
        }

        text = text+"...";
        return text;
    }
}
