package br.com.bg7.appvistoria.data.source.remote.dto;

import org.json.JSONException;
import org.json.JSONObject;

public class Property {

    private String idProperty;
    private String value;

    public Property() {

    }

    public Property(String id, String value) {
        this.idProperty = id;
        this.value = value;
    }

    public static Property fromJson(JSONObject jsonObject) {
        Property b = new Property();
        try {
            if(jsonObject.has("id")) b.idProperty = jsonObject.getString("id");
            if(jsonObject.has("value")) b.value = jsonObject.getString("value");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return b;
    }

    /**
     * Return a JSON representation from Object
     */
    public JSONObject toJSON(){
        JSONObject jsonObject= new JSONObject();
        try {
            jsonObject.put("id", idProperty);
            if(value != null && value.length() > 0) jsonObject.put("value", value);
            return jsonObject;
        } catch (JSONException e) {
            e.printStackTrace();
            return jsonObject;
        }
    }
}
