package br.com.bg7.appvistoria.data.source.remote.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by: elison
 * Date: 2017-07-28
 */
public class PictureResponse {

    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("elements")
    @Expose
    private List<Attachment> elements = null;

}
