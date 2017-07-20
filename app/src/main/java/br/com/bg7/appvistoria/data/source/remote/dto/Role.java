package br.com.bg7.appvistoria.data.source.remote.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by: elison
 * Date: 2017-07-12
 */
class Role {

    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("roleId")
    @Expose
    private Integer roleId;
    @SerializedName("roleDescription")
    @Expose
    private String roleDescription;
    @SerializedName("userParentId")
    @Expose
    private Integer userParentId;
    @SerializedName("userParentName")
    @Expose
    private String userParentName;
}
