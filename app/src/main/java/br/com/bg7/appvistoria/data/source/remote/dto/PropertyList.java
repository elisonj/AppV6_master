package br.com.bg7.appvistoria.data.source.remote.dto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PropertyList {

    private String idList;
    private List<Property> propertyList;
    private long propertiesId;

    public PropertyList() {

    }

    public PropertyList(String id, List<Property> propertyList) {
        this.idList = id;
        this.propertyList = propertyList;
    }

    public static PropertyList fromJson(JSONObject jsonObject) {
        PropertyList b = new PropertyList();
        try {
            b.idList = jsonObject.getString("id");

            if(jsonObject.has("propertyList") && jsonObject.getJSONArray("propertyList").length() > 0) {
                JSONObject propertyJson;
                JSONArray arr = jsonObject.getJSONArray("propertyList");

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
        return b;
    }

    /**
     * Return a JSON representation from Object
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

    private List<Property> getPropertyList() {
        return propertyList;
    }

    private void setPropertyList(List<Property> propertyList) {
        this.propertyList = propertyList;
    }
}
