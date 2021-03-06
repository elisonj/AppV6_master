package br.com.bg7.appvistoria.data.source.remote.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by: elison
 * Date: 2017-07-12
 */
@SuppressWarnings("unused")
public class BasicInfo {
    @SerializedName("fullName")
    @Expose
    private String fullName;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("countryId")
    @Expose
    private Integer countryId;
    @SerializedName("countryDDI")
    @Expose
    private Integer countryDDI;
    @SerializedName("countryCode")
    @Expose
    private String countryCode;
    @SerializedName("civilStateId")
    @Expose
    private Integer civilStateId;
    @SerializedName("email")
    @Expose
    private Email email;

    public String getFullName() {
        return fullName;
    }

    public Email getEmail() {
        return email;
    }
}
