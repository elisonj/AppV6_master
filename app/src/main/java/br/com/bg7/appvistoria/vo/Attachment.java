package br.com.bg7.appvistoria.vo;

import com.orm.SugarRecord;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Created by elison on 03/07/17.
 */

public class Attachment extends SugarRecord<Attachment> {

    private Map<String, String> headers;
    private Object object;
    private AttachmentDataHandler dataHandler;
    private String contentId;
    private AttachmentContentDisposition contentDisposition;
    private AttachmentContentType contentType;



    public Attachment() {

        dataHandler =  new AttachmentDataHandler();
        contentId = "";
        contentDisposition = new AttachmentContentDisposition();
        contentType = new AttachmentContentType();

    }


   /**
     * Return a JSON representation from Attachment
     * @return
     */
    public JSONObject toJSON(){

        JSONObject jsonObject= new JSONObject();
        try {

            if(object != null) {
                byte[] readFileToByteArray = org.apache.commons.io.FileUtils.readFileToByteArray((File)object);

                jsonObject.put("object", readFileToByteArray);

            }

            if(headers != null) jsonObject.put("headers", headers);

            if(dataHandler != null) jsonObject.put("dataHandler", dataHandler.toJSON());
            if(contentId != null) jsonObject.put("contentId", contentId);
            if(contentDisposition != null) jsonObject.put("contentDisposition", contentDisposition.toJSON());
            if(contentType != null) jsonObject.put("contentType", contentType.toJSON());
         //   if(value != null && value.length() > 0) jsonObject.put("value", value);


            return jsonObject;
        } catch (JSONException e) {
            e.printStackTrace();
            return jsonObject;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }






    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public AttachmentDataHandler getDataHandler() {
        return dataHandler;
    }

    public void setDataHandler(AttachmentDataHandler dataHandler) {
        this.dataHandler = dataHandler;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public AttachmentContentDisposition getContentDisposition() {
        return contentDisposition;
    }

    public void setContentDisposition(AttachmentContentDisposition contentDisposition) {
        this.contentDisposition = contentDisposition;
    }

    public AttachmentContentType getContentType() {
        return contentType;
    }

    public void setContentType(AttachmentContentType contentType) {
        this.contentType = contentType;
    }




}
