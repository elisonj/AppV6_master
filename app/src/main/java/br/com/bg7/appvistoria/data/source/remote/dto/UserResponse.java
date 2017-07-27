package br.com.bg7.appvistoria.data.source.remote.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by: elison
 * Date: 2017-07-12
 *
 * {@link SuppressWarnings pois o DTO tem campos requeridos usados para serializar}
 */
@SuppressWarnings("unused")
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
    private final List<UserAccount> userAccounts = null;
}
