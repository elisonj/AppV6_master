package br.com.bg7.appvistoria.data.source.remote.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by: elison
 * Date: 2017-07-12
 */
public class Credentials {
    @SerializedName("login")
    @Expose
    private String login;

    public String getLogin() {
        return login;
    }
}
