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

public class Properties extends SugarRecord<Properties> {

    List<PropertyList> propertyGroupList;

    public Properties() {}

    public Properties(List<PropertyList> list) {
        this.propertyGroupList = list;
    }

    public List<PropertyList> getPropertyGroupList() {
        return propertyGroupList;
    }

    public void setPropertyGroupList(List<PropertyList> propertyGroupList) {
        this.propertyGroupList = propertyGroupList;
    }

    // Decodes properties json into Properties model object
    public static Properties fromJson(JSONObject jsonObject) {
        Properties b = new Properties();
        // Deserialize json into object fields
        try {



            if(jsonObject.has("propertyGroupList") && jsonObject.getJSONArray("propertyGroupList").length() > 0) {
                JSONObject propertyJson;
                JSONArray arr = jsonObject.getJSONArray("propertyGroupList");

                // Process each result in json array, decode and convert to business object
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
        // Return new object
        return b;
    }



}
