package br.com.bg7.appvistoria.data.source.remote.retrofit.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by: elison
 * Date: 2017-07-12
 */
public class UserResponse {

    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("start")
    @Expose
    private Integer start;
    @SerializedName("limit")
    @Expose
    private Integer limit;
    @SerializedName("userAccounts")
    @Expose
    private List<UserAccount> userAccounts = null;

    public List<UserAccount> getUserAccounts() {
        return userAccounts;
    }

}
