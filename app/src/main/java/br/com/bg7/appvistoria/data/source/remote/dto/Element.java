package br.com.bg7.appvistoria.data.source.remote.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by: elison
 * Date: 2017-09-04
 */
public class Element {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("statusId")
    @Expose
    private Integer statusId;
    @SerializedName("statusDesc")
    @Expose
    private String statusDesc;
    @SerializedName("closeAt")
    @Expose
    private String closeAt;
    @SerializedName("ownerId")
    @Expose
    private Integer ownerId;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("productsPerSubCategory")
    @Expose
    private ProductsPerSubCategory productsPerSubCategory;
    @SerializedName("storeId")
    @Expose
    private Integer storeId;

    public Integer getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
}
