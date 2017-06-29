package br.com.bg7.appvistoria.vo;

import com.orm.SugarRecord;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by elison on 27/06/17.
 */

public class Property extends SugarRecord<Property> {

    String idProperty;
    String value;

    public Property() {

    }

    public Property(String id, String value) {
        this.idProperty = id;
        this.value = value;
    }


    public String getIdProperty() {
        return idProperty;
    }

    public String getValue() {
        return value;
    }

    public void setIdProperty(String id) {
        this.idProperty = id;
    }

    public void setValue(String value) {
        this.value = value;
    }


    // Decodes property json into Property model object
    public static Property fromJson(JSONObject jsonObject) {
        Property b = new Property();
        // Deserialize json into object fields
        try {
            if(jsonObject.has("id")) b.idProperty = jsonObject.getString("id");
            if(jsonObject.has("value")) b.value = jsonObject.getString("value");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        // Return new object
        return b;
    }


}
