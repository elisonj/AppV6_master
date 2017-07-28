package br.com.bg7.appvistoria.data.source.remote.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by: elison
 * Date: 2017-07-28
 */
public class Attachment {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("fileName")
    @Expose
    private String fileName;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("contentType")
    @Expose
    private String contentType;
    @SerializedName("attachmentType")
    @Expose
    private String attachmentType;
    @SerializedName("uploadFileName")
    @Expose
    private String uploadFileName;
    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
}