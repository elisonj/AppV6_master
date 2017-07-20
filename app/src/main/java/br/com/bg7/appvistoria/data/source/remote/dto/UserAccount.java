package br.com.bg7.appvistoria.data.source.remote.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by: elison
 * Date: 2017-07-11
 */

public class UserAccount {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("lastUpdatedAt")
    @Expose
    private String lastUpdatedAt;
    @SerializedName("portalId")
    @Expose
    private Integer portalId;
    @SerializedName("statusId")
    @Expose
    private Integer statusId;
    @SerializedName("basicInfo")
    @Expose
    private final BasicInfo basicInfo;
    @SerializedName("companyInfo")
    @Expose
    private CompanyInfo companyInfo;
    @SerializedName("phones")
    @Expose
    private List<Phone> phones = null;
    @SerializedName("addresses")
    @Expose
    private List<Address> addresses = null;
    @SerializedName("documents")
    @Expose
    private List<Document> documents = null;
    @SerializedName("roles")
    @Expose
    private List<Role> roles = null;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("credentials")
    @Expose
    private final Credentials credentials;
    @SerializedName("mothersName")
    @Expose
    private String mothersName;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("birthDate")
    @Expose
    private String birthDate;
    @SerializedName("company")
    @Expose
    private String company;
    @SerializedName("companyPosition")
    @Expose
    private String companyPosition;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BasicInfo getBasicInfo() {
        return basicInfo;
    }

    public Credentials getCredentials() {
        return credentials;
    }
}