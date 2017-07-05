package br.com.bg7.appvistoria.vo;

import com.orm.SugarRecord;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by elison on 03/07/17.
 */

public class AttachmentContentType extends SugarRecord<AttachmentContentType> {
    private String type;
    private String subtype;
    private Map<String, String> parameters;
    private  boolean wildcardType;
    private boolean wildcardSubtype;




    /**
     * Return a JSON representation from Attachment
     * @return
     */
    public JSONObject toJSON(){

        JSONObject jsonObject= new JSONObject();
        try {


            if(parameters != null) jsonObject.put("parameters", parameters);
            if(type != null) jsonObject.put("type", type);
            if(subtype != null) jsonObject.put("subtype", subtype);
            jsonObject.put("wildcardType", wildcardType);
            jsonObject.put("wildcardSubtype", wildcardSubtype);


            return jsonObject;
        } catch (JSONException e) {
            e.printStackTrace();
            return jsonObject;
        }

    }





    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public boolean isWildcardType() {
        return wildcardType;
    }

    public void setWildcardType(boolean wildcardType) {
        this.wildcardType = wildcardType;
    }

    public boolean isWildcardSubtype() {
        return wildcardSubtype;
    }

    public void setWildcardSubtype(boolean wildcardSubtype) {
        this.wildcardSubtype = wildcardSubtype;
    }
}
