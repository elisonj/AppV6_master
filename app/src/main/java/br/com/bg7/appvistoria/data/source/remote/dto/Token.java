package br.com.bg7.appvistoria.data.source.remote.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by: elison
 * Date: 2017-07-11
 *
 * {@link SuppressWarnings pois o DTO tem campos requeridos usados para serializar}
 */
@SuppressWarnings("unused")
public class Token {

    @SerializedName("access_token")
    @Expose
    private String accessToken;
    @SerializedName("token_type")
    @Expose
    private String tokenType;
    @SerializedName("expires_in")
    @Expose
    private Integer expiresIn;
    @SerializedName("refresh_token")
    @Expose
    private String refreshToken;
    @SerializedName("userId")
    @Expose
    private String userId;

    public Token() {
    }

    public Token(String accessToken, String userId) {
        this.accessToken = accessToken;
        this.userId = userId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getUserId() {
        return userId;
    }
}
