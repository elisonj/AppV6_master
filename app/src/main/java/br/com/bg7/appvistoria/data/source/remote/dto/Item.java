package br.com.bg7.appvistoria.data.source.remote.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by: elison
 * Date: 2017-09-04
 */
public class Item {
    @SerializedName("subCategoryId")
    @Expose
    private Integer subCategoryId;
    @SerializedName("subCategoryDesc")
    @Expose
    private String subCategoryDesc;
    @SerializedName("count")
    @Expose
    private Integer count;
}
