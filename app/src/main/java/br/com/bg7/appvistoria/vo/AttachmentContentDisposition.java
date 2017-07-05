package br.com.bg7.appvistoria.vo;

import com.orm.SugarRecord;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by elison on 03/07/17.
 */

public class AttachmentContentDisposition extends SugarRecord<AttachmentContentDisposition> {

    private Map<String, String> parameters;
    private String type;



    /**
     * Return a JSON representation from Attachment
     * @return
     */
    public JSONObject toJSON(){

        JSONObject jsonObject= new JSONObject();
        try {


            if(parameters != null) jsonObject.put("parameters", parameters);
            if(type != null) jsonObject.put("type", type);


            return jsonObject;
        } catch (JSONException e) {
            e.printStackTrace();
            return jsonObject;
        }

    }



    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
