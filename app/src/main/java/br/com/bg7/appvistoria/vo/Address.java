package br.com.bg7.appvistoria.vo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by: elison
 * Date: 2017-07-12
 */
public class Address {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("type")
    @Expose
    private Integer type;
    @SerializedName("streetType")
    @Expose
    private String streetType;
    @SerializedName("addressLine1")
    @Expose
    private String addressLine1;
    @SerializedName("number")
    @Expose
    private String number;
    @SerializedName("addressLine2")
    @Expose
    private String addressLine2;
    @SerializedName("district")
    @Expose
    private String district;
    @SerializedName("zipCode")
    @Expose
    private String zipCode;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("countryId")
    @Expose
    private Integer countryId;
    @SerializedName("countryIsoKey")
    @Expose
    private String countryIsoKey;
    @SerializedName("countryName")
    @Expose
    private String countryName;

}
