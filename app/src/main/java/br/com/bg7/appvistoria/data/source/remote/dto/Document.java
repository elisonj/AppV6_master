package br.com.bg7.appvistoria.data.source.remote.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by: elison
 * Date: 2017-07-12
 */
public class Document {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("type")
    @Expose
    private Integer type;
    @SerializedName("number")
    @Expose
    private String number;
    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("order")
    @Expose
    private Integer order;
    @SerializedName("description")
    @Expose
    private String description;
}
