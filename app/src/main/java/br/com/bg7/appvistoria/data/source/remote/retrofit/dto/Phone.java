package br.com.bg7.appvistoria.data.source.remote.retrofit.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by: elison
 * Date: 2017-07-12
 */
public class Phone  {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("type")
    @Expose
    private Integer type;
    @SerializedName("ddi")
    @Expose
    private String ddi;
    @SerializedName("ddd")
    @Expose
    private String ddd;
    @SerializedName("number")
    @Expose
    private String number;
    @SerializedName("userId")
    @Expose
    private Integer userId;
}
