package br.com.bg7.appvistoria.data.source.remote.dto;

import com.orm.SugarRecord;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Properties extends SugarRecord<Properties> {

    private List<PropertyList> propertyGroupList;
    private long productId;

    /**
     * Default constructor used by Sugar
     */
    @SuppressWarnings("unused")
    public Properties() {}

    public static Properties fromJson(JSONObject jsonObject) {
        Properties b = new Properties();
        try {

            if(jsonObject.has("propertyGroupList") && jsonObject.getJSONArray("propertyGroupList").length() > 0) {
                JSONObject propertyJson;
                JSONArray arr = jsonObject.getJSONArray("propertyGroupList");

                for (int i=0; i < arr.length(); i++) {
                    try {
                        propertyJson = arr.getJSONObject(i);
                    } catch (Exception e) {
                        e.printStackTrace();
                        continue;
                    }

                    PropertyList p = PropertyList.fromJson(propertyJson);
                    if (p != null) {
                        if(b.getPropertyGroupList() == null) {
                            b.setPropertyGroupList(new ArrayList<PropertyList>());
                        }
                        b.getPropertyGroupList().add(p);
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
     * @return
     */
    public JSONObject toJSON() {

        JSONObject propertiesGroupList = new JSONObject();
        JSONArray arr = new JSONArray();
        if(propertyGroupList != null && propertyGroupList.size() > 0) {
            for (PropertyList list: propertyGroupList ) {
                arr.put(list.toJSON());
            }
        }
        try {
            propertiesGroupList.put("propertyGroupList", arr);
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return propertiesGroupList;
    }

    public List<PropertyList> getPropertyGroupList() {
        return propertyGroupList;
    }

    public void setPropertyGroupList(List<PropertyList> propertyGroupList) {
        this.propertyGroupList = propertyGroupList;
    }
}
