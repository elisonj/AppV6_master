package br.com.bg7.appvistoria.vo;

import com.orm.SugarRecord;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by elison on 27/06/17.
 */

public class PropertyList extends SugarRecord<PropertyList> {

    String idList;
    List<Property> propertyList;
    private long propertiesId;


    public PropertyList() {}

    public PropertyList(String id, List<Property> propertyList) {
        this.idList = id;
        this.propertyList = propertyList;
    }

    public String getIdList() {
        return idList;
    }

    public List<Property> getPropertyList() {
        return propertyList;
    }

    public void setIdList(String idList) {
        this.idList = idList;
    }

    public void setPropertyList(List<Property> propertyList) {
        this.propertyList = propertyList;
    }

    // Decodes propertyList json into PropertyList model object
    public static PropertyList fromJson(JSONObject jsonObject) {
        PropertyList b = new PropertyList();
        // Deserialize json into object fields
        try {
            b.idList = jsonObject.getString("id");


            if(jsonObject.has("propertyList") && jsonObject.getJSONArray("propertyList").length() > 0) {
                JSONObject propertyJson;
                JSONArray arr = jsonObject.getJSONArray("propertyList");


                // Process each result in json array, decode and convert to business object
                for (int i=0; i < arr.length(); i++) {
                    try {
                        propertyJson = arr.getJSONObject(i);
                    } catch (Exception e) {
                        e.printStackTrace();
                        continue;
                    }

                    Property p = Property.fromJson(propertyJson);
                    if (p != null) {

                        if(b.getPropertyList() == null) {
                            b.setPropertyList(new ArrayList<Property>());
                        }

                        b.getPropertyList().add(p);
                    }
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        // Return new object
        return b;
    }



    /**
     * Return a JSON representation from Object
     * @return
     */
    public JSONObject toJSON() {

        JSONObject propertiesList = new JSONObject();
        JSONArray arr = new JSONArray();

        if(propertyList != null && propertyList.size() > 0) {
            for (Property property: propertyList ) {
                arr.put(property.toJSON());
            }
        }
        try {
            propertiesList.put("id", idList);
            propertiesList.put("propertyList", arr);
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return propertiesList;
    }


    public void setPropertiesId(long propertiesId) {
        this.propertiesId = propertiesId;
    }

    public long getPropertiesId() {
        return propertiesId;
    }
}
