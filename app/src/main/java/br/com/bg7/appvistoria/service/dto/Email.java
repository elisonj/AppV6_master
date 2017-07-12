package br.com.bg7.appvistoria.service.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by: elison
 * Date: 2017-07-12
 */
public class Email {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("validated")
    @Expose
    private Boolean validated;
    @SerializedName("validatedAt")
    @Expose
    private String validatedAt;
    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("master")
    @Expose
    private Boolean master;

    public String getAddress() {
        return address;
    }
}
